package kz.zhanbolat.di.exception;

public class InjectionException extends Exception {
    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
