package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@Deprecated
public class ThumbnailsOperationResult extends WithEntityOperationResult<ResourceThumbnailListWrapper> {

    public ThumbnailsOperationResult(Response response, Class<? extends ResourceThumbnailListWrapper> entityClass) {
        super(response, entityClass);
    }

    @Override
    public ResourceThumbnailListWrapper getEntity() {
        try {
            return new ResourceThumbnailListWrapper(response.readEntity(new GenericType<List<ResourceThumbnail>>() {}));
        } catch (Exception e) {
            return null;
        }
    }
}
