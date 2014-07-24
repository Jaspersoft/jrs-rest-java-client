package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

@PrepareForTest({SingleRoleRequestAdapter.class, JerseyRequest.class})
public class SingleRoleRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void asyncDelete() {
        // ?
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock);
    }
}