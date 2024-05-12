package mk.com.readify.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mk.com.readify.model.common.BaseEntity;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookBorrowing extends BaseEntity {
    @ManyToOne
    private User borrower;
    @ManyToOne
    private Book borrowedBook;
    private boolean isReturned;
    private boolean isReturnApproved;
}
