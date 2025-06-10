package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Service.ReviewService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public Map<String, Object> getReviews(@RequestBody Map<String, String> movieId) {
        return reviewService.getReviewByMovieid(movieId.get("movie_id"));
    }

    @PostMapping("/new_review")
    public ResponseEntity<String> saveReview(@RequestBody Map<String, String> newReview) {
        return reviewService.saveReview(newReview);
    }
}
