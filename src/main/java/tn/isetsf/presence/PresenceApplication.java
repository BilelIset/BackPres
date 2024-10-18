package tn.isetsf.presence;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import tn.isetsf.presence.sec.repository.AppRoleRepo;
import tn.isetsf.presence.sec.repository.AppUserRepo;
import tn.isetsf.presence.sec.service.AppUserInterfaceImpl;


import java.time.LocalTime;


@SpringBootApplication


public class PresenceApplication {
	@Autowired
	private AppRoleRepo appRoleRepo;
	@Autowired
	private AppUserRepo appUserRepo;
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {

		SpringApplication.run(PresenceApplication.class, args);
	}}

//		int index = 0, indexdouble = 0;
//
//		LocalTime time = LocalTime.now();
//		if (time.isAfter(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(9, 45))) {
//			index = 1;
//		} else if (time.isAfter(LocalTime.of(9, 45)) && time.isBefore(LocalTime.of(11, 30))) {
//			index = 2;
//		} else if (time.isAfter(LocalTime.of(11, 30)) && time.isBefore(LocalTime.of(13, 15))) {
//			index = 3;
//		} else if (time.isAfter(LocalTime.of(13, 15)) && time.isBefore(LocalTime.of(14, 50))) {
//			index = 4;
//		} else if (time.isAfter(LocalTime.of(14, 50)) && time.isBefore(LocalTime.of(16, 25))) {
//			index = 5;
//		} else if (time.isAfter(LocalTime.of(16, 25)) && time.isBefore(LocalTime.of(18, 0))) {
//			index = 6;
//
//
//		}
//
//
//		if (time.isAfter(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(11, 30))) {
//			indexdouble = 7;
//		} else if (time.isAfter(LocalTime.of(9, 45)) && time.isBefore(LocalTime.of(13, 15))) {
//			indexdouble = 8;
//		} else if (time.isAfter(LocalTime.of(11, 30)) && time.isBefore(LocalTime.of(14, 50))) {
//			indexdouble = 11;
//		} else if (time.isAfter(LocalTime.of(13, 15)) && time.isBefore(LocalTime.of(16, 25))) {
//			indexdouble = 9;
//		} else if (time.isAfter(LocalTime.of(14, 50)) && time.isBefore(LocalTime.of(18, 0))) {
//			indexdouble = 10;
//		} else {
//			indexdouble = 0;
//		}
//		System.out.println("simple = " + index + " double = " + indexdouble);
//
//	}
//
//	@Bean
//	CommandLineRunner start(AppUserInterfaceImpl appUserInterface) {
//		return args -> {

//appUserInterface.AddUser(new AppUser(null,"user1","1234",new ArrayList<>()));
//			appUserInterface.AddUser(new AppUser(null,"admin","1234",new ArrayList<>()));
//			appUserInterface.AddUser(new AppUser(null,"user2","1234",new ArrayList<>()));
//			appUserInterface.AddUser(new AppUser(null,"user3","1234",new ArrayList<>()));
//			appUserInterface.AddUser(new AppUser(null,"user4","1234",new ArrayList<>()));
//			appUserInterface.AddRoleToUser("user1","AGENT");
//			appUserInterface.AddRoleToUser("admin","ADMIN");
//			appUserInterface.AddRoleToUser("user2","ENSEIGNANT");
//			appUserInterface.AddRoleToUser("user3","ETUDIANT");
//			appUserInterface.AddRoleToUser("admin","AGENT");
//
////appUserRepo.deleteAll();
//			System.out.println(appUserInterface.ListUser());
//
//		};
//	}






