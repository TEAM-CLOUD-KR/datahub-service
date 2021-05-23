package kr.dataportal.datahubservice.dto.api;

import kr.dataportal.datahubservice.dto.dataset.DataSetColumnDesc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApiListDetailAndDataSetColumn {
    private final ApiList detail;
    private final List<Object> dataSetColumnDesc;

}
