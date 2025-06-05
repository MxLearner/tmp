package org.tj.tjmovies;// CommentServiceTest.java

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tj.tjmovies.DAO.CommentDAO;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.User;          // <-- 确保导入你的 User 实体
import org.tj.tjmovies.Service.CommentService;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private CommentDAO commentDAO;

    private Map<String, String> baseArgs;

    @BeforeEach
    void setUp() {
        baseArgs = new HashMap<>();
        baseArgs.put("post_id", "42");
        baseArgs.put("userId", "1001");
        baseArgs.put("text", "这是一条合法长度的评论");
        baseArgs.put("comment_date", "2023-10-01T12:00:00.000+08:00");
    }

    @Test
    void testMissingFields() {
        Map<String, String> args1 = new HashMap<>();
        assertThat(commentService.saveComment(args1)).isEqualTo("缺少必要字段");
    }

    @Test
    void testTextTooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 201; i++) sb.append("x");
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", sb.toString());
        assertThat(commentService.saveComment(args)).isEqualTo("超过上限");
    }

    @Test
    void testUserNotFound() {
        // userDAO.findById 返回 Optional.empty()
        when(userDAO.findById("1001")).thenReturn(Optional.empty());

        assertThat(commentService.saveComment(baseArgs)).isEqualTo("无用户");
    }

    @Test
    void testDateParseError() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("comment_date", "not-a-date");

        // 先让 userDAO.findById 返回一个真正的 User，而不是 new Object()
        User dummyUser = new User();
        when(userDAO.findById("1001")).thenReturn(Optional.of(dummyUser));

        String result = commentService.saveComment(args);
        assertThat(result).contains("Unparseable date");
    }

    @Test
    void testInsertSuccess() {
        User dummyUser = new User();
        when(userDAO.findById("1001")).thenReturn(Optional.of(dummyUser));

        String result = commentService.saveComment(baseArgs);
        assertThat(result).isEqualTo("success");
    }

}
