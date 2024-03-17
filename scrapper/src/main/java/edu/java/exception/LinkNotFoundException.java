package edu.java.exception;

import java.net.URL;
import org.springframework.http.HttpStatus;

public class LinkNotFoundException extends ScrapperException {

    private static final String MESSAGE = "Link with id %s is not found";

    private static final String DEFAULT_DESCRIPTION = "Link is not found";

    public LinkNotFoundException(URL url) {
        super(String.format(MESSAGE, url), DEFAULT_DESCRIPTION, HttpStatus.NOT_FOUND);
    }

    public LinkNotFoundException(Long linkId) {
        super(String.format(MESSAGE, linkId), DEFAULT_DESCRIPTION, HttpStatus.NOT_FOUND);
    }
}

