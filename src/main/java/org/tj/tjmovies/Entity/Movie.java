package org.tj.tjmovies.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "moviesid")
    private int id;

    @Column(name = "moviestitle")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private float rating;

    @Column(name = "releasedate")
    private Date releaseDate;

    @Column(name = "director")
    private String director ;

    @Column(name = "duration")
    private String duration;

    @Column(name = "language")
    private String language;

    @Column(name = "actor")
    private String actor;

    @Column(name = "imagesrc")
    private String imageSrc;
}
