package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.Enum.UserRole;
import org.tj.tjmovies.Entity.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    private static final int MIN_PASSWORD_LENGTH = 6;

    public ResponseEntity<Map<String, String>> registerUser(String username, String password, String email) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return ResponseEntity.badRequest().body(Map.of("error", "密码长度至少6位!"));
        }
        if (checkUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名已存在!"));
        }
        if (checkEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "该邮箱已注册!"));
        }
        CreateUser(username, password, email);
        return ResponseEntity.ok(Map.of("message", "注册成功!"));
    }

    public ResponseEntity<Map<String, String>> loginUser(String username, String password) {
        User user = FindUser(username, password);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "用户名或密码错误"));
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "登录成功!");
        response.put("userId", Long.toString(user.getUserId()));
        response.put("email", user.getEmail());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> changeUserPassword(String username, String oldPassword, String newPassword) {
        if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            return ResponseEntity.badRequest().body("新密码长度至少6位!");
        }
        if (UpdatePassword(username, oldPassword, newPassword)) {
            return ResponseEntity.ok("密码修改成功!");
        }
        return ResponseEntity.status(401).body("用户名或旧密码错误!");
    }

    // 检查用户名是否已被使用，是返回True
    private boolean checkUsername(String username) {
        return userDAO.findByUsername(username).isPresent();
    }

    // 检查邮箱是否已被使用，是返回True
    private boolean checkEmail(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    // 创建用户
    private void CreateUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(UserRole.user);
        userDAO.save(user);
    }

    //查找user
    private User FindUser(String username, String password) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    //修改密码
    private boolean UpdatePassword(String username, String oldPassword, String newPassword) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDAO.save(user);
            return true;
        }
        return false;
    }
}
