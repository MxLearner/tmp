package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Movie;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, String> {
    List<Movie> findAllByTitle(String title);
}
