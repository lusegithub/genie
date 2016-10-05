package com.STM.service.impl;

import com.STM.DAO.WishDao;
import com.STM.model.Wish;
import com.STM.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
@Transactional
@Service
public class WishServiceImpl implements WishService{
    @Autowired
    private WishDao wishDao;

    @Override
    public List<Wish> getWishData(String hql) {
        return wishDao.executeQuery(hql);
    }
}
