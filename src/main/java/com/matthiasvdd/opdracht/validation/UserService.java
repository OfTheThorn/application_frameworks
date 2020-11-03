package com.matthiasvdd.opdracht.validation;

import com.matthiasvdd.opdracht.data.repositories.UserRepository;
import com.matthiasvdd.opdracht.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User registerUser(User u) throws Exception {
        System.out.println("Register user");
        if(emailExists(u.getEmail()))
            throw new Exception("There is an account with email address: "
                    + u.getEmail());
        String encrypted = bCryptPasswordEncoder.encode(u.getPassword());
        u.setConfirmPassword(encrypted);
        u.setPassword(encrypted);
        return userRepository.save(u);
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
