package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SecureMondrianConnectionResourceBuilder}
 */
public class SecureMondrianConnectionResourceBuilderTest {
    
    @Test
    public void should_pass_params_to_parent_class() {
        SessionStorage storageMock = Mockito.mock(SessionStorage.class);
        ClientSecureMondrianConnection dummyMondrianConnection = new ClientSecureMondrianConnection();
        SecureMondrianConnectionResourceBuilder created = new SecureMondrianConnectionResourceBuilder(dummyMondrianConnection, storageMock);
        SessionStorage retrievedStorage = created.getProcessor().getSessionStorage();
        ClientSecureMondrianConnection retrievedConnection = created.getConnection();
        assertSame(retrievedStorage, storageMock);
        assertSame(retrievedConnection, dummyMondrianConnection);
    }
}