package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "staff", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", columnDefinition = "tinyint")
    Integer staffId;
    @Column(name = "first_name", columnDefinition = "varchar(45)")
    String firstName;
    @Column(name = "last_name", columnDefinition = "varchar(45)")
    String lastName;
    @ManyToOne
    @JoinColumn(name = "address_id")
    Address address;
    @Lob
    @Column(name = "picture", columnDefinition="BLOB")
    byte[] picture;
    @Column(name = "email", columnDefinition = "varchar(50)")
    String email;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "store_id")
    Store store;
    @Column(name= "active", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean isActive;
    @Column(name = "username", columnDefinition = "varchar(16)")
    String username;
    @Column(name = "password", columnDefinition = "varchar(40)")
    String password;
}
