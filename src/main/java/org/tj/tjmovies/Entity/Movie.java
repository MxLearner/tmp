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

    @Column(name = "moviesdescription")
    private String description;

    @Column(name = "moviesrating")
    private float rating;

    @Column(name = "releasedate")
    private Date releaseDate;

    @Column(name = "createdat")
    private Date createdAt;

    @Column(name = "updatedat")
    private Date updatedAt;

    @Column(name = "imagesrc")
    private String imageSrc;
}
