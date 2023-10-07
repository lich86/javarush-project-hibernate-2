package com.javarush.constants;

public class SQLConstants {
    public static final String selectAvailableInventory = """
            with returned_inventory as (
                select r.inventory_id from rental r
                group by r.inventory_id
                having count(r.rental_date) = count(r.return_date))
            select i.* from returned_inventory ri
                                left join inventory i on i.inventory_id = ri.inventory_id
                       where i.film_id = ?
            union
            select i.* from inventory i
            where i.inventory_id not in (select distinct r.inventory_id from rental r) and i.film_id = ?
            """;

    public static final String selectCityByName = "from City where name = :cityName and country.name = :countryName";

    public static final String selectUnfinishedRentalbyInventory = "from Rental where inventory = :inventory and returnDate is null";

    public static final String selectLanguageByName = "from Language where name = :name";
    public static final String selectCustomerByName = "from Customer where firstName = :firstName and lastName = :lastName";
    public static final String selectFilmByTitle = "from Film where title = :title";
    public static final String selectCategoryByName = "from Category where name = :name";
    public static final String selectActorByName = "from Actor where firstName = :firstName and lastName = :lastName";
}
