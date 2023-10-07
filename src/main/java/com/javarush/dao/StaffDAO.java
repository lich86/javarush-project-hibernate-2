package com.javarush.dao;

import com.javarush.model.Staff;
import com.javarush.model.Store;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class StaffDAO extends GenericDAO<Staff>{
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }

    public Staff getManagerByStore(Store store) {
        Query<Staff> query = getCurrentSession().createQuery("from Staff where store = :store", Staff.class);
        query.setParameter("store", store);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
