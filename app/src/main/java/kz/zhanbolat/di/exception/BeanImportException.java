package kz.zhanbolat.di.exception;

public class BeanImportException extends RuntimeException {
    public BeanImportException(String message) {
        super(message);
    }

    public BeanImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
