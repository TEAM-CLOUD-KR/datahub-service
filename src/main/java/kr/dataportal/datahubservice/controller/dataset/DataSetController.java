package kr.dataportal.datahubservice.controller.dataset;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import kr.dataportal.datahubservice.domain.datacore.JSONResponse;
import kr.dataportal.datahubservice.dto.dataset.DataSetColumnDesc;
import kr.dataportal.datahubservice.dto.dataset.DataSetList;
import kr.dataportal.datahubservice.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dataset")
@RequiredArgsConstructor
public class DataSetController {
    private final Gson gson;

    @ModelAttribute("user")
    public User userModel(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    // 데이터셋 검색 메소드
    private List<DataSetList> getDataSetListByName(String name) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/dataset/search?name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        if (jsonResponse.isEmpty()) {
            return new ArrayList<>();
        }

        return gson.fromJson(gson.toJson(jsonResponse.get().getData()),
                new TypeToken<ArrayList<DataSetList>>() {
                }.getType()
        );
    }

    // 데이터셋 목록 화면
    @GetMapping("")
    @ApiIgnore
    public String DataSetListView(Model model) {
        List<DataSetList> dataSetListByName = getDataSetListByName("");
        model.addAttribute("datasetList", dataSetListByName);
        return "dataset/list";
    }

    // 데이터셋 상세 조회 화면
    @GetMapping("/{name}")
    @ApiIgnore
    public String DataSetDetailView(@PathVariable("name") String name, Model model) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.get()
                .uri("/common/util/scheme/" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        jsonResponse.ifPresent(
                response -> {
                    model.addAttribute("type", jsonResponse.get().getStatus().name());
                    switch (jsonResponse.get().getStatus()) {
                        case OK -> {
                            List<DataSetColumnDesc> dataSetColumnDesc = gson.fromJson(gson.toJson(jsonResponse.get().getData()),
                                    new TypeToken<ArrayList<DataSetColumnDesc>>() {
                                    }.getType());
                            model.addAttribute("result", dataSetColumnDesc);
                        }
                        case MOVED_PERMANENTLY -> {
                            DataSetList dataSetList = gson.fromJson(gson.toJson(jsonResponse.get().getData()), DataSetList.class);
                            model.addAttribute("result", dataSetList);
                        }
                    }
                }
        );
        return "dataset/view";
    }

    // 데이터셋 요청 목록 화면
    @GetMapping("/request")
    @ApiIgnore
    public String DataSetCreateListView() {
        return "dataset/request/list";
    }

    // 데이터셋 등록 화면
    @GetMapping("/new")
    @ApiIgnore
    public String DataSetCreateView() {
        return "dataset/new";
    }

    // 데이터셋 등록 기능
    @PostMapping("/new")
    @ApiIgnore
    public String DataSetCreateAction(DataSetList dataSetList) {
        WebClient client = WebClient.builder()
                .baseUrl("https://api.dataportal.kr")
                .build();

        Optional<JSONResponse> jsonResponse = client.post()
                .uri("/dataset")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dataSetList)
                .retrieve()
                .bodyToMono(JSONResponse.class)
                .blockOptional();

        String encoded = URLEncoder.encode(dataSetList.getDataset(), StandardCharsets.UTF_8);
        return "redirect:/dataset/" + encoded;
    }

    // 데이터셋 검색 폼 화면
    @RequestMapping("/search")
    @ApiIgnore
    public String DataSetSearchFormView(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        List<DataSetList> dataSetListByName = getDataSetListByName(name);
        if (dataSetListByName.size() != 0) {
            model.addAttribute("dataset", dataSetListByName);
        }

        return "dataset/search-popup";
    }
}
