package mk.com.readify.repository;

import mk.com.readify.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("""
            select book
            from Book book
            where book.isArchived = false 
            and book.isShareable = true 
            and book.bookOwner.id <> :userId
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Long userId);

    @Query("""
            select book
            from Book book
            where (lower(book.title) like lower(concat('%', :searchTerm, '%'))
            or lower(book.authorName) like lower(concat('%', :searchTerm, '%'))
            or lower(book.isbn) like lower(concat('%', :searchTerm, '%')))
            and book.isArchived = false 
            and book.isShareable = true
            """)
    Page<Book> searchBooks(Pageable pageable, String searchTerm);
}