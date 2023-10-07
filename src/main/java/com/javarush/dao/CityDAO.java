package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDAO extends GenericDAO<City>{
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String cityName, String countryName) {
        Query<City> query = getCurrentSession().createQuery(SQLConstants.selectCityByName, City.class);
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        return query.getSingleResult();
    }
}
