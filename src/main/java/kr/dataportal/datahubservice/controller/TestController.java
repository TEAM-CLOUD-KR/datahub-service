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

import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.util.CommonUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/{target}")
    public List<?> Test(@PathVariable String target) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        JSONResponse result = client.get()
                .uri("/common/util/scheme/{target}", target)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JSONResponse.class)
                .toStream()
                .collect(Collectors.toList()).get(0);

        return CommonUtil.convertObjectToList(result.getData());
    }
}
