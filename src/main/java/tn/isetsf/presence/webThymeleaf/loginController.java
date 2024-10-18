package tn.isetsf.presence.webThymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tn.isetsf.presence.sec.config.SecurityConfig;

import javax.transaction.Transactional;

@Controller
@Transactional
public class loginController {


    @GetMapping(path = "/login")
    public String logUser(Model model){

        return "/login";
    }
    @GetMapping(path = "/logout")
    public String logOutUser(){
        return"/logout";
    }
}
