package puttipat.ketpupong.UserSystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puttipat.ketpupong.UserSystem.entity.ERole;
import puttipat.ketpupong.UserSystem.entity.Role;
import puttipat.ketpupong.UserSystem.repository.RoleRepository;
import puttipat.ketpupong.UserSystem.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        log.info("Save "+ role.getRoleId());
        return roleRepository.save(role);
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    public Role getRoleByString(String name) {
        if(name == "admin") return roleRepository.findByName(ERole.ROLE_ADMIN).get();
        if(name == "mod") return roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        return roleRepository.findByName(ERole.ROLE_USER).get();
    }
}
