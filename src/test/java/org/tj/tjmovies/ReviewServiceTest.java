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
import org.tj.tjmovies.DAO.ReviewDAO;
import org.tj.tjmovies.Entity.Post;
import org.tj.tjmovies.Entity.Review;
import org.tj.tjmovies.Service.PostService;
import org.tj.tjmovies.Service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewDAO reviewDAO;

    private Map<String, String> validReview;

    @BeforeEach
    void setUp() {
        validReview = new HashMap<>();
        validReview.put("userId", "1001");
        validReview.put("movie_id", "2002");
        validReview.put("score", "4.5");
        validReview.put("text", "这是一条有深度的影评。");
        validReview.put("reviewDate", "2023-10-01T12:00:00.000+08:00");
    }
    @Test
    void saveReview_withValidData_shouldReturnSuccess() {
        String result = reviewService.saveReview(validReview);

        assertThat(result).isEqualTo("success");

        verify(reviewDAO).insertReview(
                eq("1001"),
                eq("2002"),
                eq("4.5"),
                eq("这是一条有深度的影评。"),
                any(Date.class)
        );
    }
    @Test
    void saveReview_withInvalidDate_shouldReturnParseError() {
        Map<String, String> invalid = new HashMap<>(validReview);
        invalid.put("reviewDate", "not-a-date");

        String result = reviewService.saveReview(invalid);

        assertThat(result).contains("Unparseable date");
    }

    @Test
    void getReviewByMovieid_shouldReturnReviewList() {
        Review r1 = new Review();
        r1.setUserId("1001");
        r1.setMovie_id("2002");
        r1.setText("评价1");

        Review r2 = new Review();
        r2.setUserId("1002");
        r2.setMovie_id("2002");
        r2.setText("评价2");

        List<Review> dummyList = Arrays.asList(r1, r2);
        when(reviewDAO.findAllByMovieId("2002")).thenReturn(dummyList);

        List<Review> result = reviewService.getReviewByMovieid("2002");

        assertThat(result).isEqualTo(dummyList);
        verify(reviewDAO).findAllByMovieId("2002");
    }

    @Test
    void getReviewByMovieid_whenNoReviews_shouldReturnEmptyList() {
        when(reviewDAO.findAllByMovieId("9999")).thenReturn(Collections.emptyList());

        List<Review> result = reviewService.getReviewByMovieid("9999");

        assertThat(result).isEmpty();
    }

}
