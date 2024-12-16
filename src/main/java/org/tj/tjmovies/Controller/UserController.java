package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Service.UserService;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> CreateUser(String username, String password, String email) {
        if (password.length() < 6) {
            return ResponseEntity.badRequest().body("密码长度至少6位!");
        }
        if (userService.checkUsername(username)) {
            return ResponseEntity.badRequest().body("用户名已存在!");
        }
        if (userService.checkEmail(email)) {
            return ResponseEntity.badRequest().body("该邮箱已注册!");
        }
        return ResponseEntity.created(URI.create(userService.CreateUser(username, password, email).toString())).body("注册成功!");
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
