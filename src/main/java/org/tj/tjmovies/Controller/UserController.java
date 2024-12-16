package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Entity.User;
import org.tj.tjmovies.Service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> CreateUser(String username, String password, String email) {
        if (password.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "密码长度至少6位!"));
        }
        if (userService.checkUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名已存在!"));
        }
        if (userService.checkEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "该邮箱已注册!"));
        }
        User user = userService.CreateUser(username, password, email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "注册成功!");
        response.put("userId", Long.toString(user.getUserId()));
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(String username, String password) {
        if (userService.checkPassword(username, password)) {
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("用户名或密码错误");
        }
    }
}
