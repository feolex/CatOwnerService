package ru.feolex.ControllerLayer.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.feolex.SecurityLayer.Security.ApiUser;
import ru.feolex.SecurityLayer.Security.Role;

import java.security.Principal;

@Controller
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/check")
    public String check(Principal principal){
        if(principal == null){
            return "checkAll";
        }
        return principal.getName();
    }

    @GetMapping("/getExample")
    public ResponseEntity<ApiUser> getApiUserExample(){
        ApiUser apiUser = new ApiUser();
//        apiUser.setId((long) 1l);
        apiUser.setEmail("email@email.com");
        apiUser.setPassword("letThinkThisEncodeByBCrypt");
        apiUser.setRole(Role.USER);

        return ResponseEntity.ok(apiUser);
    }
    @GetMapping("/checkAll")
    public String checkAll(Model model) {
        return "checkAll";
    }

    @GetMapping("/checkUser")
    @PreAuthorize("hasAuthority('user:crudOwnCats')")
    public String checkUser(Model model) {
        return "checkUser";
    }

    @GetMapping("/checkModer")
    @PreAuthorize("hasAuthority('user:allActions')")
    public String checkModer(Model model) {
        return "checkModer";
    }

}
