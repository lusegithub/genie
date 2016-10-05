package com.STM.DAO;

import org.hibernate.StaleStateException;
import org.hibernate.dialect.H2Dialect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Blaine on 2016/7/17.
 */
public interface BaseDao<T> {
    // load entity by id
    T get(Class<T> entityClazz, Serializable id);
    //load entity by field
    List<T> get(Class<T> entityClazz, HashMap<String, Object> params);
    // save entity
    Serializable save(T entity);
    // update entity
    void update(T entity);
    // delete entity
    void delete(T entity) throws StaleStateException;
    // delete entities by id
    void delete(Class<T> entityClazz, Serializable id);
    //delete entities by field
    void delete(Class<T> entityClazz, HashMap<String, Object> params);
    // get all entities by id
    List<T> findAll(Class<T> entityClazz);
    List<T> findByPage(final String hql, final int pageNo, final int pageSize);
    // get count of entities
    long findCount(Class<T> entityClazz);

    List<T> executeQuery(final String hql);
}
