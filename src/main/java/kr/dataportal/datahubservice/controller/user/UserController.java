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
        model.addAttribute("nickname", userSignUpDto.getNickname());
        
        SignUpStatus result = (SignUpStatus) model.getAttribute("result");
        if (result == null) {
            model.addAttribute("result", "알 수 없는 오류가 발생하였습니다. [SRV->CORE]");
            return "user/signup_result";
        }
        String resultMsg = switch (result) {
            case SUCCESS -> "회원가입에 성공하였습니다.";
            case CONFLICT_EMAIL -> "이미 등록된 이메일입니다.";
            case CONFLICT_NICKNAME -> "이미 등록된 닉네임입니다.";
            case MISMATCH_PASSWORD -> "패스워드가 일치하지 않습니다.";
            case FAIL -> "알 수 없는 오류가 발생하였습니다. 다시 시도해주세요.";
        };
        model.addAttribute("result", resultMsg);

        if (result == SignUpStatus.SUCCESS) {
            return "user/signup_result";
        }

        return "user/signup";
    }

    // 마이페이지 화면 연결
    @GetMapping("/info")
    @ApiIgnore
    public String MyPageView() {
        return "user/info";
    }
}
