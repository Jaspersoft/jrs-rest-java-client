/*
 * Copyright © 2014-2022. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.favorites;

import com.jaspersoft.jasperserver.dto.favorite.FavoriteResource;
import com.jaspersoft.jasperserver.dto.favorite.FavoriteResourceListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tetyana Matveyeva
 */
public class FavoritesService extends AbstractAdapter {
    public static final String SERVICE_URI = "favorites";
    public static final String DELETE_URI = "delete";
    private List<String> resourceUris;

    public FavoritesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public FavoritesService forResource(String uri) {
        resourceUris = new ArrayList<>();
        resourceUris.add(uri);
        return this;
    }

    public FavoritesService forResources(List<String> uris) {
        resourceUris = new ArrayList<>();
        resourceUris.addAll(uris);
        return this;
    }

    public OperationResult<FavoriteResourceListWrapper> add() {
        JerseyRequest<FavoriteResourceListWrapper> request = buildAddRequest();
        FavoriteResourceListWrapper body = new FavoriteResourceListWrapper()
                .setFavorites(resourceUris.stream().map(FavoriteResource::new).collect(Collectors.toList()));
        return request.post(body);
    }

    public OperationResult<Object> delete() {
        JerseyRequest<Object> request = buildDeleteRequest();
        FavoriteResourceListWrapper body = new FavoriteResourceListWrapper()
                .setFavorites(resourceUris.stream().map(FavoriteResource::new).collect(Collectors.toList()));
        return request.post(body);
    }

    private JerseyRequest<FavoriteResourceListWrapper> buildAddRequest() {
        JerseyRequest<FavoriteResourceListWrapper> request =
                JerseyRequest.buildRequest(sessionStorage, FavoriteResourceListWrapper.class, new String[]{SERVICE_URI}, new DefaultErrorHandler());
        request.setAccept(MediaType.APPLICATION_JSON);
        request.setContentType(MediaType.APPLICATION_JSON);
        return request;
    }

    private JerseyRequest<Object> buildDeleteRequest() {
        JerseyRequest<Object> request =
                JerseyRequest.buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI, DELETE_URI}, new DefaultErrorHandler());
        request.setAccept(MediaType.APPLICATION_JSON);
        return request;
    }

}
