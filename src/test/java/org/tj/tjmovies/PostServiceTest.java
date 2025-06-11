package org.tj.tjmovies;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tj.tjmovies.DAO.PostDAO;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Service.PostService;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostDAO postDAO;

    @Autowired
    private PostService postService;

    private Map<String, String> baseArgs;

    @BeforeEach
    void setUp() {
        baseArgs = new HashMap<>();
        baseArgs.put("movie_id", "1");
        baseArgs.put("userId", "1001");
        baseArgs.put("title", "A great movie");
        baseArgs.put("text", "This movie is amazing.");
        baseArgs.put("post_date", "2023-10-01T12:00:00.000+08:00");
    }

    // 测试用例 1: 正常情况
    @Test
    void testSavePostSuccess() {
        Map<String, Object> result = postService.savePost(baseArgs);
        assertThat(result.get("message")).isEqualTo("发布成功");
    }

    // 测试用例 2-6: 缺少字段
    @Test
    void testMissingMovieId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("movie_id");
        assertFailure(args);
    }

    @Test
    void testMissingUserId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("userId");
        assertFailure(args);
    }

    @Test
    void testMissingTitle() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("title");
        assertFailure(args);
    }

    @Test
    void testMissingText() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("text");
        assertFailure(args);
    }

    @Test
    void testMissingPostDate() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("post_date");
        assertFailure(args);
    }

    // 测试用例 7: 无效 movie_id
    @Test
    void testInvalidMovieId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("movie_id", "");
        assertFailure(args);
    }

    // 测试用例 8: 无效 userId
    @Test
    void testInvalidUserId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("userId", "");
        assertFailure(args);
    }

    // 测试用例 9: 空 title
    @Test
    void testEmptyTitle() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("title", "");
        assertFailure(args);
    }

    // 测试用例 10: 空 text
    @Test
    void testEmptyText() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", "");
        assertFailure(args);
    }

    // 测试用例 11: post_date 为未来时间，应发布失败
    @Test
    void testPostDateInFuture() {
        Map<String, String> args = new HashMap<>(baseArgs);
        String futureDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .format(new java.util.Date(System.currentTimeMillis() + 100000000L));
        args.put("post_date", futureDate);
        Map<String, Object> result = postService.savePost(args);
        assertThat(result.get("error")).isEqualTo("发布时间不能晚于当前时间");
    }

    // 测试用例 12: post_date 格式错误
    @Test
    void testInvalidDateFormat() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("post_date", "invalid-format");
        assertFailure(args);
    }

    // 测试用例 13: getAllPosts 返回正常帖子列表
    @Test
    void testGetAllPostsNormal() {
        List<Post> mockPosts = List.of(new Post(), new Post());
        org.mockito.Mockito.when(postDAO.findAll()).thenReturn(mockPosts);
        Map<String, Object> result = postService.getAllPosts();
        assertThat(result.containsKey("post")).isTrue();
        assertThat(((List<?>) result.get("post")).size()).isEqualTo(2);
    }

    // 测试用例 14: getAllPosts 无帖子
    @Test
    void testGetAllPostsEmpty() {
        org.mockito.Mockito.when(postDAO.findAll()).thenReturn(Collections.emptyList());
        Map<String, Object> result = postService.getAllPosts();
        assertThat(result.containsKey("post")).isTrue();
        assertThat(((List<?>) result.get("post")).isEmpty()).isTrue();
    }

    // 辅助方法用于统一断言失败结果
    private void assertFailure(Map<String, String> args) {
        Map<String, Object> result = postService.savePost(args);
        assertThat(result.get("error")).isEqualTo("发布失败");
    }
}
