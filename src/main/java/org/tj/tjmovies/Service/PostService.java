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
            // 必填字段是否为 null 或空字符串
            if (isBlank(newPost.get("movie_id")) ||
                    isBlank(newPost.get("userId")) ||
                    isBlank(newPost.get("title")) ||
                    isBlank(newPost.get("text")) ||
                    isBlank(newPost.get("post_date"))) {
                response.put("error", "发布失败");
                return response;
            }

            // 检查日期格式
            Date date = sdf.parse(newPost.get("post_date"));

            // 检查是否为未来时间
            if (date.after(new Date())) {
                response.put("error", "发布时间不能晚于当前时间");
                return response;
            }

            // 调用 DAO 插入
            postDAO.insertReview(
                    newPost.get("movie_id"),
                    newPost.get("userId"),
                    newPost.get("title"),
                    newPost.get("text"),
                    date
            );

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

    // 工具方法：判空或纯空白
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
