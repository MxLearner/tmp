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
            // 非空校验
            if (newReview.get("userId") == null ||
                    newReview.get("movie_id") == null ||
                    newReview.get("score") == null ||
                    newReview.get("text") == null ||
                    newReview.get("reviewDate") == null ||
                    newReview.get("text").trim().isEmpty()) {
                return ResponseEntity.badRequest().body("插入失败");
            }

            // 检查分数合法性
            int score = Integer.parseInt(newReview.get("score"));
            if (score < 0 || score > 10) {
                return ResponseEntity.badRequest().body("插入失败");
            }

            // 检查时间合法性
            Date date = sdf.parse(newReview.get("reviewDate"));
            if (date.after(new Date())) {
                return ResponseEntity.badRequest().body("时间不能是未来");
            }

            reviewDAO.insertReview(newReview.get("userId"),
                    newReview.get("movie_id"), newReview.get("score"), newReview.get("text"),
                    date);
            return ResponseEntity.ok("插入成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("插入失败");
        }
    }
}
