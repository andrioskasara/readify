package mk.com.readify.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewResponse {
    private Double rating;
    private String comment;
    private boolean ownReview;
}