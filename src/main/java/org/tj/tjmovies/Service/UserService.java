package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.User;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    // 检查用户名是否已被使用，是返回True
    public boolean checkUsername(String username) {
        return userDAO.findByUsername(username).isPresent();
    }

    // 检查邮箱是否已被使用，是返回True
    public boolean checkEmail(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    // 创建用户，返回userId的字符串
    public String CreateUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userDAO.save(user);
        return Integer.toString(user.getUserId());
    }

    public boolean checkPassword(String username, String password) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
