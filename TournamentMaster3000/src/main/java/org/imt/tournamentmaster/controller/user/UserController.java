package org.imt.tournamentmaster.controller.user;

import org.imt.tournamentmaster.model.auth.User;
import org.imt.tournamentmaster.model.auth.UserDto;
import org.imt.tournamentmaster.service.auth.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    IUserService userService;


    @GetMapping("/register")
    public ResponseEntity<User> Register(final UserDto accountDto) {
        final User registered = userService.registerNewUserAccount(accountDto);
        return ResponseEntity.ok().body(registered);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> Login() {
        return ResponseEntity.ok().build();
    }
}

