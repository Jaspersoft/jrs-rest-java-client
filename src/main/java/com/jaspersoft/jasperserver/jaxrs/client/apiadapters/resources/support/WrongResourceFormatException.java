package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongResourceFormatException extends RuntimeException {
    public WrongResourceFormatException(String msg) {
        super(msg);
    }
}
