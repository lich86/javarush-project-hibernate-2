package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Language;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class LanguageDAO extends GenericDAO<Language> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }

    public Language getByName(String name) {
        Query<Language> query = getCurrentSession().createQuery(SQLConstants.selectLanguageByName, Language.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
