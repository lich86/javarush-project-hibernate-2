package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "address", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", columnDefinition = "smallint")
    Integer addressId;
    @Column(name = "address", columnDefinition = "varchar(50)")
    String address;
    @Column(name = "address2", columnDefinition = "varchar(50)")
    String address2;
    @Column(name = "district", columnDefinition = "varchar(20)")
    String district;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "city_id")
    City city;
    @Column(name = "postal_code", columnDefinition = "varchar(10)")
    String postalCode;
    @Column(name = "phone", columnDefinition = "varchar(20)")
    String phone;

}
