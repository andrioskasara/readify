package mk.com.readify.util;

import mk.com.readify.model.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withBookOwnerId(Long ownerId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bookOwner").get("id"), ownerId));
    }
}