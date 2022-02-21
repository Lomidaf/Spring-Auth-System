package puttipat.ketpupong.UserSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import puttipat.ketpupong.UserSystem.payload.request.LoginRequest;
import puttipat.ketpupong.UserSystem.payload.response.JwtResponse;
import puttipat.ketpupong.UserSystem.payload.response.MessageResponse;
import puttipat.ketpupong.UserSystem.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response;
        try {
            response = authService.authenticateUser(loginRequest.getUsername(),loginRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MessageResponse(("Your username or password is wrong!")));
        }
        return ResponseEntity.ok(response);
    }
}
