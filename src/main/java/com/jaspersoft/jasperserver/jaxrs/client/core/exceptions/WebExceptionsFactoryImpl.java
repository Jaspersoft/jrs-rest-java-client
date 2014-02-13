package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class WebExceptionsFactoryImpl implements WebExceptionsFactory {

    private static final Map<Integer, Class<? extends JSClientWebException>> errorCodeToTypeMap =
            new HashMap<Integer, Class<? extends JSClientWebException>>(){{
                put(ResponseStatus.BAD_REQUEST, BadRequestException.class);
                put(ResponseStatus.FORBIDDEN, AuthenticationFailureException.class);
                put(ResponseStatus.NOT_ALLOWED, HttpMethodNotAllowedException.class);
                put(ResponseStatus.SERVER_ERROR, InternalServerErrorException.class);
                put(ResponseStatus.NOT_ACCEPTABLE, RepresentationNotAvailableForResourceException.class);
                put(ResponseStatus.NOT_FOUND, ResourceNotFoundException.class);
                put(ResponseStatus.UNAUTHORIZED, UnauthorizedActionException.class);
            }};

    @Override
    public JSClientWebException getException(int errorCode) {
        Class<? extends JSClientWebException> exceptionClass = errorCodeToTypeMap.get(errorCode);
        JSClientWebException exception = null;
        try {
            exception = exceptionClass.newInstance();
        } catch (Exception ignored) {}

        return exception;
    }

}
