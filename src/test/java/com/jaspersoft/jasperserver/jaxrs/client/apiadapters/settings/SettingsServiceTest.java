package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.*;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class})
public class SettingsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private SingleSettingsAdapter settingsAdapterMock;

    private SettingsService service;

    @BeforeMethod
    public void before() {
        initMocks(this);
        service = new SettingsService(sessionStorageMock);
    }

    @Test
    public void should_return_proper_settings_adapter() throws Exception {
        //when
        SingleSettingsAdapter retrieved = service.settings();
        //then
        assertNotNull(retrieved);

    }

    @Test
    public void should_invoke_settings_method() throws Exception {
        //when
        service = spy(new SettingsService(sessionStorageMock));
        doReturn(settingsAdapterMock).when(service).settings();
        SingleSettingsAdapter retrieved = service.settings();
        //then
        assertNotNull(retrieved);

    }

}