package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Film;
import com.javarush.model.Inventory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class InventoryDAO extends GenericDAO<Inventory> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public Inventory getByFilmId(int id) {
        Query<Inventory> query = getCurrentSession().createNativeQuery(SQLConstants.selectAvailableInventory, Inventory.class);
        query.setParameter(1, id);
        query.setParameter(2, id);
        return query.getResultList().get(0);
    }
}
