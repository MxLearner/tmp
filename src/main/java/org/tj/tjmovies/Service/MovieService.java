package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.MovieDAO;
import org.tj.tjmovies.DAO.MovieProjection;
import org.tj.tjmovies.Entity.Movie;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDAO movieDAO;

    public List<MovieProjection> getMoviesWithSpecificColumns(){
        return movieDAO.findAllBy();
    }

    public List<Movie> getMoviesByTitle(String title) {
        return movieDAO.findAllByTitleContaining(title);
    }

    public List<Movie> getMoviesById(Long id) {
        return movieDAO.findAllById(id);
    }
}
