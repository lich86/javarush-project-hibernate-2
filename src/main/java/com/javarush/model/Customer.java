package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "smallint")
    Integer customerId;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "store_id")
    Store store;
    @Column(name = "first_name", columnDefinition = "varchar(45)")
    String firstName;
    @Column(name = "last_name", columnDefinition = "varchar(45)")
    String lastName;
    @Column(name = "email", columnDefinition = "varchar(50)")
    String email;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    Address address;
    @Column(name= "active", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean isActive;
    @Column(name = "create_date", columnDefinition = "datetime")
    @CreationTimestamp
    LocalDateTime createTime;

}
