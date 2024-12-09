package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Comments;
import org.tj.tjmovies.Entity.Movies;

public interface CommentDAO extends JpaRepository<Comments, String> {
}
