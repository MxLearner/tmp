package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.CommentDAO;
import org.tj.tjmovies.Entity.Comment;
import org.tj.tjmovies.Entity.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public String saveComment(Map<String, String> newComment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            Date date = sdf.parse(newComment.get("comment_date"));
            commentDAO.insertComment(newComment.get("post_id"),
                    newComment.get("userId"), newComment.get("text"),
                    date);
            return "success";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public List<Comment> getAllComments(Map<String, String> newComment) {
        return commentDAO.findAllByPostId(Long.valueOf(newComment.get("post_id")));
    }
}
