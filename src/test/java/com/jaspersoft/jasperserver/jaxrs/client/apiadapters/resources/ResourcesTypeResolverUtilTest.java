package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientOlapUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientQuery;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class ResourcesTypeResolverUtilTest {

    @Test
    public void should_return_proper_client_resource() {
        Class<? extends ClientResource> retrieved = ResourcesTypeResolverUtil.getClassForMime("application/repository.olapUnit+{json}");
        assertSame(retrieved, ClientOlapUnit.class);
    }

    @Test
    public void should_proper_MimeType() {
        String retrieved = ResourcesTypeResolverUtil.getMimeType(ClientQuery.class);
        assertEquals(retrieved, "application/repository.query+{mime}");
    }

    @Test
    public void should_return_class_of_object() {
        Class<? extends ClientResource> retrieved = ResourcesTypeResolverUtil.getResourceType(new ClientFolder());
        assertSame(retrieved, ClientFolder.class);
    }

    @Test
    public void should_create_instance_of_class() {
        ResourcesTypeResolverUtil created = new ResourcesTypeResolverUtil();
        assertNotNull(created);
    }
}