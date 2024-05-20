package mk.com.readify.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.com.readify.exception.BookOperationDeniedException;
import mk.com.readify.model.common.PageResponse;
import mk.com.readify.model.entity.Book;
import mk.com.readify.model.entity.BookBorrowing;
import mk.com.readify.model.entity.User;
import mk.com.readify.model.request.BookRequest;
import mk.com.readify.model.response.BookResponse;
import mk.com.readify.model.response.BorrowedBookResponse;
import mk.com.readify.repository.BookBorrowingRepository;
import mk.com.readify.repository.BookRepository;
import mk.com.readify.service.BookService;
import mk.com.readify.service.FileStorageService;
import mk.com.readify.util.BookConverter;
import mk.com.readify.util.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {
    private final BookConverter bookConverter;
    private final BookRepository bookRepository;
    private final BookBorrowingRepository bookBorrowingRepository;
    private final FileStorageService fileStorageService;

    @Override
    public BookResponse findById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookConverter::convertToBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
    }

    @Override
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponses = books.stream()
                .map(bookConverter::convertToBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    @Override
    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withBookOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponses = books.stream()
                .map(bookConverter::convertToBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookBorrowing> allBorrowedBooks = bookBorrowingRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookConverter::convertToBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookBorrowing> allBorrowedBooks = bookBorrowingRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookConverter::convertToBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());
    }

    @Override
    public Long createBook(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookConverter.convertToBook(request);
        book.setBookOwner(user);
        return bookRepository.save(book).getId();
    }

    @Override
    public Long borrowBook(Long bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        if (Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot borrow your own book");
        }
        if (!book.isShareable() || book.isArchived()) {
            throw new BookOperationDeniedException("The requested book cannot be borrowed");
        }
        final boolean isCurrentlyBorrowedByUser = bookBorrowingRepository.isCurrentlyBorrowedByUser(bookId, user.getId());
        if (isCurrentlyBorrowedByUser) {
            throw new BookOperationDeniedException("You already borrowed the requested book and it is still not returned or the return is not approved by the book owner");
        }
        final boolean isCurrentlyBorrowedByAnotherUser = bookBorrowingRepository.isCurrentlyBorrowed(bookId);
        if (isCurrentlyBorrowedByAnotherUser) {
            throw new BookOperationDeniedException("The requested book is currently borrowed");
        }
        BookBorrowing bookBorrowing = BookBorrowing.builder()
                .borrower(user)
                .borrowedBook(book)
                .isReturned(false)
                .isReturnApproved(false)
                .build();
        return bookBorrowingRepository.save(bookBorrowing).getId();
    }

    @Override
    public Long returnBorrowedBook(Long bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        if (Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot borrow or return your own book");
        }
        if (!book.isShareable()) {
            throw new BookOperationDeniedException("The requested book is not shareable");
        }
        if (book.isArchived()) {
            throw new BookOperationDeniedException("The requested book is archived");
        }
        BookBorrowing bookBorrowing = bookBorrowingRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new BookOperationDeniedException("You did not borrow this book"));
        bookBorrowing.setReturned(true);
        return bookBorrowingRepository.save(bookBorrowing).getId();
    }

    @Override
    public Long approveReturnedBook(Long bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        if (!Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot approve the return of a book you do not own");
        }
        if (!book.isShareable()) {
            throw new BookOperationDeniedException("The requested book is not shareable");
        }
        if (book.isArchived()) {
            throw new BookOperationDeniedException("The requested book is archived");
        }
        BookBorrowing bookBorrowing = bookBorrowingRepository.findByBookIdAndBookOwnerId(bookId, user.getId())
                .orElseThrow(() -> new BookOperationDeniedException("The book is not returned yet"));
        bookBorrowing.setReturnApproved(true);
        bookBorrowingRepository.save(bookBorrowing);
        book.setShareable(true);
        book.setArchived(false);
        return bookRepository.save(book).getId();
    }

    @Override
    public Long updateShareableStatus(Long bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        if (!Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot update the book's shareable status");
        }
        if (!bookBorrowingRepository.isReturnApproved(bookId, user.getId())) {
            throw new BookOperationDeniedException("The return is not approved yet");
        }
        final boolean isCurrentlyBorrowed = bookBorrowingRepository.isCurrentlyBorrowed(bookId);
        if (isCurrentlyBorrowed) {
            throw new BookOperationDeniedException("The requested book is currently borrowed, you cannot update the status");
        }
        book.setShareable(!book.isShareable());
        return bookRepository.save(book).getId();
    }

    @Override
    public Long updateArchivedStatus(Long bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        if (!Objects.equals(user.getId(), book.getBookOwner().getId())) {
            throw new BookOperationDeniedException("You cannot update the book's archived status");
        }
        if (!bookBorrowingRepository.isReturnApproved(bookId, user.getId())) {
            throw new BookOperationDeniedException("The return is not approved yet");
        }
        final boolean isCurrentlyBorrowed = bookBorrowingRepository.isCurrentlyBorrowed(bookId);
        if (isCurrentlyBorrowed) {
            throw new BookOperationDeniedException("The requested book is currently borrowed, you cannot update the status");
        }
        book.setArchived(!book.isArchived());
        return bookRepository.save(book).getId();
    }

    @Override
    public void uploadBookCoverImage(Long bookId, MultipartFile file, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        var bookCoverImage = fileStorageService.saveFile(file, user.getId());
        book.setBookCoverImage(bookCoverImage);
        bookRepository.save(book);
    }
}