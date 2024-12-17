package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Entity.Comment;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Service.CommentService;
import org.tj.tjmovies.Service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    //不知道前端为什么这么要求api，传了post又返回post
    public Map<String, Object> savePost(@RequestBody Map<String, String> newComment) {
        Map<String, Object> response = new HashMap<>();
        if(Objects.equals(commentService.saveComment(newComment), "success")){
            response.put("message","评论成功");
        }
        else{
            response.put("error","评论失败");
        }
        return response;
    }

    @PostMapping("/newcomments")
    public Map<String, Object> getAllComments(@RequestBody Map<String, String> newComment) {
        Map<String, Object> response = new HashMap<>();
        List<Comment> comment=commentService.getAllComments(newComment);
        response.put("comment",comment);
        return response;
    }
}
