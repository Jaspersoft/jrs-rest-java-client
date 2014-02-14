package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import javax.ws.rs.core.Response;

public interface WebExceptionsFactory {

    public JSClientWebException getException(Response errorCode);

}
