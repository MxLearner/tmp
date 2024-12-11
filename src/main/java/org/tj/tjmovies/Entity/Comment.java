package org.tj.tjmovies.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "review")
public class Comment {
    @Id
    @Column(name = "reviewid")
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "movieid")
    private int movieId;

    @Column(name = "text")
    private String text;

    @Column(name = "reviewdate")
    private Date reviewDate;

    @Column(name = "score", precision = 3, scale = 1) // 总位数3，小数位数1
    private BigDecimal score;
}
