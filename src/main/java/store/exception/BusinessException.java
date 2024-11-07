package store.exception;

public class BusinessException extends IllegalArgumentException {
    public BusinessException(ErrorMessage message) {
        super("[ERROR] " + message);
    }
}
