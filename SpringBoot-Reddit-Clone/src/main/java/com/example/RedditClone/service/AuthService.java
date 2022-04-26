package com.example.RedditClone.service;


import com.example.RedditClone.dto.RegisterRequest;
import com.example.RedditClone.exceptions.SpringRedditException;
import com.example.RedditClone.model.NotificationEmail;
import com.example.RedditClone.model.User;
import com.example.RedditClone.model.VerificationToken;
import com.example.RedditClone.repository.UserRepository;
import com.example.RedditClone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Optional;
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
    private final MailService mailService;

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

        //We use the token sent with the url to lookup for which user was the token created and then activate that user
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(),
                "Thank you for signing up to Spring Reddit, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user) {
        //we gonna use this token to check if the email verification for the user is already done
        //UUID randomly generates tokens
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public void verifyAccount(String token) {
        //As we are returning optional here, in case the entity does not exist, we can call the orElseThrow method
        //and throw the custom Exception message as Invalid Token
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));

        //need to use .get() because of type optional
        fetchUserAndEnable(verificationToken.get());
    }

    //Method to enable the user and activate the user account
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        //Powerful use of Java Hibernate, we dont have user info but only user_id in the table
        //but because of mapping done onetoone we can directly access user's functions.
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name: " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
