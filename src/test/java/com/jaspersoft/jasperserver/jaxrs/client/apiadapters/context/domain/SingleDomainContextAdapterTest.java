package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings("unchecked")
public class SingleDomainContextAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private ClientResourceLookup clientResourceLookupMock;
    @Mock
    private SingleContextAdapter<ClientResourceLookup, Object> singleContextAdapterMock;
    @Mock
    private OperationResult<ClientResourceLookup> resourceLookupOperationResult;
    @Mock
    private ContextService contextServiceMock;
    @Mock
    private Response responseMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock,
                contextServiceMock,
                singleContextAdapterMock,
                resourceLookupOperationResult,
                clientResourceLookupMock,
                responseMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private void setInternalState(Object obj, String fieldName, Object value) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_return_proper_adapter_with_proper_internal_state() throws Exception {
        SingleDomainContextAdapter contextAdapter = new SingleDomainContextAdapter(sessionStorageMock,
                clientResourceLookupMock,
                ClientResourceLookup.class,
                ContextMediaTypes.DOMAIN_JSON);

        assertEquals(getInternalState(contextAdapter, "contextMimeType"), ContextMediaTypes.DOMAIN_JSON);
        assertEquals(getInternalState(contextAdapter, "contextClass"), ClientResourceLookup.class);
        assertSame(getInternalState(contextAdapter, "context"), clientResourceLookupMock);
        assertNotNull(getInternalState(contextAdapter, "service"));
    }

    @Test
    public void should_return_proper_operationResult_when_create() throws Exception {
        SingleDomainContextAdapter domainContextAdapter = new SingleDomainContextAdapter(sessionStorageMock,
                clientResourceLookupMock,
                ClientResourceLookup.class,
                ContextMediaTypes.RESOURCE_LOOKUP_JSON);
        setInternalState(domainContextAdapter, "service", contextServiceMock);

        doReturn(singleContextAdapterMock).when(contextServiceMock)
                .context(eq(ClientResourceLookup.class), eq(ContextMediaTypes.RESOURCE_LOOKUP_JSON));
        doReturn(resourceLookupOperationResult).when(singleContextAdapterMock).create(clientResourceLookupMock);
        doReturn(responseMock).when(resourceLookupOperationResult).getResponse();
        doReturn(new ClientResourceLookup()).when(resourceLookupOperationResult).getEntity();
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");

        OperationResult<ClientResourceLookup> connection = domainContextAdapter.create();

        assertNotNull(connection);
        assertTrue(connection instanceof DomainContextOperationResult);
        assertSame(getInternalState(connection, "service"), contextServiceMock);
        verify(contextServiceMock).context(eq(ClientResourceLookup.class), eq(ContextMediaTypes.RESOURCE_LOOKUP_JSON));
        verify(singleContextAdapterMock).create(clientResourceLookupMock);
        verify(resourceLookupOperationResult).getResponse();
        verify(resourceLookupOperationResult).getEntity();
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_create_null_context() {
        new SingleDomainContextAdapter(sessionStorageMock,
                null,
                ClientResourceLookup.class,
                ContextMediaTypes.RESOURCE_LOOKUP_JSON);
    }
}
