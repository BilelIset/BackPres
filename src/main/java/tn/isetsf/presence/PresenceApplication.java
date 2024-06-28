package tn.isetsf.presence;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.LocalDateTime;
import java.util.Random;


@SpringBootApplication
@EnableRedisHttpSession

public class PresenceApplication {



	public static void main(String[] args) {

		SpringApplication.run(PresenceApplication.class, args);

	}


}
