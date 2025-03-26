package ru.feolex.ControllerLayer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.feolex.SecurityLayer.Security.*;

@RestController
@Component
@RequestMapping("/api/auth")
@PreAuthorize("hasAuthority('user:crudOwnCats')")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final ApiUserService apiUserService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ApiUserService apiUserService) {
        this.authenticationManager = authenticationManager;
        this.apiUserService = apiUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiUser> login() {
//        Authentication auth = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(auth);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        System.out.println(auth.getName());

        ApiUser apiUser = apiUserService.findByEmail(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User with name/email:" + user.getUsername() + "not found!"));
        return ResponseEntity.ok(apiUser);
    }
}
