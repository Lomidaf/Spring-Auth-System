package puttipat.ketpupong.UserSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puttipat.ketpupong.UserSystem.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Boolean deleteByUsername(String username);
}
