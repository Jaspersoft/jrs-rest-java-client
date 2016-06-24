package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain;


import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest({DomainAdapter.class, JerseyRequest.class})
public class DomainAdapterTest extends PowerMockTestCase {

    public static final String URI = "uri";
    public static final List<String> path = asList("resources", "uri");
    public static final String CONTENT_TYPE = "application/repository.domain+json";
    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientDomain> requestMock;

    @Mock
    private OperationResult<ClientDomain> resultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        DomainAdapter adapter = new DomainAdapter(sessionStorageMock, URI);
        ArrayList<String> uri = (ArrayList<String>) getInternalState(adapter, "path");

        // Then
        assertNotNull(adapter);
        assertEquals(uri, path);
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_get() throws Exception {

        // Given
        DomainAdapter adapter = spy(new DomainAdapter(sessionStorageMock, URI));
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setAccept(CONTENT_TYPE);
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        // When
        adapter.get();

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept(CONTENT_TYPE);
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_create() throws Exception {

        // Given
        DomainAdapter adapter = spy(new DomainAdapter(sessionStorageMock, URI));

        ClientDomain domain = new ClientDomain();
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setContentType(CONTENT_TYPE);
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).post(domain);

        // When
        adapter.create(domain);

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).post(domain);
        verify(requestMock, times(1)).setContentType(CONTENT_TYPE);
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_update() throws Exception {

        // Given
        DomainAdapter adapter = spy(new DomainAdapter(sessionStorageMock, URI));
        ClientDomain domain = new ClientDomain();

        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setContentType(CONTENT_TYPE);
        doReturn(requestMock).when(adapter, "buildRequest");;
        doReturn(resultMock).when(requestMock).put(domain);

        // When
        adapter.update(domain);

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).put(domain);
        verify(requestMock, times(1)).setContentType(CONTENT_TYPE);
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_delete() throws Exception {

        // Given
        DomainAdapter adapter = spy(new DomainAdapter(sessionStorageMock, URI));

        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        // When
        adapter.delete();

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).delete();
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_private_static_method() {

        // Given
        final DomainAdapter adapter = new DomainAdapter(sessionStorageMock, URI);
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setAccept(CONTENT_TYPE);
        doReturn(resultMock).when(requestMock).get();

        // When
        OperationResult<ClientDomain> retrieved = adapter.get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(ClientDomain.class),
                eq(new String[]{"resources", URI}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept(CONTENT_TYPE);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);
    }
}