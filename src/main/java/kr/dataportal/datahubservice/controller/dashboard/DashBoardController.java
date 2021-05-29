package kr.dataportal.datahubservice.controller.dashboard;

import com.google.gson.Gson;
import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.dashboard.DashBoardListDTO;
import kr.dataportal.datahubservice.dto.user.DashBoardContentUpdateDto;
import kr.dataportal.datahubservice.dto.user.User;
import kr.dataportal.datahubservice.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    private final Gson gson;

    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    @GetMapping("")
    public String DashBoardMainView() {
        return "dashboard/main";
    }

    @GetMapping("/list")
    public String DashBoardChartListView() {
        return "dashboard/list";
    }

    @GetMapping("/write")
    public String DashBoardWriteView(Model model) {
        User user = (User) model.getAttribute("user");
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/user/dashboard?userSeq=" + Objects.requireNonNull(user).getSeq())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            User findUser = gson.fromJson(gson.toJson(response.getData()), User.class);
            model.addAttribute("user", findUser);
        });
        return "dashboard/write";
    }

    @PostMapping("/write")
    public String DashBoardWriteAction(DashBoardContentUpdateDto dashBoardContentUpdateDto) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/dashboard")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dashBoardContentUpdateDto)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();
        return "redirect:/dashboard/write";
    }

    @GetMapping("/new")
    public String DashBoardNewView(@RequestParam(name = "type") String type, Model model) {
        model.addAttribute("type", type);
        return "dashboard/new";
    }

    @PostMapping("/new")
    public String DashBoardNewAction(DashBoardListDTO dashBoardListDTO, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/dashboard")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dashBoardListDTO)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        if (jsonResponse.isEmpty()) {
            return "error";
        }

        int seq = gson.fromJson(gson.toJson(jsonResponse.get().getData()), int.class);

        return "redirect:/dashboard/" + seq;
    }

    @GetMapping("/{seq}")
    public String DashBoardDetailView(@PathVariable int seq) {
        return "error";
    }
}
