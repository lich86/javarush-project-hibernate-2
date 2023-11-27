package com.javarush.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "film_text", schema = "movie")
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmText extends BaseEntity{
    @Id
    @Column(name = "film_id", columnDefinition = "smallint")
    Short film_id;
    @OneToOne
    @JoinColumn(name = "film_id")
    Film film;
    @Column(name = "title", columnDefinition = "varchar(255)")
    String title;
    @Column(name = "description", columnDefinition = "text")
    String description;

    public void setFilm(Film film) {
        this.film = film;
        this.film_id = film.getFilmId();
    }
}
