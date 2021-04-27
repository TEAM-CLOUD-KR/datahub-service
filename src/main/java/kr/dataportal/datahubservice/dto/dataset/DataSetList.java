package kr.dataportal.datahubservice.dto.dataset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DataSetList {
    private final String dataset;

    public DataSetList() {
        this.dataset = null;
    }
}
