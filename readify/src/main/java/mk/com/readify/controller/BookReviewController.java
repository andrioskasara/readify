package mk.com.readify.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.request.BookReviewRequest;
import mk.com.readify.model.response.BookReviewResponse;
import mk.com.readify.service.BookReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
@Tag(name = "Review")
public class BookReviewController {
    private final BookReviewService reviewService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<BookReviewResponse>> findAllReviewsByBook(
            @PathVariable Long bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "12", required = false) int size,
            Authentication user) {
        return ResponseEntity.ok(reviewService.findAllReviewsByBook(bookId, page, size, user));
    }

    @PostMapping
    public ResponseEntity<Long> createBookReview(@RequestBody @Valid BookReviewRequest request, Authentication user) {
        return ResponseEntity.ok(reviewService.createBookReview(request, user));
    }

}