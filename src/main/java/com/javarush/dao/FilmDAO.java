package com.javarush.dao;

import com.javarush.model.Film;
import org.hibernate.SessionFactory;

public class FilmDAO extends GenericDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }
}
