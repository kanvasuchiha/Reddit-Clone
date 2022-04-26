package com.example.RedditClone.exceptions;

//In the backend when we are creating REST APIs, exceptions are pretty common in the code.
//So whenever exceptions occur, we don't want any technical details to be exposed to the user.
//Rather than showing NullPointerException, IllegalStateException, etc., it is better if we share the exception with
//the user in a much understandable message format. We can do this exact thing by creating our own custom exceptions

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
