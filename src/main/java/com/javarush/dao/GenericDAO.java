package com.javarush.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> clazz;
    private SessionFactory sessionFactory;

    public GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getByID(int id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int pageNumber, int pageSize) {
        Query<T> query = getCurrentSession().createQuery("FROM " + clazz.getName(), clazz);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<T> getAll() {
        return getCurrentSession().createQuery("FROM " + clazz.getName(), clazz).getResultList();
    }

    public void save(final T entity) {
        getCurrentSession().persist(entity);
    }

    public void update(final T entity) {
        getCurrentSession().merge(entity);
    }

    public void delete(final  T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(int id) {
        T entity = getByID(id);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
