package com.lc.test.springboot.config;

import com.lc.springboot.user.mapper.UserMapper;
import com.lc.springboot.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author liangc
 * @version V1.0
 * @date 2020/10/21
 **/
@Configuration
public class TestUserConfig {
    @Autowired
    UserMapper userMapper;

    @PostConstruct
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
