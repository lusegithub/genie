package com.STM.service;

import com.STM.model.Whisper;

import java.util.List;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface WhisperService {
    List<Whisper> getWhisperData(String hql);
}
