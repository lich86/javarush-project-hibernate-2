package com.javarush;

import com.javarush.model.*;
import org.hibernate.Session;
import com.javarush.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    private final SessionFactory sessionFactory;

    public Main() {
        sessionFactory = MySessionFactory.getSessionFactory();
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createCustomer();
    }

    public Customer createCustomer() {
        Customer customer;
        try(Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Please enter your store number (1 or 2):");
                Store store = storeDAO.getByID(Integer.parseInt(reader.readLine()));

                if (store == null) {
                    System.out.println("Store not found");
                    transaction.rollback();
                    throw new IOException("Store not found");
                }

                System.out.println("Please enter customer's country name");
                String countryName = reader.readLine();
                System.out.println("Please enter customer's city name:");
                String cityName = reader.readLine();
                City city = cityDAO.getByName(cityName, countryName);

                if (city == null) {
                    System.out.println("City not found");
                    transaction.rollback();
                    throw new IOException("City not found");
                }

                Address address = new Address();
                address.setCity(city);
                System.out.println("Please enter address:");
                address.setAddress(reader.readLine());
                System.out.println("Please enter district:");
                address.setDistrict(reader.readLine());
                System.out.println("Please enter postal code:");
                address.setPostalCode(reader.readLine());
                System.out.println("Please enter phone number");
                address.setPhone(reader.readLine());

                customer = new Customer();
                customer.setAddress(address);
                customer.setIsActive(true);
                customer.setStore(store);
                System.out.println("Please enter customer's first name");
                customer.setFirstName(reader.readLine());
                System.out.println("Please enter customer's last name");
                customer.setLastName(reader.readLine());
                System.out.println("Please enter customer's email");
                customer.setEmail(reader.readLine());

                } catch (IOException e) {
                throw new RuntimeException(e);
            }
            transaction.commit();

        }
        return customer;
    }


}
