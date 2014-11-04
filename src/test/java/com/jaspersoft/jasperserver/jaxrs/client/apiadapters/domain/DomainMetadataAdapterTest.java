package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.dto.domain.DomainMetaData;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link DomainMetadataAdapter}
 */
@PrepareForTest(JerseyRequest.class)
public class DomainMetadataAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<DomainMetaData> jerseyRequestMock;

    @Mock
    private OperationResult<DomainMetaData> operationResultMock;

    private final String VALID_URI_FAKE = "very/valid/path/to/domain";
    private DomainMetadataAdapter domainMetadataAdapter;

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
        domainMetadataAdapter = new DomainMetadataAdapter(sessionStorageMock, VALID_URI_FAKE);
    }

    /**
     * The constructor under test is {@link DomainMetadataAdapter#DomainMetadataAdapter(SessionStorage, String)}
     */
    @Test(testName = "DomainMetadataAdapter_constructor")
    public void should_pass_not_null_SessionStorage_and_domainUri_to_super_class_without_any_changes() {
        assertEquals(sessionStorageMock, domainMetadataAdapter.getSessionStorage());
        assertEquals(VALID_URI_FAKE, domainMetadataAdapter.getDomainURI());
    }

    @Test
    public void should_return_proper_OperationResult_object() {
        mockStatic(JerseyRequest.class);

        when(JerseyRequest.buildRequest(
                any(SessionStorage.class),
                any(Class.class),
                any(String[].class),
                any(ErrorHandler.class)
        )).thenReturn(jerseyRequestMock);

        doReturn(operationResultMock)
                .when(jerseyRequestMock)
                .get();

        assertEquals(operationResultMock, domainMetadataAdapter.retrieve());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock);
    }
}