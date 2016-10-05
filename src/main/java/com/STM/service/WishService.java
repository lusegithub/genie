package com.STM.service;

import com.STM.model.Wish;

import java.util.List;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public interface WishService {
    List<Wish> getWishData(String hql);
}
