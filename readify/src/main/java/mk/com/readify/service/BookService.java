package mk.com.readify.service;

import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.request.BookRequest;
import mk.com.readify.model.response.BookResponse;
import mk.com.readify.model.response.BorrowedBookResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookResponse findById(Long bookId);

    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication user);

    PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication user);

    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication user);

    PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication user);

    Long createBook(BookRequest request, Authentication user);

    Long borrowBook(Long bookId, Authentication user);

    Long returnBorrowedBook(Long bookId, Authentication user);

    Long approveReturnedBook(Long bookId, Authentication user);

    Long updateShareableStatus(Long bookId, Authentication user);

    Long updateArchivedStatus(Long bookId, Authentication user);

    void uploadBookCoverImage(Long bookId, MultipartFile file, Authentication user);
}