package com.example.pharmacy.exception;

public class NotValidLoginException extends Throwable {
    public NotValidLoginException() {
    }

    public NotValidLoginException(String message) {
        super(message);
    }

    public NotValidLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidLoginException(Throwable cause) {
        super(cause);
    }
}
