package org.tj.tjmovies.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name="movies")
public class Movie {
    @Id
    private int id;

    private String title;

    private String description;

    private float rating;

    @Column(name="release_date")
    private Date releaseDate;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
