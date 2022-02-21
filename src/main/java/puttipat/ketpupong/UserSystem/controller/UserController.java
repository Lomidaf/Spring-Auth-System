package puttipat.ketpupong.UserSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puttipat.ketpupong.UserSystem.dto.User.AddRoleToUserDto;
import puttipat.ketpupong.UserSystem.dto.User.UpdateUserDto;
import puttipat.ketpupong.UserSystem.entity.User;
import puttipat.ketpupong.UserSystem.payload.request.LoginRequest;
import puttipat.ketpupong.UserSystem.payload.request.SignUpRequest;
import puttipat.ketpupong.UserSystem.payload.response.MessageResponse;
import puttipat.ketpupong.UserSystem.repository.UserRepository;
import puttipat.ketpupong.UserSystem.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user;
        try {
            user = userService.createUser(signUpRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        log.info("Create username:"+user.getUsername()+" into database");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/").toUriString());
        return ResponseEntity.created(uri).body(user);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId ,@RequestBody UpdateUserDto user) {
//        log.info("Update username:"+userId+" into database");
////        return ResponseEntity.ok().body(userService.updateUser(user));
//        return ResponseEntity.badRequest();
//    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long userId) {
        log.info("Delete username:"+userId);
        return ResponseEntity.ok().body(userService.deleteUserById(userId));
    }

    @PostMapping("/")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserDto dto) {
        userService.addRoleToUser(dto.getUserId(), dto.getRoleId());
        return ResponseEntity.ok().body(null);
    }


}
