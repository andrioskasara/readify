package mk.com.readify.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mk.com.readify.model.common.BaseEntity;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book extends BaseEntity {
    private String isbn;
    private String title;
    private String authorName;
    private String bookSummary;
    private String bookCoverImage;
    @ManyToOne
    private User bookOwner;
    @OneToMany(mappedBy = "reviewedBook")
    private List<BookReview> bookReviews;
    @OneToMany(mappedBy = "borrowedBook")
    private List<BookBorrowing> bookBorrowings;
    private boolean isShareable;
    private boolean isArchived;

    @Transient
    public double getRating() {
        if (bookReviews == null || bookReviews.isEmpty()) {
            return 0.0;
        }
        var rating = this.bookReviews
                .stream()
                .mapToDouble(BookReview::getRating)
                .average()
                .orElse(0.0);
        return Math.round(rating * 10.0) / 10.0;
    }
}
