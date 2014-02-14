package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class WebExceptionsFactoryImpl implements WebExceptionsFactory {

    private static final Map<Integer, Class<? extends JSClientWebException>> errorCodeToTypeMap =
            new HashMap<Integer, Class<? extends JSClientWebException>>(){{
                put(ResponseStatus.BAD_REQUEST, BadRequestException.class);
                put(ResponseStatus.FORBIDDEN, AccessForbiddenException.class);
                put(ResponseStatus.NOT_ALLOWED, HttpMethodNotAllowedException.class);
                put(ResponseStatus.SERVER_ERROR, InternalServerErrorException.class);
                put(ResponseStatus.NOT_ACCEPTABLE, RepresentationNotAvailableForResourceException.class);
                put(ResponseStatus.NOT_FOUND, ResourceNotFoundException.class);
                put(ResponseStatus.UNAUTHORIZED, AuthenticationFailureException.class);
            }};

    @Override
    public JSClientWebException getException(Response response) {
        Class<? extends JSClientWebException> exceptionClass = errorCodeToTypeMap.get(response.getStatus());
        JSClientWebException exception = null;
        try {
            ErrorDescriptor errorDescriptor = null;

            try {
                errorDescriptor = response.readEntity(ErrorDescriptor.class);
            } catch (Exception ignored) {}

            exception = exceptionClass.getConstructor(String.class)
                    .newInstance(errorDescriptor != null ? errorDescriptor.getMessage()
                            : "Exception happened with response: " + response);
        } catch (Exception ignored) {}

        return exception;
    }

}
