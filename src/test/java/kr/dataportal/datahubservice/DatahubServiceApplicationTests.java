package kr.dataportal.datahubservice;

import kr.dataportal.datahubservice.domain.user.User;
import kr.dataportal.datahubservice.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = ("" +
        "spring.config.location=" +
        "classpath:/application.yml," +
        "optional:C:/repository/_secrets/datahub-service.yml," +
        "optional:/home/datahub/_secrets/datahub-service.yml," +
        "optional:/Users/sun/repository/_secrets/datahub-service.yml"
))
class DatahubServiceApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = true)
    void 회원가입테스트() {
        User user_1 = User.create("test@example.com", "pass1", "pass1", "abc");
        Assertions.assertThat(user_1).isNotNull();
        Assertions.assertThat(user_1.getEmail()).isEqualTo("test@example.com");

        int signup_1 = userService.signup(user_1);
        Assertions.assertThat(signup_1).isNotNull();

        Assertions.assertThat(userService.findByEmail(user_1.getEmail()).getNickname()).isEqualTo(user_1.getNickname());

        User user_2 = User.create("test@example.com", "pass1", "pass2", "abc");
        Assertions.assertThat(user_2).isNull();
    }
}
