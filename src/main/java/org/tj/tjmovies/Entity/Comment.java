package org.tj.tjmovies.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "commentsid")
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "movieid")
    private int movieId;

    private String content;

    @Column(name = "createdat")
    private Date createdAt;

    @Column(name = "updatedat")
    private Date updatedAt;
}
