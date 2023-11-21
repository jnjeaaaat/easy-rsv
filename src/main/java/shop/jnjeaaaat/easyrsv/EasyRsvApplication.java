package shop.jnjeaaaat.easyrsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasyRsvApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyRsvApplication.class, args);
    }

}
