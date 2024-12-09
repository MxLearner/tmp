package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.MovieDAO;
import org.tj.tjmovies.Entity.Movie;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDAO movieDAO;

    public List<Movie> getMovies() {
        return movieDAO.findAll();
    }

    public List<Movie> getMoviesByTitle(String title) {
        return movieDAO.findAllByTitle(title);
    }
}
