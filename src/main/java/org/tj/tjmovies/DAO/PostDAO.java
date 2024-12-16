package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Entity.Review;

import java.util.Date;
import java.util.List;

public interface PostDAO extends JpaRepository<Post, String> {


    List<Post> findAll();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO post (movie_id, userId, title, text, post_date) " +
            "VALUES ( :movie_id, :userId, :title, :text, :post_date)", nativeQuery = true)
    int insertReview(
            @Param("movie_id") String movie_id,
            @Param("userId") String userId,
            @Param("title") String title,
            @Param("text") String text,
            @Param("post_date") Date post_date);
}
