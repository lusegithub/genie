package com.STM.util.Exception;

/**
 * Created by pisiyuan on 16/7/26.
 */
public class DeleteNullEntityException extends IllegalArgumentException {
    public DeleteNullEntityException(String msg) {
        super(msg);
    }
}
