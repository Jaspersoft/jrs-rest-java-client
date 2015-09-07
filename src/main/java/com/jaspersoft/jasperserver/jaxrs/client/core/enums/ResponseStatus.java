/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core.enums;

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
     * Successful creation of a resources.
     */
    public static final int CREATED = 201;

    /**
     * The request was processed successfully, but no response body is needed.
     */
    public static final int NO_CONTENT = 204;


    public static final int FOUND = 302;

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
     * Method not allowed on resources.
     */
    public static final int NOT_ALLOWED = 405;

    /**
     * Requested representation not available for the resources.
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * Resource was updated by another user.
     */
    public static final int CONFLICT = 409;

    /**
     * Representation not supported for the resources.
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

    private ResponseStatus() {
    }
}
