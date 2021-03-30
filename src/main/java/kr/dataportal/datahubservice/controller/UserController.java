/*
    Copyright (c) 2021 Aaron(JIN, Taeyang).
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Lesser General Public License, version 3
    which accompanies this distribution, and is available at
    https://www.gnu.org/licenses/lgpl-3.0.html
    
    Contributors:
        Aaron(JIN, Taeyang) - 
*/

package kr.dataportal.datahubservice.controller;

import io.swagger.annotations.ApiModelProperty;
import kr.dataportal.datahubservice.domain.user.User;
import kr.dataportal.datahubservice.dto.UserSignupDto;
import kr.dataportal.datahubservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    @ApiIgnore
    public String SignupView() {
        return "register";
    }

    @PostMapping("")
    public String SignupAction(@RequestBody UserSignupDto user) {
        User u = User.create(user.getEmail(), user.getFirstPassword(), user.getSecondPassword(), user.getNickname());
        if (u == null) {
            return "ERROR";
        } else {
            userService.signup(u);
            return "";
        }
    }
}
