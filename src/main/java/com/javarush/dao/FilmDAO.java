package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getByTitle(String title) {
        Query<Film> query = getCurrentSession().createQuery(SQLConstants.selectFilmByTitle, Film.class);
        query.setParameter("title", title);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
