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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.api.ApiList;
import kr.dataportal.datahubservice.dto.api.ApiListSearchDTO;
import kr.dataportal.datahubservice.dto.api.ApiListSearchFilterDTO;
import kr.dataportal.datahubservice.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {
    private final Gson gson;

    // API 목록 화면
    @GetMapping("")
    public String ApiListView(Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/api/list")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ApiListSearchFilterDTO.createDefault())
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            ApiListSearchDTO apiList = gson.fromJson(gson.toJson(response.getData()), ApiListSearchDTO.class);
            model.addAttribute("apiCount", apiList.getItemCount());
            model.addAttribute("category", apiList.getCategory());
            model.addAttribute("organization", apiList.getOrganization());
            model.addAttribute("datahub", apiList.getOwnDatahub());
            model.addAttribute("apiData", apiList.getItems());
        });

        model.addAttribute("search_name", "");
        model.addAttribute("check_organization", new ArrayList<String>());
        model.addAttribute("check_category", new ArrayList<String>());
        model.addAttribute("check_datahub", new ArrayList<String>());

        return "api/list";
    }

    @PostMapping("")
    public String ApiListView(ApiListSearchFilterDTO searchDTO, Model model) {
        System.out.println(searchDTO);
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

        jsonResponse.ifPresent(response -> {
            ApiListSearchDTO apiList = gson.fromJson(gson.toJson(response.getData()), ApiListSearchDTO.class);
            model.addAttribute("apiCount", apiList.getItemCount());
            model.addAttribute("category", apiList.getCategory());
            model.addAttribute("organization", apiList.getOrganization());
            model.addAttribute("datahub", apiList.getOwnDatahub());
            model.addAttribute("apiData", apiList.getItems());
        });

        model.addAttribute("search_name", searchDTO.getName());
        model.addAttribute("check_organization", searchDTO.getOrganization());
        model.addAttribute("check_category", searchDTO.getCategory());
        model.addAttribute("check_datahub", searchDTO.getOwnDatahub());

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
