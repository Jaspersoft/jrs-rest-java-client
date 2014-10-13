package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.processor;

import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

@PrepareForTest(JerseyRequest.class)
public class CommonOperationProcessorImplTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientSemanticLayerDataSource> requestMock;

    @Mock
    private OperationResult<ClientSemanticLayerDataSource> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_fields_of_instance_with_proper_entities() {

        // given
        final ClientSemanticLayerDataSource resource = new ClientSemanticLayerDataSource();
        final Class<? extends ClientSemanticLayerDataSource> resourceClass = resource.getClass();

        // when
        final CommonOperationProcessorImpl operationProcessor = new CommonOperationProcessorImpl(resource, resourceClass, sessionStorageMock);

        // than
        Object settedResource = Whitebox.getInternalState(operationProcessor, "resource");
        Object settedResourceTypeClass = Whitebox.getInternalState(operationProcessor, "resourceTypeClass");
        Object settedSessionStorage = Whitebox.getInternalState(operationProcessor, "sessionStorage");

        assertSame(settedSessionStorage, sessionStorageMock);
        assertTrue(instanceOf(ClientSemanticLayerDataSource.class).matches(settedResource));
        assertTrue(instanceOf(Class.class).matches(settedResourceTypeClass));
        assertTrue(instanceOf(SessionStorage.class).matches(settedSessionStorage));
    }


    @Test
    @SuppressWarnings("unchecked")
    public void should_create_resource_and_return_operation_result() {

        // given
        final ClientSemanticLayerDataSource resource = new ClientSemanticLayerDataSource();
        final Class<? extends ClientSemanticLayerDataSource> resourceClass = resource.getClass();
        final CommonOperationProcessorImpl operationProcessor = new CommonOperationProcessorImpl(resource, resourceClass, sessionStorageMock);
        FormDataMultiPart dummyForm = new FormDataMultiPart();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientSemanticLayerDataSource.class), eq(new String[]{"/resources", "/path/to/folder"}))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).post(dummyForm);


        // when
        OperationResult retrieved = operationProcessor.create(dummyForm, MediaType.APPLICATION_JSON_TYPE, "/path/to/folder");


        // than
        assertNotNull(retrieved);

        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientSemanticLayerDataSource.class), eq(new String[]{"/resources", "/path/to/folder"}));

        verify(requestMock, times(1)).setContentType(MediaType.MULTIPART_FORM_DATA);
        verify(requestMock, times(1)).post(dummyForm);

        verifyNoMoreInteractions(requestMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_resource_wrapped_with_operation_result() {

        // given
        final ClientSemanticLayerDataSource resource = new ClientSemanticLayerDataSource();
        final Class<? extends ClientSemanticLayerDataSource> resourceClass = resource.getClass();
        final CommonOperationProcessorImpl operationProcessor = new CommonOperationProcessorImpl(resource, resourceClass, sessionStorageMock);

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientSemanticLayerDataSource.class), eq(new String[]{"/resources", "/path/to/folder"}))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();


        // when
        OperationResult retrieved = operationProcessor.get("/path/to/folder");


        // than
        assertNotNull(retrieved);

        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientSemanticLayerDataSource.class), eq(new String[]{"/resources", "/path/to/folder"}));
        verify(requestMock, times(1)).get();
        verifyNoMoreInteractions(requestMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock);
    }
}