package org.tj.tjmovies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.tj.tjmovies.DAO.CommentDAO;
import org.tj.tjmovies.DAO.UserDAO;
import org.tj.tjmovies.Entity.Comment;
import org.tj.tjmovies.Entity.User;
import org.tj.tjmovies.Service.CommentService;

@SpringBootTest
public class CommentServiceTest {

    @MockBean
    private CommentDAO commentDAO;

    @MockBean
    private UserDAO userDAO;

    @Autowired
    private CommentService commentService;

    private Map<String, String> baseArgs;

    @BeforeEach
    void setUp() {
        baseArgs = new HashMap<>();
        baseArgs.put("post_id", "42");
        baseArgs.put("userId", "1001");
        baseArgs.put("text", "这是一条合法长度的评论");
        baseArgs.put("comment_date", "2023-10-01T12:00:00.000+08:00");
    }

    // 测试用例 1: 正常情况
    @Test
    void testSaveCommentSuccess() {
        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(baseArgs);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("评论成功");
    }

    // 测试用例 2: text 长度为 0
    @Test
    void testTextLengthZero() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", "");

        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("评论不能为空");
    }

    // 测试用例 3: text 长度为 1
    @Test
    void testTextLength1() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1; i++) sb.append("x");

        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", sb.toString());

        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("评论成功");
    }

    // 测试用例 4: text 长度为 199
    @Test
    void testTextLength199() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 199; i++) sb.append("x");

        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", sb.toString());

        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("评论成功");
    }

    // 测试用例 5: text 长度为 200
    @Test
    void testTextLength200() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) sb.append("x");

        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", sb.toString());

        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("评论成功");
    }

    // 测试用例 6: text 长度为 201
    @Test
    void testTextLength201() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 201; i++) sb.append("x");

        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", sb.toString());

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("超过上限");
    }

    // 测试用例 7: 缺少 post_id
    @Test
    void testMissingPostId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("post_id");

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("缺少必要字段");
    }

    // 测试用例 8: 缺少 userId
    @Test
    void testMissingUserId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("userId");

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("缺少必要字段");
    }

    // 测试用例 9: 缺少 text
    @Test
    void testMissingText() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("text");

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("缺少必要字段");
    }

    // 测试用例 10: 缺少 comment_date
    @Test
    void testMissingCommentDate() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("comment_date");

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("缺少必要字段");
    }

    // 测试用例 11: 日期格式错误
    @Test
    void testInvalidDateFormat() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("comment_date", "not-a-date");

        when(userDAO.findById("1001")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = commentService.saveComment(args);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Unparseable date");
    }

    // 测试用例 12: 有效的 post_id 且存在评论
    @Test
    void testGetAllCommentsSuccess() {
        baseArgs.put("post_id", "42");
        when(commentDAO.findAllByPostId(42L)).thenReturn(List.of(new Comment(), new Comment())); // 模拟返回两个评论

        Map<String, Object> response = commentService.getAllComments(baseArgs);
        assertThat(response.containsKey("comment")).isTrue();
        assertThat(((List<Comment>) response.get("comment")).size()).isEqualTo(2);
    }

    // 测试用例 13: 有效的 post_id 但没有评论
    @Test
    void testGetAllCommentsNoComments() {
        baseArgs.put("post_id", "43");
        when(commentDAO.findAllByPostId(43L)).thenReturn(Collections.emptyList()); // 模拟返回空列表

        Map<String, Object> response = commentService.getAllComments(baseArgs);
        assertThat(response.containsKey("comment")).isTrue();
        assertThat(((List<Comment>) response.get("comment")).isEmpty()).isTrue();
    }

    // 测试用例 14: 无效的 post_id
    @Test
    void testGetAllCommentsInvalidPostId() {
        baseArgs.put("post_id", "-1");
        when(commentDAO.findAllByPostId(-1L)).thenReturn(Collections.emptyList()); // 模拟返回空列表

        Map<String, Object> response = commentService.getAllComments(baseArgs);
        assertThat(response.containsKey("comment")).isTrue();
        assertThat(((List<Comment>) response.get("comment")).isEmpty()).isTrue();
    }

    // 测试用例 15: post_id 为 null
    @Test
    void testGetAllCommentsNullPostId() {
        baseArgs.put("post_id", null);  // 模拟 post_id 为 null

        Map<String, Object> response = commentService.getAllComments(baseArgs);
        assertThat(response.containsKey("error")).isTrue();  // 确保包含 error 字段
        assertThat(response.get("error")).isEqualTo("缺少必要字段: post_id");  // 错误信息包含 "缺少必要字段: post_id"
    }


    // 测试用例 16: post_id 为 0
    @Test
    void testGetAllCommentsZeroPostId() {
        baseArgs.put("post_id", "0");
        when(commentDAO.findAllByPostId(0L)).thenReturn(Collections.emptyList()); // 模拟返回空列表

        Map<String, Object> response = commentService.getAllComments(baseArgs);
        assertThat(response.containsKey("comment")).isTrue();
        assertThat(((List<Comment>) response.get("comment")).isEmpty()).isTrue();
    }
}
