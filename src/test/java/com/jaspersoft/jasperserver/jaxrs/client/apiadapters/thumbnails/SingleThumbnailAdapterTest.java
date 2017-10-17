package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SingleThumbnailAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({JerseyRequest.class})
public class SingleThumbnailAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest jerseyRequestMock;
    @Mock
    private RequestBuilder<InputStream> requestBuilderMock;
    @Mock
    private OperationResult<InputStream> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#SingleThumbnailAdapter(SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        //When
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        SessionStorage retrieved = thumbnailAdapter.getSessionStorage();
        //Then
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_set_report_uri() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        SingleThumbnailAdapter retrieved = thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        // Then
        String reportName = (String) Whitebox.getInternalState(retrieved, "reportUri");
        assertEquals(reportName, "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_not_set_null_report_uri() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        SingleThumbnailAdapter retrieved = thumbnailAdapter.report(null);

        // Then
        String reportName = (String) Whitebox.getInternalState(retrieved, "reportUri");
        assertNull(reportName);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_throw_exception_when_report_uri_is_null() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        thumbnailAdapter.report(null).get();

        // Then
    }


    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_throw_exception_when_report_uri_is_empty() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        thumbnailAdapter.report("").get();

        // Then
    }


    @Test
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_not_set_empty_report_uri() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        SingleThumbnailAdapter retrieved = thumbnailAdapter.report("");

        // Then
        String reportName = (String) Whitebox.getInternalState(retrieved, "reportUri");
        assertNull(reportName);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_set_report_uri_with_default_allowed() {
        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        SingleThumbnailAdapter retrieved = thumbnailAdapter.defaultAllowed(true).report("/public/Samples/Reports/07g.RevenueDetailReport");

        // Then
        Boolean param = (Boolean) Whitebox.getInternalState(retrieved, "defaultAllowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(param, Boolean.TRUE);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#defaultAllowed(Boolean)}
     */
    public void should_set_thumbnails_parameter() {

        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        // When
        SingleThumbnailAdapter retrieved = thumbnailAdapter.defaultAllowed(true);

        // Then
        Boolean param = (Boolean) Whitebox.getInternalState(retrieved, "defaultAllowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(param, Boolean.TRUE);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#get()}
     */
    public void should_return_proper_operation_result() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"thumbnails", "", "public", "Samples", "Reports", "07g.RevenueDetailReport"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        when(jerseyRequestMock.setAccept("image/jpeg")).thenReturn(jerseyRequestMock);
        when(jerseyRequestMock.get()).thenReturn(operationResultMock);
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        // When
        OperationResult<InputStream> retrieved = thumbnailAdapter.get();


        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).setAccept(eq("image/jpeg"));
        verify(jerseyRequestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"thumbnails", "", "public", "Samples", "Reports", "07g.RevenueDetailReport"}),
                any(DefaultErrorHandler.class));

    }


    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    /**
     * for {@link SingleThumbnailAdapter#get()}
     */
    public void should_throw_exception() {

        // Given
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report(null);

        // When
        OperationResult<InputStream> retrieved = thumbnailAdapter.get();


        // Then
        //An exception should be thrown


    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, requestBuilderMock, operationResultMock);
    }
}