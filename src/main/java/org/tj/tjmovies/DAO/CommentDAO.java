package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Comment;

public interface CommentDAO extends JpaRepository<Comment, String> {
}
