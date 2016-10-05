package com.STM.util;

/**
 * Created by dandan on 2016/7/19.
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ObjectMappingCustomer extends ObjectMapper {
    public ObjectMappingCustomer(){
        super();
        this.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
