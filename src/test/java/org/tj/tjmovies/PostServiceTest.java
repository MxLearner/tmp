package org.tj.tjmovies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tj.tjmovies.DAO.PostDAO;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostDAO postDAO;

    @InjectMocks
    private PostService postService;

    private Map<String, String> validArgs;

    @BeforeEach
    void setUp() {
        validArgs = new HashMap<>();
        validArgs.put("movie_id", "123");    // 字符串形式，Service 里会 parse 成 Long
        validArgs.put("userId", "456");      // 同上
        validArgs.put("title", "精彩影评");
        validArgs.put("text", "这是一条详细且内容丰富的影评。");
        validArgs.put("post_date", "2023-10-01T12:00:00.000+08:00");
    }

    @Test
    void savePost_withValidDate_shouldReturnSuccess() {
        String result = postService.savePost(validArgs);
        assertThat(result).isEqualTo("success");
    }

    @Test
    void savePost_withInvalidDate_shouldReturnParseErrorMessage() {
        Map<String, String> args = new HashMap<>(validArgs);
        args.put("post_date", "not-a-date");

        String result = postService.savePost(args);

        assertThat(result).contains("Unparseable date");
    }

    @Test
    void getAllPosts_shouldReturnListFromDAO() {
        // 构造两个 Post 对象
        Post p1 = new Post();
        p1.setId(1L);
        p1.setMovie_id(123L);
        p1.setUserId(1L);
        p1.setTitle("标题1");
        p1.setText("文本1");
        p1.setPost_date(LocalDateTime.of(2023, 10, 1, 12, 0));

        Post p2 = new Post();
        p2.setId(2L);
        p2.setMovie_id(456L);
        p2.setUserId(2L);
        p2.setTitle("标题2");
        p2.setText("文本2");
        p2.setPost_date(LocalDateTime.of(2023, 10, 2, 13, 30));

        List<Post> dummyPosts = Arrays.asList(p1, p2);
        when(postDAO.findAll()).thenReturn(dummyPosts);

        List<Post> result = postService.getAllPosts();
        assertThat(result).isEqualTo(dummyPosts);

        verify(postDAO).findAll();
    }

    @Test
    void savePost_missingTitle_shouldReturnErrorMessage() {
        Map<String, String> args = new HashMap<>(validArgs);
        args.remove("title");

        String result = postService.savePost(args);

        assertThat(result).contains("Missing required field");
    }
    @Test
    void savePost_missingUserId_shouldReturnErrorMessage() {
        Map<String, String> args = new HashMap<>(validArgs);
        args.remove("userId");

        String result = postService.savePost(args);

        assertThat(result).contains("Missing required field");
    }
    @Test
    void savePost_missingMovieId_shouldReturnErrorMessage() {
        Map<String, String> args = new HashMap<>(validArgs);
        args.remove("movie_id");

        String result = postService.savePost(args);

        assertThat(result).contains("Missing required field");
    }

    @Test
    void savePost_missingText_shouldReturnErrorMessage() {
        Map<String, String> args = new HashMap<>(validArgs);
        args.remove("text");

        String result = postService.savePost(args);

        assertThat(result).contains("Missing required field");
    }
}
