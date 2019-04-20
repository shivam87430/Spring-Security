package com.springsecuritykm.demo.springsecuritykmdemo.repository;

import com.springsecuritykm.demo.springsecuritykmdemo.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Integer> {

    VerificationToken findByToken(String token);
}
