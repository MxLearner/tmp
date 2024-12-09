package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Movies;

public interface MovieDAO extends JpaRepository<Movies, String> {
}
