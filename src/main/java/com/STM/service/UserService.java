package com.STM.service;

import com.STM.model.User;

import java.util.List;

/**
 * Created by chenjunkai on 16-12-3.
 */
public interface UserService {
    List<User> getUsers(String hql);
}
