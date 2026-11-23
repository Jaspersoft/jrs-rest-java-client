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

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.RequestMethod;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import org.glassfish.jersey.uri.UriComponent;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType.JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;


public class JerseyRequest<ResponseType> implements RequestBuilder<ResponseType> {
    private static final int GET = 0;
    private static final int DELETE = 1;
    private static final int POST = 2;
    private static final int PUT = 3;

    private final OperationResultFactory operationResultFactory;
    private final Class<ResponseType> responseClass;
    private final GenericType<ResponseType> responseGenericType;
    private final Boolean restrictedHttpMethods;
    private ErrorHandler errorHandler;
    private MultivaluedMap<String, String> headers;
    private WebTarget usersWebTarget;
    private String contentType;
    private String acceptType;
    private Boolean handleErrors;
    private Locale userLocale;

    protected JerseyRequest(SessionStorage sessionStorage, Class<ResponseType> responseClass) {
        operationResultFactory = new OperationResultFactoryImpl();
        this.responseClass = responseClass;
        this.responseGenericType = null;
        restrictedHttpMethods = sessionStorage.getConfiguration().getRestrictedHttpMethods();
        this.userLocale = sessionStorage.getUserLocale();
        init(sessionStorage);
    }


    @SuppressWarnings("unchecked")
    protected JerseyRequest(SessionStorage sessionStorage, GenericType<ResponseType> genericType) {
        operationResultFactory = new OperationResultFactoryImpl();
        this.responseClass = (Class<ResponseType>) genericType.getRawType();
        this.responseGenericType = genericType;
        restrictedHttpMethods = sessionStorage.getConfiguration().getRestrictedHttpMethods();
        init(sessionStorage);
    }

    private void init(SessionStorage sessionStorage) {
        RestClientConfiguration configuration = sessionStorage.getConfiguration();

        contentType = configuration.getContentMimeType() == JSON ? APPLICATION_JSON : APPLICATION_XML;
        acceptType = configuration.getAcceptMimeType() == JSON ? APPLICATION_JSON : APPLICATION_XML;
        headers = new MultivaluedHashMap<String, String>();

        usersWebTarget = sessionStorage.getRootTarget()
                .path("/rest_v2");
        handleErrors = configuration.getHandleErrors();
    }

    public static <T> JerseyRequest<T> buildRequest(SessionStorage sessionStorage, Class<T> responseClass, String[] path) {
        return buildRequest(sessionStorage, responseClass, path, null);
    }

    public static <T> JerseyRequest<T> buildRequest(SessionStorage sessionStorage, Class<T> responseClass, String[] path, ErrorHandler errorHandler) {
        JerseyRequest<T> request = new JerseyRequest<T>(sessionStorage, responseClass);
        return configRequest(request, path, errorHandler);
    }

    public static <T> JerseyRequest<T> buildRequest(SessionStorage sessionStorage, GenericType<T> genericType, String[] path, ErrorHandler errorHandler) {
        JerseyRequest<T> request = new JerseyRequest<T>(sessionStorage, genericType);
        return configRequest(request, path, errorHandler);
    }

    private static <T> JerseyRequest<T> configRequest(JerseyRequest<T> request, String[] path, ErrorHandler errorHandler) {
        request.errorHandler = errorHandler != null ? errorHandler : new DefaultErrorHandler();
        boolean validatNnext = true;
        for (String pathElem : path) {
            if (validatNnext) {
                if (!UriComponent.valid(pathElem, UriComponent.Type.PATH_SEGMENT)) {
                    pathElem = UriComponent.contextualEncode(pathElem, UriComponent.Type.PATH_SEGMENT);
                }
                if (pathElem.equals("inputControls")) {
                    validatNnext = false;
                }
            } else {
                validatNnext = true;
            }
            request.setPath(pathElem);
        }

        return request;
    }

    public JerseyRequest<ResponseType> setPath(String path) {
        usersWebTarget = usersWebTarget.path(path);
        return this;
    }

    @Override
    public OperationResult<ResponseType> get() throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        if (userLocale != null) {
            request.acceptLanguage(userLocale);
        }
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
        if (userLocale != null) {
            request.acceptLanguage(userLocale);
        }
        return executeRequest(PUT, request, entity);
    }

    @Override
    public OperationResult<ResponseType> post(Object entity) throws JSClientWebException {
        Invocation.Builder request = buildRequest();
        if (userLocale != null) {
            request.acceptLanguage(userLocale);
        }
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
        if (restrictedHttpMethods && (httpMethod != POST || httpMethod != GET)) {
            request.header("X-HTTP-Method-Override", RequestMethod.values()[httpMethod].toString());
            response = request.post(Entity.entity(entity, contentType));
        } else {
            switch (httpMethod) {
                case GET:
                    response = request.get();
                    break;
                case DELETE:
                    response = request.delete();
                    break;
                case POST:
                    response = request.post(Entity.entity(entity, contentType));
                    break;
                case PUT:
                    response = request.put(Entity.entity(entity, contentType));
                    break;
            }

            if (response != null && response.getStatus() == 411 && (httpMethod != POST || httpMethod != GET)) {
                request.header("X-HTTP-Method-Override", RequestMethod.values()[httpMethod].toString());
                executeRequest(POST, request, entity);
            }
        }

        if (response != null && response.getStatus() >= 400) {
            if (!handleErrors) {
                return (responseGenericType != null) ? new NullEntityOperationResult<ResponseType>(response, responseGenericType, errorHandler)
                        : new NullEntityOperationResult<ResponseType>(response, responseClass, errorHandler);

            }
            errorHandler.handleError(response);
        }

        return (responseGenericType != null) ? operationResultFactory.getOperationResult(response, responseGenericType)
                : operationResultFactory.getOperationResult(response, responseClass);
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

}
