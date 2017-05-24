package com.py.service.impl;

import com.py.dao.UserMapper;
import com.py.model.User;
import com.py.service.UserServicre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pysasuke on 2017/5/23.
 */
@Service("userService")
public class UserServiceImpl implements UserServicre {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

}
