package com.javarush.dao;

import com.javarush.constants.SQLConstants;
import com.javarush.model.Inventory;
import com.javarush.model.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public void returnRentedInventory(Inventory inventory) {
        Query<Rental> query = getCurrentSession().createQuery(SQLConstants.selectUnfinishedRentalbyInventory, Rental.class);
        query.setParameter("inventory", inventory);
        Rental singleResult = query.getSingleResult();
        if (isNull(singleResult)) {
            throw new IllegalArgumentException("Inventory not found");
        }
        singleResult.setReturnDate(LocalDateTime.now());
        update(singleResult);
    }
}
