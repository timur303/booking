package kg.kadyrbekov.exception;

public class TurfAlreadyBookedException extends RuntimeException {
    public TurfAlreadyBookedException(String message) {
        super(message);
    }
}

