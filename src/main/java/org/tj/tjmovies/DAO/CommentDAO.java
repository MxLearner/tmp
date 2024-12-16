package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.tj.tjmovies.Entity.Comment;
import org.tj.tjmovies.Entity.Post;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, String> {

    List<Comment> findAllByPostId(Long post_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO comment (post_id, userId, text, comment_date) " +
            "VALUES ( :post_id, :userId, :text, :comment_date)", nativeQuery = true)
    int insertComment(
            @Param("post_id") String postId,
            @Param("userId") String userId,
            @Param("text") String text,
            @Param("comment_date") Date comment_date);
}