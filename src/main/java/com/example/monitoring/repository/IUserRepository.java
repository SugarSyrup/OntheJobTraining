package com.example.monitoring.repository;

import com.example.monitoring.domain.UserRole;
import com.example.monitoring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository {

    //SELECT
    Optional<User> findByEmail(String email); /* Login */
    Optional<User> findByKey(String key); /* User-Edit GET */

    List<User> findUsersByName(String name);

    List<User> findUsersByNameNRole(String name, UserRole userRole);
    List<User> findAll();

    boolean findDuplicatedEmail(String email);

    //INSERT INTO
    boolean save(User user) throws Exception ;    /* Sign-Up */

    //UPDATE
    boolean updateUserByUniqueKey(User user);/* User-Edit POST */

    void updateUserRoleByKey(String key, UserRole userRole);

    //DELETE
    void deleteUserByKey(String key);
}