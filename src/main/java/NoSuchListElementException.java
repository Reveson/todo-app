public class NoSuchListElementException extends RuntimeException {
    public NoSuchListElementException(String s) {
        super(s);
    }
    NoSuchListElementException() {
        super();
    }
}
