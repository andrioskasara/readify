package mk.com.readify.model.request;

import jakarta.validation.constraints.*;

public record BookReviewRequest(
        @NotNull(message = "200")
        Long bookId,
        @Positive(message = "201")
        @Min(value = 0, message = "202")
        @Max(value = 5, message = "203")
        Double rating,
        @NotEmpty(message = "204")
        String comment
) {
}