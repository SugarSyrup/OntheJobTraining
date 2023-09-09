package com.example.monitoring.service;

import com.example.monitoring.domain.Msg;
import com.example.monitoring.domain.User;
import com.example.monitoring.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String findUserByEmail(String email, String password) {
        Optional<User> result = userRepository.findByEmail(email);
        Msg msg = new Msg();
        if(result.isPresent()){
            User user = result.orElseGet(() -> new User());
            if(user.getPassword().equals(password)) {
                return user.getName();
            }
            else {
                return "Password Not Matched!";
            }
        } else {
            return "Email Not Founded!";
        }
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
