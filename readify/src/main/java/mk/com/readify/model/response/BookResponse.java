package mk.com.readify.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String authorName;
    private String bookSummary;
    private byte[] bookCover;
    private String ownerName;
    private double bookRating;
    private boolean isShareable;
    private boolean isArchived;
}