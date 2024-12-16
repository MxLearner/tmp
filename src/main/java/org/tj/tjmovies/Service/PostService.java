package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.PostDAO;
import org.tj.tjmovies.Entity.Movie;
import org.tj.tjmovies.Entity.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @Autowired
    private PostDAO postDAO;

    public String savePost(Map<String, String> newReview) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(newReview.get("post_date"));
            postDAO.insertReview(newReview.get("movie_id"),
                    newReview.get("userId"), newReview.get("title"), newReview.get("text"),
                    date);
            return "success";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public List<Post> getAllPosts() {
        return postDAO.findAll();
    }
}
