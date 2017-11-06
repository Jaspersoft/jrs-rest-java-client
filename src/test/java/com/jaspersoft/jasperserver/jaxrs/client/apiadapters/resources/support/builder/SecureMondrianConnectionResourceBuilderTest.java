package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.processor.CommonOperationProcessorImpl;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder}
 */
public class SecureMondrianConnectionResourceBuilderTest {
    
    @Test
    public void should_pass_params_to_parent_class() {
        SessionStorage storageMock = Mockito.mock(SessionStorage.class);
        ClientSecureMondrianConnection dummyMondrianConnection = new ClientSecureMondrianConnection();
        SecureMondrianConnectionResourceBuilder created = new SecureMondrianConnectionResourceBuilder(dummyMondrianConnection, storageMock);
        SessionStorage retrievedStorage = ((CommonOperationProcessorImpl)Whitebox.getInternalState(created, "processor")).getSessionStorage();
        ClientSecureMondrianConnection retrievedConnection = Whitebox.getInternalState(created, "connection");
        assertSame(retrievedStorage, storageMock);
        assertSame(retrievedConnection, dummyMondrianConnection);
    }
}