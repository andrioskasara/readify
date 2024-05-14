package mk.com.readify.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mk.com.readify.model.common.BaseEntity;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookReview extends BaseEntity {
    @ManyToOne
    private Book reviewedBook;
    private Double rating;
    private String comment;
}
