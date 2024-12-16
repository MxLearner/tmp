package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Service.MovieService;
import org.tj.tjmovies.Service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public Map<String, Object> savePost(@RequestBody Map<String, String> newPost) {
        Map<String, Object> response = new HashMap<>();
        String id=postService.savePost(newPost);
        if(!Objects.equals(id, "fail")){
            response.put("message","发布成功");
        }
        else{
            response.put("error","发布失败");
        }
        return response;
    }

    @GetMapping("/post")
    public Map<String, Object> getPost() {
        Map<String, Object> response = new HashMap<>();
        List<Post> post=postService.getAllPosts();
        response.put("post",post);
        return response;
    }
}
