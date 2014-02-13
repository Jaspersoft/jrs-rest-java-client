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
    public <T> OperationResult<? extends T> getOperationResult(Response response, Class<? extends T> responseClass) {
        checkForError(response);
        if (isClientResource(responseClass))
            responseClass = (Class<? extends T>) getSpecificResourceType(response);


        return null;
    }

    /*private <T> OperationResult<? extends T> getAppropriateOperationResultInstance(Response response,
                                                                                   Class<? extends T> responseClass){
        OperationResult<> result;
        if (response.hasEntity())
            result = new WithEntityOperationResult(response, responseClass)
    }*/

    private void checkForError(Response response) throws JSClientWebException {
        int statusCode = response.getStatus();
        if (isError(statusCode))
            throw exceptionsFactory.getException(statusCode);
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
