package mk.com.readify.exception;

public class BookOperationDeniedException extends RuntimeException {
    public BookOperationDeniedException(String message) {
        super(message);
    }
}