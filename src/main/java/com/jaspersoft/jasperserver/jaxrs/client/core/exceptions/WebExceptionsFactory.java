package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

public interface WebExceptionsFactory {

    public JSClientWebException getException(int errorCode);

}
