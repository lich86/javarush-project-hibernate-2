package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false, exclude = "films")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition = "tinyint")
    Byte categoryId;
    @Column(name = "name", columnDefinition = "varchar(25)")
    String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "film_category",
            joinColumns =  @JoinColumn(name="category_id"),
            inverseJoinColumns= @JoinColumn(name="film_id")
    )
    @ToString.Exclude
    private Set<Film> films = new HashSet<>();
}
