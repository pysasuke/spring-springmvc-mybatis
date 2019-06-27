package com.py.dao;

import com.py.model.User;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author PYSASUKE
 * @date 2016/12/20
 */
public interface UserMapper {
    User getById(@Param("id") Long id);
}
