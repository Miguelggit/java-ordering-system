package exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String s) {
        super(s);
    }
}
