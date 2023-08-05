package com.example.pharmacy.exception;

public class UserWithThisLoginAlreadyExists extends Throwable {
    public UserWithThisLoginAlreadyExists() {
    }

    public UserWithThisLoginAlreadyExists(String message) {
        super(message);
    }

    public UserWithThisLoginAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithThisLoginAlreadyExists(Throwable cause) {
        super(cause);
    }
}
