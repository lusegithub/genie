package com.STM.service.impl;

import com.STM.DAO.WhisperDao;
import com.STM.model.Whisper;
import com.STM.service.WhisperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
@Transactional
@Service
public class WhisperServiceImpl implements WhisperService{

    @Autowired
    private WhisperDao whisperDao;

    @Override
    public List<Whisper> getWhisperData(String hql) {
        return whisperDao.executeQuery(hql);
    }
}
