package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SecureMondrianConnectionResourceBuilder}
 */
public class SecureMondrianConnectionResourceBuilderTest {

    @Mock
    private SessionStorage sessionStorageMock;

    private ClientSecureMondrianConnection dummyMondrianConnection;


    @BeforeMethod
    public void before() {
        initMocks(this);
        dummyMondrianConnection = new ClientSecureMondrianConnection();
    }

    @Test
    public void should_pass_params_to_parent_class() {
        SecureMondrianConnectionResourceBuilder created = new SecureMondrianConnectionResourceBuilder(dummyMondrianConnection, sessionStorageMock);
        SessionStorage retrievedStorage = created.getProcessor().getSessionStorage();
        ClientSecureMondrianConnection retrievedConnection = created.getConnection();

        assertSame(retrievedStorage, sessionStorageMock);
        assertSame(retrievedConnection, dummyMondrianConnection);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock);
        dummyMondrianConnection = null;
    }
}