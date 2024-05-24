package mk.com.readify.model.request;

import jakarta.validation.constraints.NotEmpty;

public record BookRequest(
        Long id,
        @NotEmpty(message = "100")
        String isbn,
        @NotEmpty(message = "101")
        String title,
        @NotEmpty(message = "102")
        String authorName,
        @NotEmpty(message = "103")
        String bookSummary,
        @NotEmpty(message = "104")
        String bookPlot,
        boolean isShareable
) {
}