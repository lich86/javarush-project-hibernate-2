package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CustomerDAO extends GenericDAO<Customer>{
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer getByName(String firsName, String lastName) {
        Query<Customer> query = getCurrentSession().createQuery(SQLConstants.selectCustomerByName, Customer.class);
        query.setParameter("firstName", firsName);
        query.setParameter("lastName", lastName);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
