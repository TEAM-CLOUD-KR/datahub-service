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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class APIController {

    @GetMapping("")
    @ApiIgnore
    public String ApiList(Model model) {
        List<String> a = new ArrayList<>();
        a.add("대구시청");
        a.add("a");
        a.add("b");
        a.add("c");
        model.addAttribute("apis", a);
        return "api_list";
    }
}
