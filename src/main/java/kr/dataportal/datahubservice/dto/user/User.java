package kr.dataportal.datahubservice.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class User {
    private final int seq;
    private final String email;
    private final String nickname;
    private final LocalDateTime regDate;
    private final LocalDateTime eraseDate;
}
