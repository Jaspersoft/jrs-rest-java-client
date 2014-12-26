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

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.*;

public abstract class OperationResult<T> {

    protected Response response;
    protected Class<? extends T> entityClass;

    protected T entity;
    protected String serializedContent;

    public OperationResult(Response response, Class<? extends T> entityClass) {
        this.response = response;
        response.bufferEntity();
        this.entityClass = entityClass;
    }

    public <Type extends ResourceThumbnail> List<Type> getEntityList(Class<Type> clazz) {
        //
        // fixme: readEntity(new GenericType<List<ResourceThumbnail>>() {}); works fine, but all goes wrong if we use List<T>
        //
        List<Type> unconvertedThumbnails = response.readEntity(new GenericType<List<Type>>() {});
        return convert(unconvertedThumbnails, clazz);
    }

    @SuppressWarnings("unchecked")
    private <Type extends ResourceThumbnail> List<Type> convert(List unconvertedThumbnails, Class<Type> clazz) {
        List<Type> list = new ArrayList<Type>();
        for (Object th : unconvertedThumbnails) {
            LinkedHashMap<String, String> thumbnailAsMap = (LinkedHashMap<String, String>) th;
            Type instance;
            try {
                assert clazz != null;
                instance = clazz.getConstructor().newInstance();
                for (Map.Entry<String, String> entry : thumbnailAsMap.entrySet()) {
                    //
                    // check is entity represent ResourceThumbnail class and set fields
                    //
                    while (clazz != null) {
                        try {
                            Field field = clazz.getDeclaredField(entry.getKey());
                            field.setAccessible(true);
                            field.set(instance, entry.getValue());
                            break;
                        } catch (NoSuchFieldException e) {
                            clazz = (Class<Type>) clazz.getSuperclass();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    }
                }
                list.add(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public T getEntity() {
        try {
            if (entity == null && !entityClass.equals(ResourceThumbnailListWrapper.class)) {
                entity = response.readEntity(entityClass);
            } else if (entityClass.equals(ResourceThumbnailListWrapper.class)) {
                return (T) new ResourceThumbnailListWrapper(getEntityList(ResourceThumbnail.class));
            }
            return entity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getSerializedContent() {
        try {
            if (serializedContent == null)
                serializedContent = response.readEntity(String.class);
            return serializedContent;
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse() {
        return response;
    }

    public Class<? extends T> getEntityClass() {
        return entityClass;
    }
}
