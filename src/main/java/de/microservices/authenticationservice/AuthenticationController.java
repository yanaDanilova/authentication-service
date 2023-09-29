package de.microservices.authenticationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

/*
    {
        "username": "admin",
            "password": "200",
            "roles": [
        "ROLE_ADMIN"
  ]
    }

    {
        "username": "user",
            "password": "100",
            "roles": [
        "ROLE_USER"
  ]
    }
*/
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Credentials credentials) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(credentials));
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authenticationService.authenticate(credentials));
    }

}
