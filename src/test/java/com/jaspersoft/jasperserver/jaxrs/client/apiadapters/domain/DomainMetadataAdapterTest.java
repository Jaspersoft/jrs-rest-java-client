package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.domain.DomainMetaData;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DomainMetadataAdapterTest extends Assert {

    @Mock
    private DomainMetadataAdapter adapterMock;

    @Mock
    private OperationResult<DomainMetaData> expected;

    @Mock
    private SessionStorage storageMock;

    @Mock
    private DomainMetaData entityMock;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void should_return_operation_result() {
        when(adapterMock.retrieve()).thenReturn(expected);
        assertEquals(expected, adapterMock.retrieve());
    }

    @Test
    public void should_pass_not_null_session_storage_to_super_class_without_any_changes() {
        final String fakeValidUri = "very/valid/path/to/domain";
        final DomainMetadataAdapter adapterSpy = spy(new DomainMetadataAdapter(storageMock, fakeValidUri));

        assertNotNull(adapterSpy.getSessionStorage());
        assertEquals(storageMock, adapterSpy.getSessionStorage());
        verify(adapterSpy, times(2)).getSessionStorage();
    }

    /**
     * this test is actually checks if the OperationResult is valid
     * (this means that OperationResult contains a correct entity)
     */
    @Test
    public void should_return_dto_object_from_operation_result() {
        when(adapterMock.retrieve()).thenReturn(expected);
        when(expected.getEntity()).thenReturn(entityMock);
        assertEquals(entityMock, adapterMock.retrieve().getEntity());
    }
}