package kr.dataportal.datahubservice.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.SerializedName;
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

    public User() {
        this.seq = -1;
        this.email = null;
        this.nickname = null;
        this.regDate = null;
        this.eraseDate = null;
    }
}
