package com.maven.services.jwt;

import com.maven.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    public CustomerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String userName)
    {
        Optional<com.maven.modules.User> usersByUserName = userRepository.getUsersByUsername(userName);
        if(usersByUserName.isEmpty())
        {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not found");
        }
        return new User(usersByUserName.get().getUsername(),usersByUserName.get().getPassword(),usersByUserName.get().getAuthorities());
    }
    }

