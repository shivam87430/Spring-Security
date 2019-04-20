package com.springsecuritykm.demo.springsecuritykmdemo.controller;

import com.springsecuritykm.demo.springsecuritykmdemo.entity.User;
import com.springsecuritykm.demo.springsecuritykmdemo.entity.VerificationToken;
import com.springsecuritykm.demo.springsecuritykmdemo.repository.UserRepository;
import com.springsecuritykm.demo.springsecuritykmdemo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register/user")
    public String registeredUser(User user, HttpServletRequest httpServletRequest){
        String token= UUID.randomUUID().toString();
        user.setEnabled(false);
        userRepository.save(user);
        String authUrl="http://"+httpServletRequest.getServerName()
                +":"
                +httpServletRequest.getServerPort()
                +httpServletRequest.getContextPath()
                +"/register/confirmation"
                +"?token="+token;
        System.out.println(authUrl);
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return "login";
    }

    @RequestMapping("/register/confirmation")
    @ResponseBody
    public String confirmRegistration(String token){
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        User user=verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken.getId());
        return "Confirm Register";
    }
}
