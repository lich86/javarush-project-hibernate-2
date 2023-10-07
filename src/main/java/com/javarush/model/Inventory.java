package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "inventory", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", columnDefinition = "mediumint")
    Integer inventoryId;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn (name = "film_id")
    Film film;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "store_id")
    Store store;
}
