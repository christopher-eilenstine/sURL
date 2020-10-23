package com.ceilenstine.csci4830.exceptions;

public class LinkNotFoundException extends RuntimeException {

    public LinkNotFoundException(String shortUrl) {
        super("There is no longUrl corresponding to the shortUrl of: " + shortUrl);
    }

}
