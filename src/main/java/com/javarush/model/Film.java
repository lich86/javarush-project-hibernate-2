package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "film", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "smallint")
    Integer filmId;
    @Column(name = "title", columnDefinition = "varchar(128)")
    String title;
    @Column(name = "description", columnDefinition = "text")
    String description;
    @Column(name = "release_year", columnDefinition = "year")
    Integer releaseYear;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "language_id")
    Language language;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "original_language_id", columnDefinition = "tinyint default NULL")
    Language originalLanguage;
    @Column(name = "rental_duration", columnDefinition = "tinyint")
    Byte rentalDuration;
    @Column(name = "rental_rate", columnDefinition = "decimal")
    Double rentalRate;
    @Column(name = "length", columnDefinition = "smallint")
    Short length;
    @Column(name = "replacement_cost", columnDefinition = "decimal")
    Double replacementCost;
    @Convert(converter = RatingConverter.class)
    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    Rating rating;
    @Column(name = "special_features",
            columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    String specialFeatures;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "films")
    @ToString.Exclude
    Set<Category> categories = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "film_category",
            joinColumns =  @JoinColumn(name="category_id", columnDefinition = "tinyint"),
            inverseJoinColumns= @JoinColumn(name="film_id", columnDefinition = "smallint")
    )
    @ToString.Exclude
    Set<Actor> actors = new HashSet<>();

}
