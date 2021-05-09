package kr.dataportal.datahubservice.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserSignInDto {
    private final String email;
    private final String password;
}
