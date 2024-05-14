package mk.com.readify.repository;

import mk.com.readify.model.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    @Query("""
            select review
            from BookReview review
            where review.reviewedBook.id = :bookId
            """)
    Page<BookReview> findAllByBookId(Long bookId, Pageable pageable);
}