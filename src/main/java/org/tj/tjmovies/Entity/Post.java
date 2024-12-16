package org.tj.tjmovies.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movie_id;

    @Column(name = "userId")
    private Long userId;

    private String title;

    private String text;

    private LocalDateTime post_date;

}
