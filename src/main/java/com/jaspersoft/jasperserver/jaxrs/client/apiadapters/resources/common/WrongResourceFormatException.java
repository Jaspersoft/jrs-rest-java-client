package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.common;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongResourceFormatException extends RuntimeException {
    public WrongResourceFormatException(String msg) {
        super(msg);
    }
}
