package kr.dataportal.datahubservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DataHubServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DataHubServiceApplication.class)
                .properties("" +
                        "spring.config.location=" +
                        "classpath:/application.yml," +
                        "optional:C:/repository/_secrets/datahub-service.yml," +
                        "optional:/home/datahub/_secrets/datahub-service.yml"
                )
                .run(args);
    }
}
