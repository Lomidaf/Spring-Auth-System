package puttipat.ketpupong.UserSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puttipat.ketpupong.UserSystem.entity.ERole;
import puttipat.ketpupong.UserSystem.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
