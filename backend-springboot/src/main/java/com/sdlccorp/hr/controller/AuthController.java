package com.sdlccorp.hr.controller;

import com.sdlccorp.hr.dto.AuthDtos;
import com.sdlccorp.hr.model.User;
import com.sdlccorp.hr.repo.UserRepo;
import com.sdlccorp.hr.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(UserRepo userRepo, BCryptPasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AuthDtos.SignupRequest req) {
        if (userRepo.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        String hash = encoder.encode(req.password);
        var user = userRepo.save(new User(req.name, req.email, hash));
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token, user.getName(), user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        var userOpt = userRepo.findByEmail(req.email);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid credentials");

        var user = userOpt.get();
        if (!encoder.matches(req.password, user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token, user.getName(), user.getEmail()));
    }
}
