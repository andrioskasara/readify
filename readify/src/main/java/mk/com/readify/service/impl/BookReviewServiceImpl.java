package mk.com.readify.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mk.com.readify.exception.BookOperationDeniedException;
import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.entity.Book;
import mk.com.readify.model.entity.BookReview;
import mk.com.readify.model.entity.User;
import mk.com.readify.model.request.BookReviewRequest;
import mk.com.readify.model.response.BookReviewResponse;
import mk.com.readify.repository.BookRepository;
import mk.com.readify.repository.BookReviewRepository;
import mk.com.readify.service.BookReviewService;
import mk.com.readify.util.ReviewConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {
    private final BookRepository bookRepository;
    private final BookReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Override
    public Long createBookReview(BookReviewRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + request.bookId()));
        if (Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot leave a review for your own book");
        }
        if (!book.isShareable() || book.isArchived()) {
            throw new BookOperationDeniedException("You cannot leave a review");
        }
        BookReview review = reviewConverter.convertToReview(request);
        return reviewRepository.save(review).getId();
    }

    @Override
    public PageResponse<BookReviewResponse> findAllReviewsByBook(Long bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<BookReview> reviews = reviewRepository.findAllByBookId(bookId, pageable);
        List<BookReviewResponse> reviewResponses = reviews.stream()
                .map(r -> reviewConverter.convertToReviewResponse(r, user.getId()))
                .collect(Collectors.toList());
        return new PageResponse<>(
                reviewResponses,
                reviews.getNumber(),
                reviews.getSize(),
                reviews.getTotalElements(),
                reviews.getTotalPages(),
                reviews.isFirst(),
                reviews.isLast()
        );
    }
}