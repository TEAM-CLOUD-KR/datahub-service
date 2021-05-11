package kr.dataportal.datahubservice.controller.dataset;

import com.google.gson.Gson;
import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.api.ApiListDetailAndDataSetColumn;
import kr.dataportal.datahubservice.dto.user.User;
import kr.dataportal.datahubservice.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dataset")
@RequiredArgsConstructor
public class DataSetController {
    private final Gson gson;

    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    // 데이터셋 목록 화면
    @GetMapping("")
    @ApiIgnore
    public String DataSetListView() {
        return "dataset/list";
    }

    // 데이터셋 요청 목록 화면
    @GetMapping("/request")
    @ApiIgnore
    public String DataSetRequestListView() {
        return "dataset/request/list";
    }

    // 데이터셋 요청 화면
    @GetMapping("/new")
    @ApiIgnore
    public String DataSetRequestView() {
        return "dataset/request/action";
    }

    // 데이터셋 검색 폼 화면
    @RequestMapping("/search")
    @ApiIgnore
    public String DataSetSearchFormView(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/dataset/search?name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            CommonUtil<String> commonUtil = new CommonUtil<>();
            List<String> datasetList = commonUtil.convertObjectToList(
                    gson.fromJson(gson.toJson(response.getData()), Object.class)
            );
            model.addAttribute("dataset", datasetList);
        });
        return "dataset/search-popup";
    }
}
