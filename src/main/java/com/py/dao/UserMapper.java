package com.py.dao;

import com.py.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2016/12/20.
 */
public interface UserMapper {
    User getById(@Param("id") Long id);
}
