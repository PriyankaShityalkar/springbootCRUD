package com.maven.repository;

import com.maven.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> getUsersByUsername(String username);

    User getUserById(Long id);
}
