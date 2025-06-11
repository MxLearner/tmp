package org.tj.tjmovies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.tj.tjmovies.DAO.ReviewDAO;
import org.tj.tjmovies.Entity.Review;
import org.tj.tjmovies.Service.ReviewService;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewDAO reviewDAO;

    private Map<String, String> baseArgs;

    @BeforeEach
    void setup() {
        baseArgs = new HashMap<>();
        baseArgs.put("userId", "1001");
        baseArgs.put("movie_id", "2002");
        baseArgs.put("score", "8");
        baseArgs.put("text", "好电影！");
        baseArgs.put("reviewDate", "2023-10-01T12:00:00.000+08:00");
    }

    // TC01 查询有结果
    @Test
    void testGetReviewWithResult() {
        when(reviewDAO.findAllByMovieId("2001")).thenReturn(List.of(new Review()));
        Map<String, Object> result = reviewService.getReviewByMovieid("2001");
        assertThat(result.get("message")).isEqualTo("影评成功查找");
    }

    // TC02 查询为空
    @Test
    void testGetReviewNoResult() {
        when(reviewDAO.findAllByMovieId("2002")).thenReturn(Collections.emptyList());
        Map<String, Object> result = reviewService.getReviewByMovieid("2002");
        assertThat(result.get("message")).isEqualTo("影评未能成功查找");
    }

    // TC03 movie_id 为 null
    @Test
    void testGetReviewWithNullId() {
        Map<String, Object> result = reviewService.getReviewByMovieid(null);
        assertThat(result.get("review")).isEqualTo(Collections.emptyList());
    }

    // TC04 合法插入
    @Test
    void testSaveReviewSuccess() {
        ResponseEntity<String> result = reviewService.saveReview(baseArgs);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).contains("插入成功");
    }

    // TC05-09 缺字段测试
    @Test
    void testMissingUserId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("userId");
        assertFail(args);
    }

    @Test
    void testMissingMovieId() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("movie_id");
        assertFail(args);
    }

    @Test
    void testMissingScore() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("score");
        assertFail(args);
    }

    @Test
    void testMissingText() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("text");
        assertFail(args);
    }

    @Test
    void testMissingReviewDate() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.remove("reviewDate");
        assertFail(args);
    }

    // TC10 日期格式错误
    @Test
    void testInvalidDateFormat() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("reviewDate", "not-a-date");
        assertFail(args);
    }

    // TC11 未来时间
    @Test
    void testFutureDate() {
        Map<String, String> args = new HashMap<>(baseArgs);
        String future = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .format(new Date(System.currentTimeMillis() + 86400000));
        args.put("reviewDate", future);
        ResponseEntity<String> result = reviewService.saveReview(args);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
        assertThat(result.getBody()).contains("时间不能是未来");
    }

    // TC12 空文本
    @Test
    void testEmptyText() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("text", "");
        assertFail(args);
    }

    // TC13 score = -1
    @Test
    void testScoreNegative() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("score", "-1");
        assertFail(args);
    }

    // TC14 score = 0
    @Test
    void testScoreZero() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("score", "0");
        ResponseEntity<String> result = reviewService.saveReview(args);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    // TC15 score = 10
    @Test
    void testScoreMax() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("score", "10");
        ResponseEntity<String> result = reviewService.saveReview(args);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    // TC16 score = 11
    @Test
    void testScoreTooHigh() {
        Map<String, String> args = new HashMap<>(baseArgs);
        args.put("score", "11");
        assertFail(args);
    }

    // 辅助断言失败方法
    private void assertFail(Map<String, String> args) {
        ResponseEntity<String> result = reviewService.saveReview(args);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
        assertThat(result.getBody()).contains("插入失败");
    }
}
