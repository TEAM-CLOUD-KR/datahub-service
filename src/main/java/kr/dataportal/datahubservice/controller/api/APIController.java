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
import kr.dataportal.datahubservice.dto.api.*;
import kr.dataportal.datahubservice.dto.user.User;
import kr.dataportal.datahubservice.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {
    private final Gson gson;

    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

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
    public String ApiDetailView(@PathVariable("seq") String seq, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/api/" + seq)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            ApiListDetailAndDataSetColumn apiList = gson.fromJson(gson.toJson(response.getData()), ApiListDetailAndDataSetColumn.class);
            model.addAttribute("api_detail", apiList.getDetail());
            model.addAttribute("api_dataset_column_desc", apiList.getDataSetColumnDesc());
        });
        return "api/view";
    }

    // API 생성 화면
    @GetMapping("/new")
    @ApiIgnore
    public String ApiCreateView(Model model) {
        User user = (User) model.getAttribute("user");
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/user/datahub?userSeq=" + Objects.requireNonNull(user).getSeq())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            CommonUtil<String> commonUtil = new CommonUtil<>();
            List<String> datahubList = commonUtil.convertObjectToList(
                    gson.fromJson(gson.toJson(response.getData()), Object.class)
            );
            model.addAttribute("datahub", datahubList);
        });
        return "api/new";
    }

    // API 생성 기능
    @PostMapping("/new")
    @ApiIgnore
    public String ApiCreateFunction(ApiListCreateDTO apiListCreateDTO, Model model) {
        System.out.println(apiListCreateDTO);
        User user = (User) model.getAttribute("user");
        apiListCreateDTO.setPublisher(Objects.requireNonNull(user).getSeq());
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/api/new")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(apiListCreateDTO)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        int apiSeq = jsonResponse.map(
                response -> gson.fromJson(gson.toJson(response.getData()), Integer.class)
        ).orElse(-1);
        if (apiSeq == -1) {
            return "/";
        }
        return "redirect:/api/" + apiSeq;
    }

    // API 관리 화면
    @GetMapping("/manage")
    @ApiIgnore
    public String ApiManageView(Model model) {
        User user = (User) model.getAttribute("user");
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/api/request?userSeq=" + Objects.requireNonNull(user).getSeq())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            CommonUtil<ApiUsingListDTO> commonUtil = new CommonUtil<>();
            List<ApiUsingListDTO> apiLists = commonUtil.convertObjectToList(
                    gson.fromJson(gson.toJson(response.getData()), Object.class)
            );
            model.addAttribute("apiUsingList", apiLists);
        });
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
    public String ApiDevRequestView(@PathVariable("seq") String seq, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/api/" + seq)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(response -> {
            ApiListDetailAndDataSetColumn apiList = gson.fromJson(gson.toJson(response.getData()), ApiListDetailAndDataSetColumn.class);
            model.addAttribute("api_detail", apiList.getDetail());
        });

        return "api/dev/action";
    }
}
