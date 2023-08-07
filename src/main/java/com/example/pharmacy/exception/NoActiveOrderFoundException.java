package com.example.pharmacy.exception;

public class NoActiveOrderFoundException extends Throwable {
    public NoActiveOrderFoundException() {
    }

    public NoActiveOrderFoundException(String message) {
        super(message);
    }

    public NoActiveOrderFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoActiveOrderFoundException(Throwable cause) {
        super(cause);
    }
}
