package com.STM.DAO.impl;

import com.STM.DAO.UserDao;
import com.STM.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by chenjunkai on 16-12-3.
 */
@Repository
public class UserDaoImpl extends BaseDaoHibernate4<User> implements UserDao{
}
