package com.example.pharmacy.exception;

public class IncorrectDataOfBirthFormat extends Throwable {
    public IncorrectDataOfBirthFormat() {
    }

    public IncorrectDataOfBirthFormat(String message) {
        super(message);
    }

    public IncorrectDataOfBirthFormat(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDataOfBirthFormat(Throwable cause) {
        super(cause);
    }
}
