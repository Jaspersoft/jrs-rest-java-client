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
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class JerseyRequestBuilder<ResponseType> implements RequestBuilder<ResponseType> {

    public static <T> JerseyRequestBuilder<T> buildRequest(SessionStorage sessionStorage, Class<T> responseClass, String[] path){
        JerseyRequestBuilder<T> builder = new JerseyRequestBuilder<T>(sessionStorage, responseClass);
        for (String pathElem : path)
            builder.setPath(pathElem);

        return builder;
    }


    private final OperationResultFactory operationResultFactory;
    private final SessionStorage sessionStorage;
    private final Class<ResponseType> responseClass;
    private MultivaluedMap<String, String> headers;
    private WebTarget usersWebTarget;
    private String contentType;
    private String acceptType;


    protected JerseyRequestBuilder(SessionStorage sessionStorage, Class<ResponseType> responseClass) {

        this.operationResultFactory = new OperationResultFactoryImpl();
        this.sessionStorage = sessionStorage;
        this.responseClass = responseClass;
        this.contentType = MediaType.APPLICATION_JSON;
        this.acceptType = MediaType.APPLICATION_JSON;
        this.headers = new MultivaluedHashMap<String, String>();
        init();
    }

    private void init(){
        AuthenticationCredentials credentials = sessionStorage.getCredentials();
        Client client = ClientBuilder.newClient();
        client
                .register(CustomRepresentationTypeProvider.class)
                .register(JacksonFeature.class)
                .register(MultiPartWriter.class)
                .register(HttpAuthenticationFeature.basic(credentials.getUsername(), credentials.getPassword()));

        String restServerUrl = sessionStorage.getConfiguration().getRestServerUrl();
        usersWebTarget = client.target(restServerUrl);

        if (sessionStorage.getSessionId() != null)
            usersWebTarget.register(new SessionOutputFilter(sessionStorage.getSessionId()));
    }

    public JerseyRequestBuilder<ResponseType> setPath(String path) {
        usersWebTarget = usersWebTarget.path(path);
        return this;
    }

    @Override
    public WebTarget getPath() {
        return usersWebTarget;
    }

    @Override
    public RequestBuilder<ResponseType> setTarget(WebTarget webTarget) {
        this.usersWebTarget = webTarget;
        return this;
    }

    @Override
    public OperationResult<ResponseType> get() throws JSClientWebException {
        try {
            Invocation.Builder request =
                    usersWebTarget
                            .request();
            if (acceptType != null){
                request = request.accept(acceptType);
            }
            addHeaders(request);

            Response response = request.get();
            OperationResult<ResponseType> result =
                    operationResultFactory.getOperationResult(response, responseClass);
            this.sessionStorage.setSessionId(result.getSessionId());

            return result;
        } catch (JSClientWebException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperationResult<ResponseType> delete() throws JSClientWebException {
        Invocation.Builder request =
                usersWebTarget
                        .request();
        if (acceptType != null){
            request = request.accept(acceptType);
        }
        addHeaders(request);

        Response response = request.delete();
        OperationResult<ResponseType> result =
                operationResultFactory.getOperationResult(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());

        return result;
    }

    private void addHeaders(Invocation.Builder request){
        for (Map.Entry<String, List<String>> header : headers.entrySet()){
            for (String value : header.getValue()){
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
    public OperationResult<ResponseType> put(Object entity) throws JSClientWebException {
        Invocation.Builder request =
                usersWebTarget
                        .request();
        if (acceptType != null){
            request = request.accept(acceptType);
        }
        addHeaders(request);

        Response response = request.put(Entity.entity(entity, contentType));
        OperationResult<ResponseType> result =
                operationResultFactory.getOperationResult(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());

        return result;
    }

    @Override
    public OperationResult<ResponseType> post(Object entity) throws JSClientWebException {
        Invocation.Builder request =
                usersWebTarget
                        .request();
        if (acceptType != null){
            request = request.accept(acceptType);
        }
        addHeaders(request);

        Response response = request.post(Entity.entity(entity, contentType));
        OperationResult<ResponseType> result =
                operationResultFactory.getOperationResult(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());

        return result;
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
        return null;
    }

    public RequestBuilder<ResponseType> setHeaders(MultivaluedMap<String, String> headers){
        this.headers = headers;
        return null;
    }

}
