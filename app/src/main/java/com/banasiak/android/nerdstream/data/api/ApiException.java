package com.banasiak.android.nerdstream.data.api;

/**
 * A custom exception to be thrown when the API returns a non-200 HTTP response code.
 */
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
