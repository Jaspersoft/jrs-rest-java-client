package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings("unchecked")
public class DomainContextOperationResultTest {

    DomainContextOperationResult<ClientResourceLookup> domainContextOperationResult;

    @Mock
    private SingleContextAdapter<Object, PresentationGroupElement> singleContextAdapterMock;
    @Mock
    private SingleContextAdapter<ClientResourceLookup, PresentationGroupElement> singleContextForCreateAdapterMock;
    @Mock
    private OperationResult<PresentationGroupElement> operationResultMock;
    @Mock
    private OperationResult<ClientMultiLevelQueryResultData> queryResultDataOperationResult;
    @Mock
    private OperationResult<ClientResourceLookup> resourceLookupOperationResultMock;
    @Mock
    private ContextService contextServiceMock;
    @Mock
    private Response responseMock;
    @Mock
    private ClientResourceLookup resourceLookupMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(contextServiceMock,
                singleContextAdapterMock,
                responseMock,
                operationResultMock,
                queryResultDataOperationResult,
                singleContextForCreateAdapterMock,
                resourceLookupOperationResultMock,
                resourceLookupMock);
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
    public void should_return_proper_operationResult_when_get_metadata() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(), any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.getMetadata();

        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(), any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();
    }

    @Test
    public void should_return_proper_entity() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        setInternalState(domainContextOperationResult, "entity", resourceLookupMock);

        ClientResourceLookup entity = domainContextOperationResult.getEntity();

        assertSame(resourceLookupMock, entity);
        verify(responseMock).getHeaderString("Location");
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata_with_parameter() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(), any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.addParam("key", "value").getMetadata();

        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(), any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata_with_parameters() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(), any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.addParam("key", "value1", "value2").getMetadata();

        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(), any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_return_proper_operationResult_when_get_metadata_for_expired_context() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        doReturn(mock(ClientResourceLookup.class)).when(responseMock).readEntity(ClientResourceLookup.class);
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(), any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doThrow(ResourceNotFoundException.class).when(singleContextAdapterMock).metadata();
        doReturn(singleContextForCreateAdapterMock).when(contextServiceMock).context(any(Class.class), anyString());
        doReturn(resourceLookupOperationResultMock).when(singleContextForCreateAdapterMock).create(any(ClientResourceLookup.class));
        doReturn(responseMock).when(resourceLookupOperationResultMock).getResponse();

        domainContextOperationResult.getMetadata();
    }

    @Test
    public void should_return_proper_operationResult_when_execute_query() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString());
        doReturn(operationResultMock).when(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));

        OperationResult<ClientMultiLevelQueryResultData> queryResultSet = domainContextOperationResult.executeQuery(new ClientMultiLevelQuery());

        assertSame(operationResultMock, queryResultSet);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString());
        verify(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_return_proper_operationResult_when_execute_query_for_expired_context() throws Exception {
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        doReturn(mock(ClientResourceLookup.class)).when(responseMock).readEntity(ClientResourceLookup.class);
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString());
        doThrow(ResourceNotFoundException.class).when(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));
        doReturn(singleContextForCreateAdapterMock).when(contextServiceMock).context(any(Class.class), anyString());
        doReturn(resourceLookupOperationResultMock).when(singleContextForCreateAdapterMock).create(any(ClientResourceLookup.class));
        doReturn(responseMock).when(resourceLookupOperationResultMock).getResponse();

        domainContextOperationResult.executeQuery(new ClientMultiLevelQuery());
    }
}
