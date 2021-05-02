package kr.dataportal.datahubservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Category2nd {
    private final String text;

    public Category2nd() {
        this.text = null;
    }
}
