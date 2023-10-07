package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CategoryDAO extends GenericDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }

    public Category getCategoryByName(String name) {
        Query<Category> query = getCurrentSession().createQuery(SQLConstants.selectCategoryByName, Category.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
