package mk.com.readify.repository;

import mk.com.readify.model.entity.BookBorrowing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Long> {
    @Query("""
            select bookBorrowing
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrower.id = :userId
            """)
    Page<BookBorrowing> findAllBorrowedBooks(Pageable pageable, Long userId);

    @Query("""
            select bookBorrowing
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrowedBook.bookOwner.id = :userId                        
            """)
    Page<BookBorrowing> findAllReturnedBooks(Pageable pageable, Long userId);

    @Query("""
            select
            (count(bookBorrowing) > 0) as isBorrowed
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrowedBook.id = :bookId 
            and bookBorrowing.borrower.id = :userId
            and bookBorrowing.isReturnApproved = false                
            """)
    boolean isCurrentlyBorrowedByUser(Long bookId, Long userId);

    @Query("""
            select
            (count(bookBorrowing) > 0) as isBorrowed
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrowedBook.id = :bookId 
            and bookBorrowing.isReturnApproved = false                
            """)
    boolean isCurrentlyBorrowed(Long bookId);

    @Query("""
            select bookBorrowing
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrowedBook.id = :bookId 
            and bookBorrowing.borrower.id = :userId
            and bookBorrowing.isReturned = false
            and bookBorrowing.isReturnApproved = false
            """)
    Optional<BookBorrowing> findByBookIdAndUserId(Long bookId, Long userId);

    @Query("""
            select bookBorrowing
            from BookBorrowing bookBorrowing
            where bookBorrowing.borrowedBook.id = :bookId 
            and bookBorrowing.borrowedBook.bookOwner.id = :userId
            and bookBorrowing.isReturned = true 
            and bookBorrowing.isReturnApproved = false
            """)
    Optional<BookBorrowing> findByBookIdAndBookOwnerId(Long bookId, Long userId);
}