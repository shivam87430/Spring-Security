package com.springsecuritykm.demo.springsecuritykmdemo.service;

import com.springsecuritykm.demo.springsecuritykmdemo.entity.User;
import com.springsecuritykm.demo.springsecuritykmdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        try{
            user=userRepository.findByUsername(username);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(user==null){
            throw new UsernameNotFoundException("User does not Exists");
        }
        List<SimpleGrantedAuthority> auths=new java.util.ArrayList<SimpleGrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                auths
        );
    }
}
