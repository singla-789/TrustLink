package com.Singla.TrustLink_backend.Controller;

import com.Singla.TrustLink_backend.Dto.LoginRequest;
import com.Singla.TrustLink_backend.Dto.RegisterRequest;
import com.Singla.TrustLink_backend.Service.UserService;
import com.Singla.TrustLink_backend.modles.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.authenticateUser(request));
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole( "ROLE_USER");
        User registerdUser = userService.registerUser(user);

        return ResponseEntity.ok("User created successfully : " + registerdUser);
    }
}
