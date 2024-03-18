package edu.java.exception;

import java.net.URL;
import org.springframework.http.HttpStatus;

public class LinkNotFoundException extends ScrapperException {

    private static final String DEFAULT_DESCRIPTION = "Link is not found";

    public LinkNotFoundException(URL url) {
        super(String.format("Link with url %s is not found", url), DEFAULT_DESCRIPTION, HttpStatus.NOT_FOUND);
    }

    public LinkNotFoundException(Long linkId) {
        super(String.format("Link with id %s is not found", linkId), DEFAULT_DESCRIPTION, HttpStatus.NOT_FOUND);
    }
}

