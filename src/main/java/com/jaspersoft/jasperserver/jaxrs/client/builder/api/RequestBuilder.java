package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

public interface RequestBuilder<ResponseType>
        extends Request, GetDeleteRequest<ResponseType>, PutPostRequest<ResponseType>{

}
