package com.jonny.spring.boot.blog.initializrstart.service;

import com.jonny.spring.boot.blog.initializrstart.entity.User;

import java.util.List;

public interface UserService {
    User saveOrUpdateUser(User user);

    void deleteUser(Long id);

    User selectByid(Long id);

    List<User> selectByList();
}
