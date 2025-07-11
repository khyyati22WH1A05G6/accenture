package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.Map;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepo;

    @Transactional
    public Map<String, String> signup(User user) throws Exception {
        if (userRepo.emailExists(user.getEmail())) {
            throw new Exception("Email already registered");
        }

        userRepo.save(user);  // moved persist() into repository
        return Map.of("message", "Signup successful");
    }

    public Map<String, Object> login(User user) throws Exception {
        User dbUser = userRepo.findByEmail(user.getEmail());

        if (dbUser == null) {
            throw new Exception("User not found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new Exception("Invalid password");
        }

        return Map.of(
            "message", "Login successful",
            "user", Map.of(
                "userId", dbUser.getId(),
                "email", dbUser.getEmail()
            )
        );
    }
}
