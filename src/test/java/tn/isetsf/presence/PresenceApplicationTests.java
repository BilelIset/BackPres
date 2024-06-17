package tn.isetsf.presence;

import net.bytebuddy.agent.VirtualMachine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import tn.isetsf.presence.Controller.UserController;
import tn.isetsf.presence.Entity.Users;
import tn.isetsf.presence.Repository.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PresenceApplicationTests {

	static Users users=new Users("test","test",false);
	@Autowired
	 UserRepo userRepo;


	@Test
	void contextLoads() {
		List<Users> list=userRepo.findAll();
		System.out.println(list.toString());



		Optional<Users> us1 = userRepo.findByLoginAndPassword("bilel","123456");

				if(us1.isPresent()){
					System.out.println(("existe "));
				}else {
					System.out.println(("n'existe pas "));

				}
		Optional<Users> us2 = userRepo.findByLoginAndPassword("bbb","254");

		if(us2.isPresent()){
			System.out.println(("existe "));
		}else {
			System.out.println(("n'existe pas "));

		}
		try {
			userRepo.save(users);
			System.out.println("Inséré avec success");
		}catch (Exception e){
			System.out.println("Erreur utilisateur non inséré");
		}
		apres();


	}



     void apres(){
		try {
			Optional<Users> usTest=userRepo.findById(users.getIdUser());
			if (usTest.isPresent()){
				System.out.println("Utilisateur trouvé "+usTest.get().getIdUser());
				try {
					userRepo.delete(usTest.get());
					System.out.println("Supprimer avec success");

				}catch (Exception e){
					System.out.println("Erreur Utilisateur non trouvé");
				}

			}else {
				System.out.println("Utilisateur non trouvé");
			}
		}catch (Exception e){
			System.out.println("erreur utilisateur n'est pas inséré");
		}


	}

}
