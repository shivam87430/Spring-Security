package com.springsecuritykm.demo.springsecuritykmdemo.repository;

import com.springsecuritykm.demo.springsecuritykmdemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
}
