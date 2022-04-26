package com.example.RedditClone.service;


import com.example.RedditClone.dto.RegisterRequest;
import com.example.RedditClone.model.User;
import com.example.RedditClone.model.VerificationToken;
import com.example.RedditClone.repository.UserRepository;
import com.example.RedditClone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

//    There is nothing wrong with Field Injection @Autowire objects,
//    but spring recommends that we should use constructor injection whenever possible.
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    //We make the variables final, meaning they have to be instantiated by the constructor,
    //therefore Constructor Level Dependency Injection(DI)
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    //Since we are interacting with DB, it is better to make this method transactional
    @Transactional
    public void signup(RegisterRequest registerRequest){

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());

        // We gonna encode the password with the Bcrypt hashing encoder to prevent password-theft
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setCreated(Instant.now());
        //By default we need to disable the user, as only when they verify the email, their account will be enabled
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);

    }

    private String generateVerificationToken(User user) {
        //we gonna use this token to check if the email verification for the user is already done
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;

    }

}
