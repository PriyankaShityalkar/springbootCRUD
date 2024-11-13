package com.maven.controller;

import com.maven.modules.User;
import com.maven.modules.dtos.JwtRequest;
import com.maven.modules.dtos.JwtResponse;
import com.maven.repository.UserRepository;
import com.maven.services.UserService;
import com.maven.services.jwt.CustomerServiceImpl;
import com.maven.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController
{
    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CustomerServiceImpl customerService;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtUtil jwtUtil;

    @PostConstruct
    public void createUser(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user@gmail.com");
        user.setRole("SUPER-ADMIN");
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        userService.createUser(user);
    }

    @PostMapping("/create-normal-user")
    public User createNormalUser(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("image")MultipartFile image) throws IOException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setEnabled(Boolean.TRUE);
        user.setRole("NORMAL-USER");
        user.setImage(image.getBytes());
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }
        UserDetails userDetails = customerService.loadUserByUsername(request.getUsername());
        Optional<User> optionalUser = userRepository.getUsersByUsername(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        JwtResponse response = new JwtResponse();
        if(optionalUser.isPresent()){
            response.setToken(jwt);
            response.setRole(optionalUser.get().getRole());
            response.setUsername(optionalUser.get().getUsername());
            return response;
        }
        return null;
    }

    @GetMapping("/get-user/{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }

    @GetMapping("/get-all-user")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();

    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUserById(@PathVariable("id") Long id)
    {
        userService.deleteUserById(id);
    }

    @PutMapping("/update-user")
    public User updateUserRecord(@RequestParam("id") Long id,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("image")MultipartFile image) throws IOException {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setEnabled(Boolean.TRUE);
        user.setRole("NORMAL-USER");
        user.setImage(image.getBytes());
        return userService.createUser(user);
    }
}
