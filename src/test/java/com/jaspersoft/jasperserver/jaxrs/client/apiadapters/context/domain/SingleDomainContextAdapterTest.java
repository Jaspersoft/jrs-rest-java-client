package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
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
@SuppressWarnings("unchecked")
@PrepareForTest({SingleDomainContextAdapter.class})
public class SingleDomainContextAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private ClientResourceLookup clientResourceLookupMock;
    @Mock
    private JerseyRequest<ClientResourceLookup> resourceLookupJerseyRequest;
    @Mock
    private SingleContextAdapter<ClientResourceLookup, Object> singleContextAdapterMock;
    @Mock
    private OperationResult<ClientResourceLookup> resourceLookupOperationResult;
    @Mock
    private DomainContextOperationResult<ClientResourceLookup> domainContextResourceLookupOperationResult;
    @Mock
    private ContextService contextServiceMock;
    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock
                , configurationMock
                , contextServiceMock
                , singleContextAdapterMock
                , resourceLookupJerseyRequest
                , resourceLookupOperationResult
                , domainContextResourceLookupOperationResult
                , clientResourceLookupMock
                , responseMock
        );

    }

    @Test
    public void should_return_proper_adapter_with_proper_internal_state() throws Exception {
        //when
        SingleDomainContextAdapter contextAdapter = new SingleDomainContextAdapter(sessionStorageMock,
                clientResourceLookupMock,
                ClientResourceLookup.class,
                ContextMediaTypes.DOMAIN_JSON);
        //then
        assertEquals(Whitebox.getInternalState(contextAdapter, "contextMimeType"), ContextMediaTypes.DOMAIN_JSON);
        assertEquals(Whitebox.getInternalState(contextAdapter, "contextClass"), ClientResourceLookup.class);
        assertEquals(Whitebox.getInternalState(contextAdapter, "context"), clientResourceLookupMock);
        assertNotNull(Whitebox.getInternalState(contextAdapter, "service"));
    }


    @Test
    public void should_return_proper_operationResult_when_create() throws Exception {
        //given
        whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        SingleDomainContextAdapter domainContextAdapter = new SingleDomainContextAdapter(sessionStorageMock,
                clientResourceLookupMock,
                ClientResourceLookup.class,
                ContextMediaTypes.RESOURCE_LOOKUP_JSON);

        doReturn(singleContextAdapterMock).when(contextServiceMock).context(any(Class.class), anyString());
        doReturn(resourceLookupOperationResult).when(singleContextAdapterMock).create(clientResourceLookupMock);
        doReturn(responseMock).when(resourceLookupOperationResult).getResponse();
        doReturn(clientResourceLookupMock).when(resourceLookupOperationResult).getEntity();
        whenNew(DomainContextOperationResult.class).withArguments(any(Response.class),
                any(Class.class), eq(contextServiceMock)).thenReturn(domainContextResourceLookupOperationResult);
        //when
        OperationResult<ClientResourceLookup> connection = domainContextAdapter.create();

        //then
        assertSame(domainContextResourceLookupOperationResult, connection);
        verifyNew(ContextService.class).withArguments(sessionStorageMock);
        verify(contextServiceMock).context(any(Class.class), anyString());
        verify(singleContextAdapterMock).create(clientResourceLookupMock);
        verify(resourceLookupOperationResult).getResponse();
        verify(resourceLookupOperationResult).getEntity();
        verifyNew(DomainContextOperationResult.class).withArguments(any(Response.class),
                any(Class.class), eq(contextServiceMock));
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_create_null_context() throws Exception {
        // when
        SingleDomainContextAdapter domainContextAdapter = new SingleDomainContextAdapter(sessionStorageMock,
                null,
                ClientResourceLookup.class,
                ContextMediaTypes.RESOURCE_LOOKUP_JSON);

        //then
        // exception should be thrown
    }
}