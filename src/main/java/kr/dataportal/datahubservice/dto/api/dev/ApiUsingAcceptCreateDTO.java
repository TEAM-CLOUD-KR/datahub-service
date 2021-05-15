package kr.dataportal.datahubservice.dto.api.dev;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class  ApiUsingAcceptCreateDTO {
    private final int apiSeq;

    private final int userSeq;

    private final String purpose;
}
