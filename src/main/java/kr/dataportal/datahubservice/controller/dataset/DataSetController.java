package kr.dataportal.datahubservice.controller.dataset;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/dataset")
public class DataSetController {
    // 데이터셋 목록 화면
    @GetMapping("")
    @ApiIgnore
    public String DataSetListView() {
        return "dataset/list";
    }

    // 데이터셋 요청 목록 화면
    @GetMapping("/request")
    @ApiIgnore
    public String DataSetRequestListView() {
        return "dataset/request/list";
    }

    // 데이터셋 요청 화면
    @GetMapping("/new")
    @ApiIgnore
    public String DataSetRequestView() {
        return "dataset/request/action";
    }
}
