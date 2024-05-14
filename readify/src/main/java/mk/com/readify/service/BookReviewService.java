package mk.com.readify.service;

import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.request.BookReviewRequest;
import mk.com.readify.model.response.BookReviewResponse;
import org.springframework.security.core.Authentication;

public interface BookReviewService {
    Long createBookReview(BookReviewRequest request, Authentication user);

    PageResponse<BookReviewResponse> findAllReviewsByBook(Long bookId, int page, int size, Authentication user);
}