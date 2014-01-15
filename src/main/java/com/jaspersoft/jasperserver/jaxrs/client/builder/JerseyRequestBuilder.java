package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CollectionJsonProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class JerseyRequestBuilder<ResponseType> implements RequestBuilder<ResponseType> {

    private final SessionStorage sessionStorage;
    private WebTarget usersWebTarget;
    private final Class<ResponseType> responseClass;
    private String contentType;


    public JerseyRequestBuilder(SessionStorage sessionStorage, Class<ResponseType> responseClass) {

        this.sessionStorage = sessionStorage;

        AuthenticationCredentials credentials = sessionStorage.getCredentials();
        Client client = ClientBuilder.newClient();
        client
                .register(CollectionJsonProvider.class)
                .register(JacksonFeature.class)
                .register(HttpAuthenticationFeature.basic(credentials.getUsername(), credentials.getPassword()));

        this.responseClass = responseClass;
        this.contentType = MediaType.APPLICATION_JSON;

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
    public Request setTarget(WebTarget webTarget) {
        this.usersWebTarget = webTarget;
        return this;
    }

    @Override
    public OperationResult<ResponseType> get() {
        try {
            Invocation.Builder request = usersWebTarget.request(contentType);
            Response response = request.get();
            OperationResult<ResponseType> result =
                    new OperationResult<ResponseType>(response, responseClass);
            this.sessionStorage.setSessionId(result.getSessionId());
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperationResult<ResponseType> delete() {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.delete();
        OperationResult<ResponseType> result =
                new OperationResult<ResponseType>(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());
        return result;
    }

    @Override
    public GetDeleteRequest<ResponseType> addParam(String name, String... values) {
        usersWebTarget = usersWebTarget.queryParam(name, values);
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addParams(MultivaluedMap<String, String> params) {
        for (MultivaluedMap.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.queryParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParam(String name, String... values) {
        usersWebTarget = usersWebTarget.matrixParam(name, values);
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParams(MultivaluedMap<String, String> params) {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.matrixParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public <RequestType> OperationResult<ResponseType> put(RequestType entity) {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.put(Entity.entity(entity, contentType));
        OperationResult<ResponseType> result =
                new OperationResult<ResponseType>(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());
        return result;
    }

    @Override
    public <RequestType> OperationResult<ResponseType> post(RequestType entity) {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.post(Entity.entity(entity, contentType));
        OperationResult<ResponseType> result =
                new OperationResult<ResponseType>(response, responseClass);
        this.sessionStorage.setSessionId(result.getSessionId());
        return result;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
