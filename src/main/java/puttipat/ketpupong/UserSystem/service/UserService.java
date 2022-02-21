package puttipat.ketpupong.UserSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import puttipat.ketpupong.UserSystem.dto.User.UpdateUserDto;
import puttipat.ketpupong.UserSystem.entity.ERole;
import puttipat.ketpupong.UserSystem.entity.Role;
import puttipat.ketpupong.UserSystem.entity.User;
import puttipat.ketpupong.UserSystem.payload.request.SignUpRequest;
import puttipat.ketpupong.UserSystem.payload.response.MessageResponse;
import puttipat.ketpupong.UserSystem.repository.RoleRepository;
import puttipat.ketpupong.UserSystem.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public User createUser(SignUpRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Error: Email is already taken!");
        }

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
            roles.add(userRole);
        }
        else {
            strRoles.forEach( role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
                        break;
                    case "mod":
                        roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).get());
                        break;
                    default:
                        roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
                        break;
                }
            });
        }

        User user = new User(request.getUsername(), encoder.encode(request.getPassword()),
                request.getFirstName(), request.getLastName(), request.getEmail(),
                request.getPhoneNumber(), roles);

        log.info("Save "+ user.getUserId());
        return userRepository.save(user);
    }
//    public User updateUser(UpdateUserDto user) {
//        This doesn't work properly
//        User updatedUser = userRepository.findByUsername(user.getUsername());
//        updatedUser = user;
//        return userRepository.save(updatedUser);
//    }

    public Optional<User> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user == null) return null;
        log.info("Get info of " + user.get().getUserId());
        return user;
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        log.info("Get info of " + user.get().getUserId());
        return user.get();
    }

    public boolean deleteUserById(Long userId){
        userRepository.deleteById(userId);
        log.info("Delete user with UserId:"+ userId);
        return true;
    }

    public void addRoleToUser(Long userId, Long roleId) {
        log.info("Add role:"+roleId+" to user:"+userId);
        User user = userRepository.findById(userId).get();
        Role role = roleRepository.findById(roleId).get();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
