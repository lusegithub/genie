package com.STM.service.impl;

import com.STM.DAO.UserDao;
import com.STM.model.User;
import com.STM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenjunkai on 16-12-3.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers(String hql) {
        return userDao.executeQuery(hql);
    }
}
