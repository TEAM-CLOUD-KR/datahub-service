package kr.dataportal.datahubservice.dto.dataset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DataSetList {
    private int seq;
    private final String dataset;
    private final String datasetRaw;
    private final String datasetColumn;
    private final String description;
}
