package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;

import java.util.List;

public class UnexpectedErrorException extends JSClientWebException {

    public static final String ERROR_CODE_UNEXPECTED_ERROR = "unexpected.error";

    public UnexpectedErrorException() {
        super();
    }

    public UnexpectedErrorException(String message) {
        super(message);
    }

    public UnexpectedErrorException(String message, List<ErrorDescriptor> errorDescriptors) {
        super(message, errorDescriptors);
    }
}
