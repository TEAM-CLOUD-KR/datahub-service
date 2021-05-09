package kr.dataportal.datahubservice.dto.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SignInResponse {
    private final User user;
    private final SignInStatus status;
}
