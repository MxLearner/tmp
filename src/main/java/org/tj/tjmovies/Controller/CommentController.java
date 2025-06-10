package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Service.CommentService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<String> saveComment(@RequestBody Map<String, String> newComment) {
        return commentService.saveComment(newComment);
    }

    @PostMapping("/newcomments")
    public Map<String, Object> getAllComments(@RequestBody Map<String, String> newComment) {
        return commentService.getAllComments(newComment);
    }
}
