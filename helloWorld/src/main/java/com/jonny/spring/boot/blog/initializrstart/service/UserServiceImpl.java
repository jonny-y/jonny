package com.jonny.spring.boot.blog.initializrstart.service;

import com.jonny.spring.boot.blog.initializrstart.entity.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserServiceImpl implements UserService {
    private static AtomicLong al=new AtomicLong();
    private static final ConcurrentHashMap userMap=new ConcurrentHashMap();
    @Override
    public User saveOrUpdateUser(User user) {
        if (user.getId() == null){
            long id = al.incrementAndGet();//生成递增id
            user.setId(id);
            userMap.put(id,user);
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User selectByid(Long id) {
        return null;
    }

    @Override
    public List<User> selectByList() {
        return null;
    }
}
