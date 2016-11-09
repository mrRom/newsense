package com.mr.newsense.validation;

@SuppressWarnings("serial")
public class UserNameExistsException extends Throwable {

    public UserNameExistsException(final String message) {
        super(message);
    }
}
