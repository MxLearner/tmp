package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.MovieDAO;
import org.tj.tjmovies.DAO.MovieProjection;
import org.tj.tjmovies.Entity.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getMoviesById(Long id) {
        Map<String, Object> response = new HashMap<>();
        List<Movie> movies = movieDAO.findAllById(id);
        response.put("movie", movies);
        response.put("message", !movies.isEmpty() ? "查找成功" : "查找失败");
        return response;
    }
}
