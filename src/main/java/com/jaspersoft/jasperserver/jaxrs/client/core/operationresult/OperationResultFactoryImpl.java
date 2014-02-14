package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.WebExceptionsFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.WebExceptionsFactoryImpl;

import javax.ws.rs.core.Response;


public class OperationResultFactoryImpl implements OperationResultFactory {

    private WebExceptionsFactory exceptionsFactory;

    public OperationResultFactoryImpl(){
        exceptionsFactory = new WebExceptionsFactoryImpl();
    }

    @Override
    public <T> OperationResult<T> getOperationResult(Response response, Class<T> responseClass)
            throws JSClientWebException {
        checkForError(response);
        if (isClientResource(responseClass))
            responseClass = (Class<T>) getSpecificResourceType(response);

        return getAppropriateOperationResultInstance(response, responseClass);
    }

    private <T> OperationResult<T> getAppropriateOperationResultInstance(Response response,
                                                                                   Class<T> responseClass){
        OperationResult<T> result;
        if (response.hasEntity())
            result = new WithEntityOperationResult<T>(response, responseClass);
        else
            result = new NullEntityOperationResult(response, responseClass);
        return result;
    }

    private void checkForError(Response response) throws JSClientWebException {
        int statusCode = response.getStatus();
        if (isError(statusCode))
            throw exceptionsFactory.getException(response);
    }

    private boolean isError(int responseCode){
        return responseCode >= 400;
    }

    private boolean isClientResource(Class<?> clazz){
        return clazz.isAssignableFrom(ClientResource.class);
    }

    private Class<? extends ClientResource> getSpecificResourceType(Response response){
        return ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));
    }

}
