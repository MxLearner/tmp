package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.PostDAO;
import org.tj.tjmovies.Entity.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @Autowired
    private PostDAO postDAO;

    public Map<String, Object> savePost(Map<String, String> newPost) {
        Map<String, Object> response = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        try {
            if(newPost.get("movie_id") == null
                    || newPost.get("userId") == null
                    || newPost.get("title") == null
                    || newPost.get("text") == null
                    || newPost.get("post_date") == null) {
                response.put("error", "发布失败");
                return response;
            }

            Date date = sdf.parse(newPost.get("post_date"));
            postDAO.insertReview(newPost.get("movie_id"),
                    newPost.get("userId"), newPost.get("title"), newPost.get("text"),
                    date);
            response.put("message", "发布成功");
            return response;
        } catch (ParseException e) {
            e.printStackTrace();
            response.put("error", "发布失败");
            return response;
        }
    }

    public Map<String, Object> getAllPosts() {
        Map<String, Object> response = new HashMap<>();
        List<Post> posts = postDAO.findAll();
        response.put("post", posts);
        return response;
    }
}
