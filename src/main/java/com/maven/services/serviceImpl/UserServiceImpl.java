package com.maven.services.serviceImpl;

import com.maven.modules.User;
import com.maven.repository.UserRepository;
import com.maven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user)
    {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id)
    {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id)
    {
        User userId = userRepository.getUserById(id);
        userRepository.delete(userId);

    }

    @Override
    public User updateUserRecord(User user)
    {
        return userRepository.save(user);
    }
}
