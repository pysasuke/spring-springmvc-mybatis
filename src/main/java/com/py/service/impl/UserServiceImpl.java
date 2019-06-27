package com.py.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.UserMapper;
import com.py.model.User;
import com.py.service.UserServicre;

/**
 *
 * @author PYSASUKE
 * @date 2017/5/23
 */
@Service("userService")
public class UserServiceImpl implements UserServicre {
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

}
