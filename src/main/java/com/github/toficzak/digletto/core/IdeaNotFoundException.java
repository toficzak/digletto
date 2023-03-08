package com.github.toficzak.digletto.core;

public class IdeaNotFoundException extends RuntimeException {

    public final Long errorCode = ErrorCodes.IDEA_NOT_FOUND;

    public IdeaNotFoundException() {
    }

}
