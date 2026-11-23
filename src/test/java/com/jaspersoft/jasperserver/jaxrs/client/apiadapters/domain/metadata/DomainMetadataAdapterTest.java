package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.metadata;

import com.jaspersoft.jasperserver.dto.domain.DomainMetaData;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link DomainMetadataAdapter}
 */
@SuppressWarnings("unchecked")
public class DomainMetadataAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<DomainMetaData> jerseyRequestMock;
    @Mock
    private OperationResult<DomainMetaData> operationResultMock;

    private final String VALID_URI_FAKE = "very/valid/path/to/domain";
    private DomainMetadataAdapter domainMetadataAdapter;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void setUp() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        domainMetadataAdapter = new DomainMetadataAdapter(sessionStorageMock, VALID_URI_FAKE);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock);
    }

    @Test(testName = "DomainMetadataAdapter_constructor")
    public void should_pass_not_null_SessionStorage_and_domainUri_to_super_class_without_any_changes() {
        assertEquals(domainMetadataAdapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_OperationResult_object() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(DomainMetaData.class),
                    eq(new String[]{"domains", "very", "valid", "path", "to", "domain", "metadata"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            assertEquals(operationResultMock, domainMetadataAdapter.retrieve());

            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(DomainMetaData.class),
                    eq(new String[]{"domains", "very", "valid", "path", "to", "domain", "metadata"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).get();
        }
    }
}
