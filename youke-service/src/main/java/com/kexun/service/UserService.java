package com.kexun.service;

import com.kexun.mapper.UserMapper;
import com.kexun.pojo.Login;
import com.kexun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    public User login(Login login) {
        return userMapper.login(login);
    }

    public boolean register(User user) {
        int i = userMapper.insertSelective(user);
        return i > 0 ? true : false;
    }

    public int findUserByUsername( String username) {
        System.out.println(username);
        return userMapper.findUserByUsername(username);
    }


}
