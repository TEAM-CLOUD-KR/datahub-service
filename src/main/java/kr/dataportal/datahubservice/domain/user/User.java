package kr.dataportal.datahubservice.domain.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name = "seq")
    @ApiModelProperty(hidden = true)
    private int seq;

    @Column(name = "email", length = 255, unique = true)
    private final String email;

    @Column(name = "password", length = 255)
    private final String password;

    @Column(name = "nickname", length = 255, unique = true)
    private final String nickname;

    public User() {
        this.email = null;
        this.password = null;
        this.nickname = null;
    }

    public static User create(String email, String password_1, String password_2, String nickname) {
        if (password_1.equals(password_2)) {
            return new User(email, password_1, nickname);
        } else {
            return null;
        }
    }

}