package kr.dataportal.datahubservice.controller.user;

import com.google.gson.Gson;
import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final Gson gson;

    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    // 로그인 화면 연결
    @GetMapping("")
    @ApiIgnore
    public String SignInView(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "user/signin";
    }

    // 로그아웃 화면 연결
    @GetMapping("/logout")
    @ApiIgnore
    public String UserLogout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("")
    public String SignInAction(UserSignInDto signInDto, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }

        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/user/signin")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(signInDto)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            SignInResponse signInResponse = gson.fromJson(gson.toJson(response.getData()), SignInResponse.class);
            session.setAttribute("user", null);
            switch (signInResponse.getStatus()) {
                case SUCCESS -> session.setAttribute("user", signInResponse.getUser());
                case WRONG_EMAIL -> model.addAttribute("result", "WRONG_EMAIL");
                case WRONG_PASSWORD -> model.addAttribute("result", "WRONG_PASSWORD");
                case FAIL -> model.addAttribute("result", "FAIL");
            }
        });

        model.addAttribute("username", signInDto.getEmail());
        model.addAttribute("password", signInDto.getPassword());

        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "user/signin";
    }

    // 회원가입 화면 연결
    @GetMapping("/new")
    @ApiIgnore
    public String SignUpView() {
        return "user/signup";
    }

    @PostMapping("/new")
    @ApiIgnore
    public String SignUpAction(UserSignUpDto userSignUpDto, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        model.addAttribute("user", userSignUpDto);

        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/user/signup")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userSignUpDto)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            SignUpStatus signUpStatus = gson.fromJson(gson.toJson(response.getData()), SignUpStatus.class);
            model.addAttribute("result", signUpStatus);
        });
        model.addAttribute("email", userSignUpDto.getEmail());

        SignUpStatus result = (SignUpStatus) model.getAttribute("result");
        if (result == SignUpStatus.SUCCESS) {
            return "user/signup_result";
        }
        model.addAttribute("nickname", userSignUpDto.getNickname());
        return "user/signup";
    }

    // 마이페이지 화면 연결
    @GetMapping("/{seq}")
    @ApiIgnore
    public String MyPageView(@PathVariable("seq") String seq) {
        return "user/info";
    }
}
