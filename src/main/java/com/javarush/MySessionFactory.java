package com.javarush;

import com.javarush.constants.DBConstants;
import com.javarush.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.util.Properties;

public class MySessionFactory {
    private static MySessionFactory instance;
    private final SessionFactory sessionFactory;
    private MySessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DBConstants.DB_DRIVER);
        properties.put(Environment.URL, DBConstants.DB_URL);
        properties.put(Environment.DIALECT, DBConstants.DB_DIALECT);
        properties.put(Environment.USER, DBConstants.DB_USER);
        properties.put(Environment.PASS, DBConstants.DB_PASS);
        properties.put(Environment.HBM2DDL_AUTO, DBConstants.DB_HBM2DDL_AUTO);
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, DBConstants.DB_CURRENT_SESSION_CONTEXT_CLASS);
        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new MySessionFactory();
        }
        return instance.sessionFactory;
    }
}