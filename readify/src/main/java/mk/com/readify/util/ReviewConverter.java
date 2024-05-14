package mk.com.readify.util;

import mk.com.readify.model.entity.Book;
import mk.com.readify.model.entity.BookReview;
import mk.com.readify.model.request.BookReviewRequest;
import mk.com.readify.model.response.BookReviewResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReviewConverter {
    public BookReview convertToReview(BookReviewRequest request) {
        return BookReview.builder()
                .reviewedBook(Book.builder()
                        .id(request.bookId())
                        .build())
                .rating(request.rating())
                .comment(request.comment())
                .build();
    }

    public BookReviewResponse convertToReviewResponse(BookReview review, Long id) {
        return BookReviewResponse.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .ownReview(Objects.equals(id, review.getCreatedBy()))
                .build();
    }
}