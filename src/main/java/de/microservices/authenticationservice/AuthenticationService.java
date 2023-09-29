package de.microservices.authenticationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Autowired
    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public JwtTokenResponse register(Credentials credentials) {
        User user = new User(credentials.getUsername(), passwordEncoder.encode(credentials.getPassword()),credentials.getRoles());
        user.setRoles(credentials.getRoles());
        System.out.println(credentials.getRoles());//string [ROLE_ADMIN]
        repository.save(user);
        System.out.println(user.getAuthorities()); // integers [[82, 79, 76, 69, 95, 65, 68, 77, 73, 78]] //must be strings
        var jwtToken = jwtService.generateToken(user);

        return new JwtTokenResponse(jwtToken);
    }



    public JwtTokenResponse authenticate(Credentials credentials) {
        var user = repository.findByUsername(credentials.getUsername())
                .orElseThrow();
        if (passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            var jwtToken = jwtService.generateToken(user);
            return new JwtTokenResponse(jwtToken);
        }
        throw new BadCredentialsException("Incorrect username or password");
    }
}
