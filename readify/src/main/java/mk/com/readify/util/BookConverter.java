package mk.com.readify.util;

import mk.com.readify.model.entity.Book;
import mk.com.readify.model.entity.BookBorrowing;
import mk.com.readify.model.request.BookRequest;
import mk.com.readify.model.response.BookResponse;
import mk.com.readify.model.response.BorrowedBookResponse;
import org.springframework.stereotype.Service;

@Service
public class BookConverter {
    public Book convertToBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .isbn(request.isbn())
                .title(request.title())
                .authorName(request.authorName())
                .bookSummary(request.bookSummary())
                .bookPlot(request.bookPlot())
                .isArchived(false)
                .isShareable(request.isShareable())
                .build();
    }

    public BookResponse convertToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .bookSummary(book.getBookSummary())
                .bookPlot(book.getBookPlot())
                .bookCover(FileLoader.readFileFromLocation(book.getBookCoverImage()))
                .ownerName(book.getBookOwner().getFullName())
                .bookRating(book.getRating())
                .isShareable(book.isShareable())
                .isArchived(book.isArchived())
                .build();
    }

    public BorrowedBookResponse convertToBorrowedBookResponse(BookBorrowing bookBorrowing) {
        return BorrowedBookResponse.builder()
                .id(bookBorrowing.getBorrowedBook().getId())
                .isbn(bookBorrowing.getBorrowedBook().getIsbn())
                .title(bookBorrowing.getBorrowedBook().getTitle())
                .authorName(bookBorrowing.getBorrowedBook().getAuthorName())
                .bookRating(bookBorrowing.getBorrowedBook().getRating())
                .isReturned(bookBorrowing.isReturned())
                .isReturnApproved(bookBorrowing.isReturnApproved())
                .build();
    }
}