package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

public interface CommonRequest<RequestType, ResponseType>
        extends Request, GetDeleteRequest<ResponseType>, PutPostRequest<RequestType, ResponseType>{

}
