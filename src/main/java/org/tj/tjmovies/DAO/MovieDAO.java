package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Movie;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, String> {
    List<Movie> findAllById(Long id);

    List<Movie> findAllByTitleContaining(String title);

    List<MovieProjection> findAllBy();
}
