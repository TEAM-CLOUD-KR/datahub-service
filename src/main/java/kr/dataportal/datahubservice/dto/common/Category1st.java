package kr.dataportal.datahubservice.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Category1st {
    private final String text;

    public Category1st() {
        this.text = null;
    }
}
