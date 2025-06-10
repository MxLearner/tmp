package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.CommentDAO;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserDAO userDAO;

    public ResponseEntity<String> saveComment(Map<String, String> newComment) {
        Map<String, Object> response = new HashMap<>();
        
        if (newComment.get("post_id") == null || newComment.get("userId") == null || 
            newComment.get("text") == null || newComment.get("comment_date") == null) {
            response.put("error", "缺少必要字段");
            return ResponseEntity.badRequest().body(response.toString());
        }

        if (newComment.get("text").trim().isEmpty()) {
            response.put("error", "评论不能为空");
            return ResponseEntity.badRequest().body(response.toString());
        }

        if (newComment.get("text").length() > 200) {
            response.put("error", "超过上限");
            return ResponseEntity.badRequest().body(response.toString());
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            Date date = sdf.parse(newComment.get("comment_date"));
            commentDAO.insertComment(newComment.get("post_id"),
                    newComment.get("userId"), newComment.get("text"),
                    date);
            response.put("message", "评论成功");
            return ResponseEntity.ok().body(response.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response.toString());
        }
    }

    public Map<String, Object> getAllComments(Map<String, String> newComment) {
        Map<String, Object> response = new HashMap<>();

        // 先检查 post_id 是否为 null
        String postIdStr = newComment.get("post_id");
        if (postIdStr == null) {
            response.put("error", "缺少必要字段: post_id");
            return response;
        }

        Long postId = Long.valueOf(postIdStr);  // 转换 post_id
        List<Comment> comments = commentDAO.findAllByPostId(postId);
        response.put("comment", comments);

        return response;
    }

}
