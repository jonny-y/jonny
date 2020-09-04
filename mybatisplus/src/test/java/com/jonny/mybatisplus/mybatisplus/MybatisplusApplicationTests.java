package com.jonny.mybatisplus.mybatisplus;

import com.jonny.mybatisplus.mybatisplus.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MybatisplusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

}
