package tn.isetsf.presence;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;


@SpringBootApplication
@EnableRedisHttpSession

public class PresenceApplication {



	public static void main(String[] args) {

		SpringApplication.run(PresenceApplication.class, args);
		int index=0,indexdouble=0;

		LocalTime time = LocalTime.now();
		if (time.isAfter(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(9, 45))) {
			index= 1;
		} else if (time.isAfter(LocalTime.of(9, 45)) && time.isBefore(LocalTime.of(11, 30))) {
			index= 2;
		} else if (time.isAfter(LocalTime.of(11, 30)) && time.isBefore(LocalTime.of(13, 15))) {
			index= 3;
		} else if (time.isAfter(LocalTime.of(13, 15)) && time.isBefore(LocalTime.of(14, 50))) {
			index= 4;
		} else if (time.isAfter(LocalTime.of(14, 50)) && time.isBefore(LocalTime.of(16, 25))) {
			index= 5;
		} else if (time.isAfter(LocalTime.of(16, 25)) && time.isBefore(LocalTime.of(18, 0))) {
			index= 6;


	}


		if (time.isAfter(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(11, 30))) {
			indexdouble= 7;
		} else if (time.isAfter(LocalTime.of(9, 45)) && time.isBefore(LocalTime.of(13, 15))) {
			indexdouble= 8;
		} else if (time.isAfter(LocalTime.of(11, 30)) && time.isBefore(LocalTime.of(14, 50))) {
			indexdouble= 11;
		} else if (time.isAfter(LocalTime.of(13, 15)) && time.isBefore(LocalTime.of(16, 25))) {
			indexdouble= 9;
		} else if (time.isAfter(LocalTime.of(14, 50)) && time.isBefore(LocalTime.of(18, 0))) {
			indexdouble= 10;
		} else {
			indexdouble= 0;
		}
		System.out.println("simple = "+index+" double = "+indexdouble);

	}


}
