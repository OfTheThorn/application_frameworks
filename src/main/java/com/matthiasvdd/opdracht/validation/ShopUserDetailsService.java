package com.matthiasvdd.opdracht.validation;

import com.matthiasvdd.opdracht.data.repositories.UserRepository;
import com.matthiasvdd.opdracht.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ShopUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(s);
        if(u == null)
            throw new UsernameNotFoundException("No user found with email: " + s);
        return u;
    }
}
