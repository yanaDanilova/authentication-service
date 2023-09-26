package de.microservices.authenticationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Credentials credentials) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(credentials));
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authenticationService.authenticate(credentials));
    }

}
