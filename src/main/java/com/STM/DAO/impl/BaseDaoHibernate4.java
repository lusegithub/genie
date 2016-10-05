package com.STM.DAO.impl;

import com.STM.DAO.BaseDao;
import com.STM.util.Exception.DeleteNullEntityException;
import org.hibernate.*;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blaine on 2016/7/17.
 */
@Component
@Transactional
public class BaseDaoHibernate4<T> extends HibernateDaoSupport implements BaseDao<T> {

    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }

    @Override
    public T get(Class<T> entityClazz, Serializable id) {
        return getHibernateTemplate().get(entityClazz, id);
    }

    @Override
    public Serializable save(T entity) {
        return getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(T entity) throws StaleStateException {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void delete(Class<T> entityClazz, Serializable id) throws DeleteNullEntityException {
        Object o = get(entityClazz, id);
        if (o == null) {
            throw new DeleteNullEntityException("无此信息");
        } else {
            getHibernateTemplate().delete(o);
        }
    }

    @Override
    public void delete(Class<T> entityClazz, HashMap<String, Object> params) {
        List<T> list=get(entityClazz,params);
        if(list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                getHibernateTemplate().delete(list.get(i));
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public  List<T> get(final Class<T> entityClazz, final HashMap<String, Object> params) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            public List<?> doInHibernate(Session session) throws HibernateException{
                Criteria criteria = session.createCriteria(entityClazz);
                for (String field : params.keySet())
                    criteria.add(Property.forName(field).eq(params.get(field)));
                return criteria.list();
            }
        });
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(Class<T> entityClazz) {
        return (List<T>)getHibernateTemplate().find("select en from "
                + entityClazz.getSimpleName() + " en");
    }

    @Override
    @SuppressWarnings("unchecked")
    public long findCount(Class<T> entityClazz) {
        List<Long> list = (List<Long>)getHibernateTemplate().find("select count(*) from "
                + entityClazz.getSimpleName());
        return list.get(0);
    }

    @Override
    public List<T> executeQuery(final String hql) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                return query.list();
            }
        });
    }

    /*
    * @param hql 需要查询的hql语句
    * @param pageNo 需要查询的页
    * @param pageSize 每页显示多少记录
    * @return 当前页的所有记录
    * */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByPage(final String hql, final int pageNo, final int pageSize) {

        List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                List<T> result = session.createQuery(hql)
                        .setFirstResult((pageNo-1) * pageSize)
                        .setMaxResults(pageSize)
                        .list();
                return result;
            }
        });
        return list;
    }

    /*
    * @param hql 需要查询的hql语句
    * @param pageNo 需要查询的页
    * @param pageSize 每页显示多少记录
    * @param params 若hql带占位符参数，params用于传入占位符
    * @return 当前页的所有记录
    * */
    @SuppressWarnings("unchecked")
    public List<T> findByPage(final String hql, final int pageNo , final int pageSize, final Object... params) {

        List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                for (int i = 0, len = params.length; i < len; i++ ) {
                    query.setParameter(i + "", params[i]);
                }
                List<T> result = query.setFirstResult((pageNo - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list();
                return result;
            }
        });
        return list;
    }
}
