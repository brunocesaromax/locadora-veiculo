package com.locadoraveiculo.locadoraveiculo.dao.hibernate;

import com.locadoraveiculo.locadoraveiculo.dao.GenericDAO;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    @PersistenceContext
    private EntityManager manager;
    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public HibernateGenericDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public T findById(ID id) {
        return manager.find(entityClass, id);
    }

    @Override
    public T save(T entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public void delete(ID id) {
        T entity = findById(id);
        manager.remove(entity);
        manager.flush();
    }

    @Override
    public List<T> filter(T entity, String... properties) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Session session = (Session) manager.unwrap(entityClass);
        Criteria criteria = session.createCriteria(entityClass);

        if (properties != null) {
            for (String property : properties) {
                Object value = PropertyUtils.getProperty(entity, property);

                if (value != null) {
                    if (value instanceof String)
                        criteria.add(Restrictions.ilike(property, (String) value, MatchMode.ANYWHERE));
                    else
                        criteria.add(Restrictions.eq(property, value));
                }
            }
        }

        return criteria.list();
    }

    protected EntityManager getEntityManager(){
        return manager;
    }
}
