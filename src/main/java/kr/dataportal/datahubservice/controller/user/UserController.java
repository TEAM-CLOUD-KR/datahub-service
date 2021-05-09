package kr.dataportal.datahubservice.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/user")
public class UserController {
    // 로그인 화면 연결
    @GetMapping("")
    @ApiIgnore
    public String SignInView() {
        return "user/signin";
    }

    // 회원가입 화면 연결
    @GetMapping("/new")
    @ApiIgnore
    public String SignUpView() {
        return "user/signup";
    }

    // 마이페이지 화면 연결
    @GetMapping("/{seq}")
    @ApiIgnore
    public String MyPageView(@PathVariable("seq") String seq) {
        return "user/info";
    }
}
