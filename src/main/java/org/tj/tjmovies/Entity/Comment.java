package org.tj.tjmovies.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    private Long userId;
    private String text;
    @Column(name = "comment_date")
    private LocalDateTime comment_date;
}
