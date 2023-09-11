package com.example.monitoring.service;

import com.example.monitoring.domain.ResponseMesssage;
import com.example.monitoring.domain.User;
import com.example.monitoring.repository.IUserRepository;
import org.apache.coyote.Response;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseMesssage save(User user) {
        boolean result = userRepository.save(user);
        ResponseMesssage msg = new ResponseMesssage();

        if(result) {
            msg.setOk(true);
            msg.setMessage("회원가입에 성공했습니다.");
        } else {
            msg.setOk(false);
            msg.setMessage("회원가입에 실패 했습니다. 다시 회원가입을 시도해 주세요");
        }

        return msg;
    }

    public ResponseMesssage findUserByEmail(String email, String password) {
        Optional<User> result = userRepository.findByEmail(email);
        ResponseMesssage msg = new ResponseMesssage();
        if(result.isPresent()){
            User user = result.orElseGet(() -> new User());
            if(user.getPassword().equals(password)) {
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

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
