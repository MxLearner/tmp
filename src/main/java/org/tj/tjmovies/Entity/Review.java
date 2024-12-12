package org.tj.tjmovies.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private Long reviewId;

    @Column(name = "userid")
    private String userId;

    @Column(name = "movieid")
    private String movieId;

    @Column(name = "text")
    private String text;

    @Column(name = "reviewdate")
    private Date reviewDate;

    @Column(name = "score", precision = 3, scale = 1) // 总位数3，小数位数1
    private BigDecimal score;

}
