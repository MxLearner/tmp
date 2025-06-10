package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Entity.Review;
import org.tj.tjmovies.Service.ReviewService;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public Map<String, Object> getReviews(@RequestBody Map<String, String>movieId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> review=reviewService.getReviewByMovieid(movieId.get("movie_id"));
        if(!review.isEmpty())
            response.put("message", "影评成功查找");
        else
            response.put("message", "影评未能成功查找");
        response.put("review", review);
        return response;
    }

    @PostMapping("/new_review")
    public ResponseEntity<String> saveReview(@RequestBody Map<String, String> newReview) {
        System.out.println(newReview);
        if(Objects.equals(reviewService.saveReview(newReview), "success")){
            return ResponseEntity.ok("插入成功"); // 返回 200 状态码
        }
        else{
            return ResponseEntity.badRequest().body("插入失败");
        }
    }
}
