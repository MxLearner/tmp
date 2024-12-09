package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Movie;

public interface MovieDAO extends JpaRepository<Movie, String> {
}
