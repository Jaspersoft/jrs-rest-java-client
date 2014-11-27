/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class JerseyRequest<ResponseType> implements RequestBuilder<ResponseType> {

    private static final int GET = 0;
    private static final int DELETE = 1;
    private static final int POST = 2;
    private static final int PUT = 3;


    public static <T> JerseyRequest<T> buildRequest(SessionStorage sessionStorage,
                                                    Class<T> responseClass,
                                                    String[] path) {
        return buildRequest(sessionStorage, responseClass, path, null);
    }

    public static <T> JerseyRequest<T> buildRequest(SessionStorage sessionStorage,
                                                    Class<T> responseClass,
                                                    String[] path,
                                                    ErrorHandler errorHandler) {
        JerseyRequest<T> request = new JerseyRequest<T>(sessionStorage, responseClass);

        if (errorHandler != null)
            request.errorHandler = errorHandler;
        else
            request.errorHandler = new DefaultErrorHandler();

        for (String pathElem : path)
            request.setPath(pathElem);

        return request;
    }


    private final OperationResultFactory operationResultFactory;
    private final Class<ResponseType> responseClass;

    private ErrorHandler errorHandler;
    private MultivaluedMap<String, String> headers;
    private WebTarget usersWebTarget;
    private String contentType;
    private String acceptType;


    protected JerseyRequest(SessionStorage sessionStorage, Class<ResponseType> responseClass) {

        this.operationResultFactory = new OperationResultFactoryImpl();
        this.responseClass = responseClass;

        RestClientConfiguration configuration = sessionStorage.getConfiguration();

        if (configuration.getContentMimeType() == MimeType.JSON)
            this.contentType = MediaType.APPLICATION_JSON;
        else
            this.contentType = MediaType.APPLICATION_XML;

        if (configuration.getAcceptMimeType() == MimeType.JSON)
            this.acceptType = MediaType.APPLICATION_JSON;
        else
            this.acceptType = MediaType.APPLICATION_XML;

        this.headers = new MultivaluedHashMap<String, String>();
        this.usersWebTarget = sessionStorage.getRootTarget().path("/rest_v2")
                .register(CustomRepresentationTypeProvider.class)
                .register(JacksonFeature.class)
                .register(MultiPartFeature.class);
    }

    public JerseyRequest<ResponseType> setPath(String path) {
        usersWebTarget = usersWebTarget.path(path);
        return this;
    }

    @Override
    public OperationResult<ResponseType> get() throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        return executeRequest(GET, request);
    }

    @Override
    public OperationResult<ResponseType> delete() throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        return executeRequest(DELETE, request);
    }

    @Override
    public OperationResult<ResponseType> put(Object entity) throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        return executeRequest(PUT, request, entity);
    }

    @Override
    public OperationResult<ResponseType> post(Object entity) throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        return executeRequest(POST, request, entity);
    }

    private Invocation.Builder buildRequest() {
        Invocation.Builder request = usersWebTarget.request();
        if (acceptType != null) {
            request = request.accept(acceptType);
        }
        addHeaders(request);
        return request;
    }

    private OperationResult<ResponseType> executeRequest(int httpMethod, Invocation.Builder request) {
        return executeRequest(httpMethod, request, null);
    }

    private OperationResult<ResponseType> executeRequest(int httpMethod, Invocation.Builder request, Object entity) {
        Response response = null;
        switch (httpMethod) {
            case GET: {
                response = request.get();
                break;
            }
            case DELETE: {
                response = request.delete();
                break;
            }
            case POST: {
                response = request.post(Entity.entity(entity, contentType));
                break;
            }
            case PUT: {
                response = request.put(Entity.entity(entity, contentType));
                break;
            }
        }

        if (response.getStatus() >= 400)
            errorHandler.handleError(response);

        OperationResult<ResponseType> result = operationResultFactory.getOperationResult(response, responseClass);
        return result;
    }

    private void addHeaders(Invocation.Builder request) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            for (String value : header.getValue()) {
                request = request.header(header.getKey(), value);
            }
        }
    }

    @Override
    public RequestBuilder<ResponseType> addParam(String name, String... values) {
        usersWebTarget = usersWebTarget.queryParam(name, values);
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> addParams(MultivaluedMap<String, String> params) {
        for (MultivaluedMap.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.queryParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> addMatrixParam(String name, String... values) {
        usersWebTarget = usersWebTarget.matrixParam(name, values);
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> addMatrixParams(MultivaluedMap<String, String> params) {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.matrixParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> setAccept(String acceptMime) {
        this.acceptType = acceptMime;
        return this;
    }

    @Override
    public RequestBuilder<ResponseType> addHeader(String name, String... values) {
        headers.addAll(name, values);
        return this;
    }

    public RequestBuilder<ResponseType> setHeaders(MultivaluedMap<String, String> headers) {
        this.headers = headers;
        return this;
    }


    /**
     * getters/setters block
     */
    public OperationResultFactory getOperationResultFactory() {
        return operationResultFactory;
    }

    public Class<ResponseType> getResponseClass() {
        return responseClass;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public MultivaluedMap<String, String> getHeaders() {
        return headers;
    }

    public WebTarget getUsersWebTarget() {
        return usersWebTarget;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAcceptType() {
        return acceptType;
    }
}
