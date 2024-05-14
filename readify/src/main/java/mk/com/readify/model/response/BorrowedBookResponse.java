package mk.com.readify.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String authorName;
    private double bookRating;
    private boolean isReturned;
    private boolean isReturnApproved;
}