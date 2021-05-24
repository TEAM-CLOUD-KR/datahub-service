package kr.dataportal.datahubservice.controller.dashboard;

import kr.dataportal.datahubservice.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    @GetMapping("")
    public String DashBoardMainView() {
        return "error";
    }
}
