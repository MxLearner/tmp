package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.tj.tjmovies.Entity.Review;

import java.util.Date;
import java.util.List;

public interface ReviewDAO extends JpaRepository<Review, String> {
    List<Review> findAllByMovieId(String movieid);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO review (userid, movieid, score, text, reviewdate) " +
            "VALUES ( :userid, :movieid, :score, :text, :reviewDate)", nativeQuery = true)
    int insertReview(
                     @Param("userid") String userid,
                     @Param("movieid") String movieid,
                     @Param("score") String score,
                     @Param("text") String text,
                     @Param("reviewDate") Date reviewDate);
}
