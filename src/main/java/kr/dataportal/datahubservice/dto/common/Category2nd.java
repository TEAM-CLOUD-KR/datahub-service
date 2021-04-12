package kr.dataportal.datahubservice.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Category2nd {
    private final String text;
    private final Category1st parent;
}
