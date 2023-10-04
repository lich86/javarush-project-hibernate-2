package com.javarush.dao;

import com.javarush.model.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<Staff>{
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
