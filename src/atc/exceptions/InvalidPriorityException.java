package atc.exceptions;

public class InvalidPriorityException extends Exception {
    public InvalidPriorityException(String message) {
        super(message);
    }

    public InvalidPriorityException(String message, Throwable cause) {
        super(message, cause);
    }
}