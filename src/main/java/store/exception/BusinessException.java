package store.exception;

public class BusinessException extends IllegalArgumentException {
    public BusinessException(String message) {
        super("[ERROR] " + message);
    }
}
