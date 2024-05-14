package mk.com.readify.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.request.BookRequest;
import mk.com.readify.model.response.BookResponse;
import mk.com.readify.model.response.BorrowedBookResponse;
import mk.com.readify.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "12", required = false) int size,
            Authentication user) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, user));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "12", required = false) int size,
            Authentication user) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, user));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "12", required = false) int size,
            Authentication user) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, user));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "12", required = false) int size,
            Authentication user) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, user));
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody @Valid BookRequest request, Authentication user) {
        return ResponseEntity.ok(bookService.createBook(request, user));
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<Long> borrowBook(@PathVariable Long bookId, Authentication user) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, user));
    }

    @PatchMapping("{bookId}/borrow/return")
    public ResponseEntity<Long> returnBorrowedBook(@PathVariable Long bookId, Authentication user) {
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, user));
    }

    @PatchMapping("{bookId}/borrow/return/approve")
    public ResponseEntity<Long> approveReturnedBook(@PathVariable Long bookId, Authentication user) {
        return ResponseEntity.ok(bookService.approveReturnedBook(bookId, user));
    }

    @PatchMapping("/{bookId}/shareable")
    public ResponseEntity<Long> updateShareableBookStatus(@PathVariable Long bookId, Authentication user) {
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId, user));
    }

    @PatchMapping("/{bookId}/archived")
    public ResponseEntity<Long> updateArchivedBookStatus(@PathVariable Long bookId, Authentication user) {
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, user));
    }

    @PostMapping(value = "/{bookId}/cover-image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverImage(@PathVariable Long bookId,
                                                  @Parameter()
                                                  @RequestPart("file") MultipartFile file,
                                                  Authentication user) {
        bookService.uploadBookCoverImage(bookId, file, user);
        return ResponseEntity.accepted().build();
    }

}