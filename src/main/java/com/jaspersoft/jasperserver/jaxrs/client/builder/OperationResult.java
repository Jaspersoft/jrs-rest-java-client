package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourcesTypeResolverUtil;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;

public class OperationResult<T> {

    private Response response;
    private Class<? extends T> entityClass;
    private ErrorDescriptor error;

    private T entity;

    public OperationResult(Response response, Class<? extends T> entityClass) {
        this.response = response;
        if (entityClass.isAssignableFrom(ClientResource.class)){
            entityClass =
                   (Class<? extends T>) ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));
        }
        this.entityClass = entityClass;
        if (response.getStatus() == 500 || response.getStatus() == 400)
            error = response.readEntity(ErrorDescriptor.class);
    }

    public T getEntity() {
        if (response.getStatus() == 404)
            return null;

        try {
            if (entity == null)
                entity = response.readEntity(entityClass);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse() {
        return response;
    }

    public ErrorDescriptor getError() {
        return error;
    }

    public String getSessionId() {
        Map<String, NewCookie> cookies = response.getCookies();
        NewCookie jsessionid;

        if (cookies != null &&
                (jsessionid = cookies.get("JSESSIONID")) != null)
            return jsessionid.getValue();
        else
            return null;
    }



}
