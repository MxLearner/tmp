package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.ReviewDAO;
import org.tj.tjmovies.Entity.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO reviewDAO;

    public Map<String, Object> getReviewByMovieid(String movie_id) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewDAO.findAllByMovieId(movie_id);
        response.put("review", reviews);
        response.put("message", !reviews.isEmpty() ? "影评成功查找" : "影评未能成功查找");
        return response;
    }

    public ResponseEntity<String> saveReview(Map<String, String> newReview) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        try {
            Date date = sdf.parse(newReview.get("reviewDate"));
            reviewDAO.insertReview(newReview.get("userId"),
                    newReview.get("movie_id"), newReview.get("score"), newReview.get("text"),
                    date);
            return ResponseEntity.ok("插入成功");
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("插入失败");
        }
    }
}
