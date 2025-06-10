package org.tj.tjmovies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tj.tjmovies.Service.UserService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> CreateUser(String username, String password, String email) {
        return userService.registerUser(username, password, email);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> Login(String username, String password) {
        return userService.loginUser(username, password);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> ChangePassword(String username, String oldPassword, String newPassword) {
        return userService.changeUserPassword(username, oldPassword, newPassword);
    }
}
