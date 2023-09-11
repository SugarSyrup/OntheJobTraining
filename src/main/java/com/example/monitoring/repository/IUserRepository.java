package com.example.monitoring.repository;

import com.example.monitoring.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByEmail(String email);

    boolean save(User user);


    List<User> findAll();
}