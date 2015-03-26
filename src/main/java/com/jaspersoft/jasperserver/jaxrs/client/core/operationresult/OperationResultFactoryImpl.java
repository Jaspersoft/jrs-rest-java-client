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
package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@SuppressWarnings("unchecked")
public class OperationResultFactoryImpl implements OperationResultFactory {

    @Override
    public <T> OperationResult<T> getOperationResult(Response response, Class<T> responseClass) {
        if (isClientResource(responseClass)) {
            responseClass = (Class<T>) getSpecificResourceType(response);
        }
        return getAppropriateOperationResultInstance(response, responseClass);
    }

    /**
     * This method is to be changed in the next release when real ResourceThumbnailListWrapper class will be added.
     */
    @Deprecated
    private <T> OperationResult<T> getAppropriateOperationResultInstance(Response response, Class<T> responseClass) {
        OperationResult<T> result;

        // => code to be removed
        if (response.hasEntity() && responseClass.equals(ResourceThumbnailListWrapper.class)) {
            result = (OperationResult<T>) new ThumbnailsOperationResult(response, (Class<? extends ResourceThumbnailListWrapper>) responseClass);
        } else
        // <=

            if (response.hasEntity()) {
                result = new WithEntityOperationResult<T>(response, responseClass);
            } else {
                result = new NullEntityOperationResult(response, responseClass);
            }
        return result;
    }

    private boolean isClientResource(Class<?> clazz) {
        return clazz != Object.class && ClientResource.class.isAssignableFrom(clazz);
    }

    private Class<? extends ClientResource> getSpecificResourceType(Response response) {
        return ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));
    }

    /**
     * This is a temporary class for managing thumbnails operation result.
     * It will be removed as soon as the proper ResourceThumbnailListWrapper will be written.
     */
    @Deprecated
    protected class ThumbnailsOperationResult extends WithEntityOperationResult<ResourceThumbnailListWrapper> {

        public ThumbnailsOperationResult(Response response, Class<? extends ResourceThumbnailListWrapper> entityClass) {
            super(response, entityClass);
        }

        @Override
        public ResourceThumbnailListWrapper getEntity() {
            try {
                return new ResourceThumbnailListWrapper(response.readEntity(new GenericType<List<ResourceThumbnail>>() {
                }));
            } catch (Exception e) {
                return null;
            }
        }
    }
}
