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
		int index=0;
		int indexSimple=0;

		LocalTime time = LocalTime.now();
		if (!time.isBefore(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(9, 45))) {
			indexSimple= 1;
		} else if (!time.isBefore(LocalTime.of(10, 0)) && time.isBefore(LocalTime.of(11, 30))) {
			indexSimple= 2;
		} else if (!time.isBefore(LocalTime.of(11, 45)) && time.isBefore(LocalTime.of(13, 15))) {
			indexSimple= 3;
		} else if (!time.isBefore(LocalTime.of(13, 20)) && time.isBefore(LocalTime.of(14, 50))) {
			indexSimple= 4;
		} else if (!time.isBefore(LocalTime.of(14, 55)) && time.isBefore(LocalTime.of(16, 25))) {
			indexSimple= 5;
		} else if (!time.isBefore(LocalTime.of(16, 30)) && time.isBefore(LocalTime.of(18, 0))) {
			indexSimple= 6;
		} else {
			indexSimple= 0;
		}
		if (!time.isBefore(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(10, 0))) {
			index= 7;
		} else if (!time.isBefore(LocalTime.of(10, 0)) && time.isBefore(LocalTime.of(13, 20))) {
			index= 8;
		} else if (!time.isBefore(LocalTime.of(11, 45)) && time.isBefore(LocalTime.of(15, 0))) {
			index= 9;
		} else if (!time.isBefore(LocalTime.of(13, 20)) && time.isBefore(LocalTime.of(16, 45))) {
			index= 10;
		} else if (!time.isBefore(LocalTime.of(14, 55)) && time.isBefore(LocalTime.of(18, 0))) {
			index= 11;
		} else {
			index= 0;
		}
System.out.println("index simple= "+indexSimple+" index souble = "+index);
	}


}
