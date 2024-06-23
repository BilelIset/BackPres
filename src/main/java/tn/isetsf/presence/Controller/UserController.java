package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import tn.isetsf.presence.Entity.Resp;
import tn.isetsf.presence.Entity.Users;
import tn.isetsf.presence.Repository.UserRepo;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    @Autowired
    private UserRepo userRepo;



    @PostMapping(value = "/login/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users addUser(@RequestBody Users users) {
        try {
            userRepo.save(users);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/login/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getAll() {
        return userRepo.findAll();
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users updateUser(@RequestParam int idUser, @RequestBody Users user) {
        Optional<Users> us = userRepo.findById(idUser);
        if (us.isPresent()) {
            Users updatedUser = us.get();
            updatedUser.setLogin(user.getLogin());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setIsAdmin(user.getIsAdmin());
            userRepo.save(updatedUser);
            return updatedUser;
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "/login/delete")
    public Boolean deleteUser(@RequestParam int idUser) {
        userRepo.deleteById(idUser);
        return userRepo.findById(idUser).isEmpty();
    }



    @PostMapping("/login")
    public Resp logUser(@RequestBody Users user) {
        Resp res = new Resp();
        res.setAdmin(false);
        res.setStatue(false);

        Optional<Users> us1 = userRepo.findByLoginAndPassword(user.getLogin(), user.getPassword());

        if (us1.isPresent()) {
            Users foundUser = us1.get();
            if (foundUser.getIsAdmin()) {
                res.setAdmin(true);
                res.setStatue(true);
            } else {
                res.setStatue(true);
            }
        }
        return res;
    }
}
