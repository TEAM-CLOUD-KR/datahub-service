/*
    Copyright (c) 2021 Aaron(JIN, Taeyang).
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Lesser General Public License, version 3
    which accompanies this distribution, and is available at
    https://www.gnu.org/licenses/lgpl-3.0.html
    
    Contributors:
        Aaron(JIN, Taeyang) - 
*/

package kr.dataportal.datahubservice.controller.api;

import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.api.ApiList;
import kr.dataportal.datahubservice.dto.api.ApiListSearchDTO;
import kr.dataportal.datahubservice.util.CommonUtil;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class APIController {

    // API 목록 화면
    @GetMapping("")
    public String ApiListView(ApiListSearchDTO searchDTO, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/api/list")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(searchDTO)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response ->
                model.addAttribute(
                        "apis",
                        new CommonUtil<ApiList>().convertObjectToList(response.getData())
                ));

        jsonResponse = client.get()
                .uri("/api/count")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> model.addAttribute("api_count", response.getData()));

        return "api/list";
    }

    // API 상세 조회 화면
    @GetMapping("/{seq}")
    @ApiIgnore
    public String ApiDetailView(@PathVariable("seq") String seq) {
        return "api/view";
    }

    // API 생성 화면
    @GetMapping("/new")
    @ApiIgnore
    public String ApiCreateView() {
        return "api/new";
    }

    // API 관리 화면
    @GetMapping("/manage/{seq}")
    @ApiIgnore
    public String ApiManageView(@PathVariable("seq") String seq) {
        return "api/manage";
    }

    // API 활용 신청 목록 화면
    @GetMapping("/dev")
    @ApiIgnore
    public String ApiDevRequestListView() {
        return "api/dev/list";
    }

    // API 활용 신청 화면
    @GetMapping("/dev/{seq}")
    @ApiIgnore
    public String ApiDevRequestView(@PathVariable("seq") String seq) {
        return "api/dev/action";
    }
}
