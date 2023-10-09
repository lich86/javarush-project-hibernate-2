package com.javarush;

import com.javarush.model.*;
import org.hibernate.Session;
import com.javarush.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class Main {
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
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

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Main() {
        sessionFactory = MySessionFactory.getSessionFactory();
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
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

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.addCustomer();
        main.returnRentedInventory();
        main.rentInventory();
        main.addFilm();
        main.reader.close();
    }

    public Customer addCustomer() {
        Customer customer;
        try(Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                System.out.println("Please enter your store number (1 or 2):");
                Store store = storeDAO.getByID(Integer.parseInt(reader.readLine()));

                if (isNull(store)) {
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

                addressDAO.save(address);

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

                customerDAO.save(customer);

                } catch (IOException e) {
                throw new RuntimeException(e);
            }
            transaction.commit();

        }
        return customer;
    }

    public void returnRentedInventory() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                System.out.println("Please enter inventory id:");
                int inventoryId = Integer.parseInt(reader.readLine());
                Inventory inventory = inventoryDAO.getByID(inventoryId);
                rentalDAO.returnRentedInventory(inventory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            transaction.commit();
        }
    }


    public void rentInventory() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                System.out.println("Please enter customer's first name");
                String firstName = reader.readLine();
                System.out.println("Please enter customer's last name");
                String lastName = reader.readLine();

                Customer customer = customerDAO.getByName(firstName, lastName);
                if(isNull(customer)) {
                    customer = addCustomer();
                }

                Store store = customer.getStore();
                Staff staff = staffDAO.getManagerByStore(store);

                System.out.println("Please enter film's title:");
                Film film = filmDAO.getByTitle(reader.readLine());
                int filmId = film.getFilmId();
                Inventory inventory = inventoryDAO.getByFilmId(filmId);

                if(isNull(inventory)) {
                    System.out.println("This film is not available");
                    transaction.rollback();
                    return;
                }

                Rental rental = new Rental();
                rental.setInventory(inventory);
                rental.setCustomer(customer);
                rental.setStaff(staff);
                rental.setRentalDate(LocalDateTime.now());

                rentalDAO.save(rental);

                Payment payment = new Payment();
                payment.setStaff(staff);
                payment.setCustomer(customer);
                payment.setRental(rental);
                payment.setPaymentDate(LocalDateTime.now());
                payment.setAmount(film.getRentalRate()*film.getRentalDuration());

                paymentDAO.save(payment);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            transaction.commit();
        }
    }

    public void addFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Film film = new Film();
            try {

                System.out.println("Please enter film title:");
                film.setTitle(reader.readLine());

                System.out.println("Please enter film description:");
                film.setDescription(reader.readLine());

                System.out.println("Please enter film release year:");
                film.setReleaseYear(Integer.parseInt(reader.readLine()));

                System.out.println("Please enter film language:");
                Language language = languageDAO.getByName(reader.readLine());
                if(isNull(language)) {
                    transaction.rollback();
                    throw new NullPointerException("language not found");
                }
                film.setLanguage(language);

                System.out.println("Please enter film original language:");
                Language originalLanguage = languageDAO.getByName(reader.readLine());
                film.setOriginalLanguage(originalLanguage);

                System.out.println("Please enter rental duration:");
                film.setRentalDuration(Byte.parseByte(reader.readLine()));

                System.out.println("Please enter rental rate:");
                film.setRentalRate(Double.parseDouble(reader.readLine()));

                System.out.println("Please enter film length:");
                film.setLength(Short.parseShort(reader.readLine()));

                System.out.println("Please enter replacement cost:");
                film.setReplacementCost(Double.parseDouble(reader.readLine()));

                System.out.println("Please enter rating (G, PG, PG-13, R, NC-17):");
                film.setRating(Rating.getByValue(reader.readLine()));

                System.out.println("Please enter special features (Trailers, Commentaries, Deleted Scenes, Behind the Scenes):");
                String specialFeatures = reader.readLine();
                Set<SpecialFeature> specialFeatureSet = Stream.of(specialFeatures.split(",", -1))
                        .map(String::trim)
                        .map(SpecialFeature::getFeatureByValue)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                film.setSpecialFeatures(specialFeatureSet);

                System.out.println("Please enter categories (Action, Animation, Children):");
                String categories = reader.readLine();
                Set<Category> categorySet = Stream.of(categories.split(",", -1))
                        .map(String::trim)
                        .map(categoryDAO::getCategoryByName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                film.setCategories(categorySet);

                System.out.println("Please enter actor's names (PENELOPE GUINESS, NICK WAHLBERG, ED CHASE):");
                String actors = reader.readLine();
                Set<Actor> actorSet = Stream.of(actors.split(",", -1))
                        .map(String::trim)
                        .map(name -> name.split(" ", -1))
                        .map(arr -> actorDAO.getByName(arr[0], arr[1]))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                film.setActors(actorSet);

                filmDAO.save(film);

                System.out.println("Please enter your store number (1 or 2):");
                Store store = storeDAO.getByID(Integer.parseInt(reader.readLine()));

                Inventory inventory = new Inventory();
                inventory.setStore(store);
                inventory.setFilm(film);
                inventoryDAO.save(inventory);

                FilmText filmText = new FilmText();
                filmText.setFilm(film);
                filmText.setTitle(film.getTitle());
                filmText.setDescription(film.getDescription());

                filmTextDAO.save(filmText);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            transaction.commit();
        }
    }


}
