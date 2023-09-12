package com.example.monitoring.repository;

import com.example.monitoring.domain.Role;
import com.example.monitoring.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    //SELECT
    Optional<User> findByEmail(String email); /* Login */
    Optional<User> findByKey(String key); /* User-Edit GET */

    List<User> findUsersByName(String name);

    List<User> findUsersByNameNRole(String name, Role role);
    List<User> findAll();

    //INSERT INTO
    boolean save(User user);    /* Sign-Up */

    //UPDATE
    boolean updateUserByUniqueKey(User user);/* User-Edit POST */

    void updateUserRoleByKey(String key, Role role);

    //DELETE
    void deleteUserByKey(String key);
}