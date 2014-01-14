package com.jaspersoft.jasperserver.jaxrs.client;

public final class ResponseStatus {

    /**
     * No error, operation successful.
     */
    public static final int OK = 200;

    /**
     * Resource was updated.
     */
    public static final int UPDATED = 200;

    /**
     * Successful creation of a resource.
     */
    public static final int CREATED = 201;

    /**
     * The request was processed successfully, but no response body is needed.
     */
    public static final int NO_CONTENT = 204;

    /**
     * Malformed syntax or a bad query.
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Action requires user authentication.
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Authentication failure or invalid Application ID.
     */
    public static final int FORBIDDEN = 403;

    /**
     * Resource not found.
     */
    public static final int NOT_FOUND = 404;

    /**
     * Method not allowed on resource.
     */
    public static final int NOT_ALLOWED = 405;

    /**
     * Requested representation not available for the resource.
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * Representation not supported for the resource.
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * Internal server error.
     */
    public static final int SERVER_ERROR = 500;

    /**
     * Requested HTTP operation not supported.
     */
    public static final int NOT_IMPLEMENTED = 501;

    private ResponseStatus(){}

}
