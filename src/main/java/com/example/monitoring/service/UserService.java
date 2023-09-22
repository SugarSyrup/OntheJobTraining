package com.example.monitoring.service;

import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.domain.Role;
import com.example.monitoring.domain.User;
import com.example.monitoring.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseMessage signup(User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean result = userRepository.save(user);
        ResponseMessage msg = new ResponseMessage();

        if(result) {
            msg.setOk(true);
            msg.setMessage("회원가입에 성공했습니다.");
        } else {
            msg.setOk(false);
            msg.setMessage("회원가입에 실패 했습니다. 다시 회원가입을 시도해 주세요");
        }

        return msg;
    }

    public ResponseMessage login(String email, String password) {
        Optional<User> result = userRepository.findByEmail(email);
        ResponseMessage msg = new ResponseMessage();
        if(result.isPresent()){
            User user = result.orElseGet(() -> new User());
            if(passwordEncoder.matches(password, user.getPassword())) {
                msg.setMessage(Integer.toString(user.getUserNo()));
                msg.setOk(true);
            }
            else {
                msg.setMessage("Password Not Matched!");
                msg.setOk(false);
            }
        } else {
            msg.setMessage("Email Not Founded!");
            msg.setOk(false);
        }

        return msg;
    }

    public boolean findAdminById(String id) {
        Optional<User> result = userRepository.findByKey(id);
        if(result.isPresent()) {
            User user = result.orElseGet(() -> new User());
            if(Role.ADMIN.equals(user.getRole())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public User findUserByKey(String key) {
        Optional<User> result = userRepository.findByKey(key);
        if(result.isPresent()) {
            User user = result.orElseGet(() -> new User());
            return user;
        }

        return null;
    }

    public List<User> findUsersByName(String name) {
        return userRepository.findUsersByName(name);
    }

    public List<User> findUsersByNameNRole(String name, Role role) {
        return userRepository.findUsersByNameNRole(name, role);
    }

    public boolean findDuplicatedEmail(String email) {
        return userRepository.findDuplicatedEmail(email);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean result = userRepository.updateUserByUniqueKey(user);

        if(result) {
            return true;
        }
        return false;
    }

    public void deleteUserByKey(String key) {
        userRepository.deleteUserByKey(key);
    }

    public void updateUserRole(String key, String role) {
        Role updateRole = Role.valueOf(role);

        userRepository.updateUserRoleByKey(key, updateRole);
    }
}
