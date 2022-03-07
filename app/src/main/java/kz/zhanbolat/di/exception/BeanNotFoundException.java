package kz.zhanbolat.di.exception;

public class BeanNotFoundException extends Exception {
    public BeanNotFoundException(String message) {
        super(message);
    }

    public BeanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
