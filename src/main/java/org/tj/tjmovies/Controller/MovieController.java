package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Entity.Movie;
import org.tj.tjmovies.Service.MovieService;

import java.util.List;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("/api")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping("/movies")
    public List<Movie> getMoviesByTitle(String title) {
        return movieService.getMoviesByTitle(title);
    }
}
