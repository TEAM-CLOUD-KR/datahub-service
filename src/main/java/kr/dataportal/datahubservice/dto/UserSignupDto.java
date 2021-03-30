package kr.dataportal.datahubservice.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class UserSignupDto {
    private final String email;
    private final String firstPassword;
    private final String secondPassword;
    private final String nickname;
}
