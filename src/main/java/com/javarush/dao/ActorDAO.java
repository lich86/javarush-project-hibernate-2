package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Actor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ActorDAO extends GenericDAO<Actor>{
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }

    public Actor getByName(String firstName, String lastName) {
        Query<Actor> query = getCurrentSession().createQuery(SQLConstants.selectActorByName, Actor.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getSingleResult();
    }
}
