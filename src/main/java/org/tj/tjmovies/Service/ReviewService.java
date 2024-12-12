package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.ReviewDAO;
import org.tj.tjmovies.Entity.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO reviewDAO;

    public List<Review> getReviewByMovieid(String movie_id) {
        return reviewDAO.findAllByMovieId(movie_id);
    }

    public String saveReview(Map<String, String> newReview) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(newReview.get("reviewDate"));
            reviewDAO.insertReview(newReview.get("user_id"),
                    newReview.get("movie_id"), newReview.get("score"), newReview.get("text"),
                    date);
            return "success";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
