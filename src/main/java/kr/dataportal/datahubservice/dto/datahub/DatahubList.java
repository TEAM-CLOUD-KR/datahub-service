package kr.dataportal.datahubservice.dto.datahub;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DatahubList {
    private final int seq;
    private final String name;

    public DatahubList() {
        this.seq = -1;
        this.name = null;
    }
}
