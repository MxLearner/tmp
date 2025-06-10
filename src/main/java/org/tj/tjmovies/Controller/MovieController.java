package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.DAO.MovieProjection;
import org.tj.tjmovies.Entity.Movie;
import org.tj.tjmovies.Service.MovieService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<MovieProjection> getMovies() {
        return movieService.getMoviesWithSpecificColumns();
    }

    @PostMapping("/movies")
    public List<Movie> getMoviesByTitle(String title) {
        return movieService.getMoviesByTitle(title);
    }

    @PostMapping("/details")
    public Map<String, Object> getMoviesById(@RequestBody Map<String, String> id) {
        return movieService.getMoviesById(Long.valueOf(id.get("movie_id")));
    }
}
