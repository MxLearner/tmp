package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Service.PostService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public Map<String, Object> savePost(@RequestBody Map<String, String> newPost) {
        return postService.savePost(newPost);
    }

    @GetMapping("/post")
    public Map<String, Object> getPost() {
        return postService.getAllPosts();
    }
}
