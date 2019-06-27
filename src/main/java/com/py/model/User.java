package com.py.model;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author PYSASUKE
 * @date 2016/12/20
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String email;
}
