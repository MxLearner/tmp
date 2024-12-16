package org.tj.tjmovies.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.Enum.UserRole;
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

    // 创建用户
    public void CreateUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(UserRole.user);
        userDAO.save(user);
    }

    //查找user
    public User FindUser(String username, String password) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    //修改密码
    public boolean UpdatePassword(String username, String oldPassword, String newPassword) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDAO.save(user);
            return true;
        }
        return false;
    }
}
