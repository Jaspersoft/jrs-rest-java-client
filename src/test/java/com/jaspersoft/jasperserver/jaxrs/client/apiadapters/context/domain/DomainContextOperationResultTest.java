package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
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
@PrepareForTest({DomainContextOperationResult.class, Response.class, SingleContextAdapter.class})
public class DomainContextOperationResultTest extends PowerMockTestCase {

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

    @BeforeMethod
    public void before() {
        initMocks(this);

    }

    @AfterMethod
    public void after() {
        reset(contextServiceMock
                , singleContextAdapterMock
                , responseMock
                , operationResultMock
                , queryResultDataOperationResult
                , singleContextForCreateAdapterMock
                , resourceLookupOperationResultMock
                , resourceLookupMock
        );

    }


    @Test
    public void should_return_proper_operationResult_when_get_metadata() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(),
                any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        //when
        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.getMetadata();

        //then
        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(),any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();

    }

    @Test
    public void should_return_proper_entity() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<ClientResourceLookup>(responseMock, ClientResourceLookup.class, contextServiceMock);
        Whitebox.setInternalState(domainContextOperationResult, "entity", resourceLookupMock);

        //when
        ClientResourceLookup entity = domainContextOperationResult.getEntity();

        //then
        assertSame(resourceLookupMock, entity);
        verify(responseMock).getHeaderString("Location");
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata_with_parameter() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(),
                any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        //when
        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.addParam("key", "value").getMetadata();

        //then
        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(),any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata_with_parameters() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(),
                any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(singleContextAdapterMock).metadata();

        //when
        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.addParam("key", "value1", "value2").getMetadata();

        //then
        assertSame(operationResultMock, metadata);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString(),any(Class.class), anyString());
        verify(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        verify(singleContextAdapterMock).metadata();
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_return_proper_operationResult_when_get_metadata_for_expired_context() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = spy(new DomainContextOperationResult<ClientResourceLookup>(responseMock, ClientResourceLookup.class, contextServiceMock));
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString(),
                any(Class.class), anyString());
        doReturn(singleContextAdapterMock).when(singleContextAdapterMock).addParameters(any(MultivaluedHashMap.class));
        doThrow(ResourceNotFoundException.class).when(singleContextAdapterMock).metadata();
        doReturn(singleContextForCreateAdapterMock).when(contextServiceMock).context(any(Class.class), anyString());
        doReturn(resourceLookupOperationResultMock).when(singleContextForCreateAdapterMock).create(any(ClientResourceLookup.class));
        doReturn(responseMock).when(resourceLookupOperationResultMock).getResponse();

        //when
        OperationResult<PresentationGroupElement> metadata = domainContextOperationResult.getMetadata();

        //then
        assertSame(operationResultMock, metadata);

    }

    @Test
    public void should_return_proper_operationResult_when_execute_query() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString());
        doReturn(operationResultMock).when(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));

        //when
        OperationResult<ClientMultiLevelQueryResultData> queryResultSet = domainContextOperationResult.executeQuery(new ClientMultiLevelQuery());

        //then
        assertSame(operationResultMock, queryResultSet);
        verify(responseMock).getHeaderString("Location");
        verify(contextServiceMock).context(anyString());
        verify(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_return_proper_operationResult_when_execute_query_for_expired_context() throws Exception {
        //given
        doReturn("/test_uuId").when(responseMock).getHeaderString("Location");
        domainContextOperationResult = new DomainContextOperationResult<>(responseMock, ClientResourceLookup.class, contextServiceMock);
        doReturn(singleContextAdapterMock).when(contextServiceMock).context(anyString());
        doThrow(ResourceNotFoundException.class).when(singleContextAdapterMock).executeQuery(any(ClientMultiLevelQuery.class));

        doReturn(singleContextForCreateAdapterMock).when(contextServiceMock).context(any(Class.class), anyString());
        doReturn(resourceLookupOperationResultMock).when(singleContextForCreateAdapterMock).create(any(ClientResourceLookup.class));
        doReturn(responseMock).when(resourceLookupOperationResultMock).getResponse();

        //when
        OperationResult<ClientMultiLevelQueryResultData> queryResultSet = domainContextOperationResult.executeQuery(new ClientMultiLevelQuery());

        // then
        // an exception should be thrown

    }
}