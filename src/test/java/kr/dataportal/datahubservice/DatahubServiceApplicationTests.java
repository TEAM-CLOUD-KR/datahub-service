package kr.dataportal.datahubservice;

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

    @Test
    void contextLoads() {
    }
}
