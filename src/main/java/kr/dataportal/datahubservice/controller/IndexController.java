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
import kr.dataportal.datahubservice.dto.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    @GetMapping("/")
    @ApiIgnore
    public String Index() {
        return "index";
    }
}
