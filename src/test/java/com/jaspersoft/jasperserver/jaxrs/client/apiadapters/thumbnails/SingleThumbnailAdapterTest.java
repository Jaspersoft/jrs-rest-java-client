package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SingleThumbnailAdapter}
 */
@SuppressWarnings("unchecked")
public class SingleThumbnailAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<InputStream> jerseyRequestMock;
    @Mock
    private OperationResult<InputStream> operationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock);
        mockitoSession.close();
    }

    /**
     * for {@link SingleThumbnailAdapter#SingleThumbnailAdapter(SessionStorage)}
     */
    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        SessionStorage retrieved = thumbnailAdapter.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    /**
     * for {@link SingleThumbnailAdapter#report(String)} — non-null uri is stored
     */
    @Test
    public void should_set_report_uri() throws Exception {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        String reportName = getField(thumbnailAdapter, "reportUri");
        assertEquals(reportName, "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    /**
     * for {@link SingleThumbnailAdapter#report(String)} — null uri is not stored
     */
    @Test
    public void should_not_set_null_report_uri() throws Exception {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        thumbnailAdapter.report(null);

        String reportName = getField(thumbnailAdapter, "reportUri");
        assertNull(reportName);
    }

    /**
     * for {@link SingleThumbnailAdapter#get()} — throws when uri was null
     */
    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_report_uri_is_null() {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report(null).get();
    }

    /**
     * for {@link SingleThumbnailAdapter#get()} — throws when uri was empty
     */
    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_report_uri_is_empty() {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report("").get();
    }

    /**
     * for {@link SingleThumbnailAdapter#report(String)} — empty uri is not stored
     */
    @Test
    public void should_not_set_empty_report_uri() throws Exception {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        thumbnailAdapter.report("");

        String reportName = getField(thumbnailAdapter, "reportUri");
        assertNull(reportName);
    }

    /**
     * for {@link SingleThumbnailAdapter#defaultAllowed(Boolean)} — defaultAllowed is stored
     */
    @Test
    public void should_set_thumbnails_parameter() throws Exception {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        SingleThumbnailAdapter retrieved = thumbnailAdapter.defaultAllowed(true);

        Boolean param = getField(retrieved, "defaultAllowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(param, Boolean.TRUE);
    }

    /**
     * for {@link SingleThumbnailAdapter#defaultAllowed(Boolean)} chained with report — both fields set
     */
    @Test
    public void should_set_report_uri_with_default_allowed() throws Exception {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        SingleThumbnailAdapter retrieved = thumbnailAdapter
                .defaultAllowed(true)
                .report("/public/Samples/Reports/07g.RevenueDetailReport");

        Boolean param = getField(retrieved, "defaultAllowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(param, Boolean.TRUE);
    }

    /**
     * for {@link SingleThumbnailAdapter#get()} — returns proper result and sends correct request
     */
    @Test
    public void should_return_proper_operation_result() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"thumbnails", "", "public", "Samples", "Reports", "07g.RevenueDetailReport"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
            thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

            OperationResult<InputStream> retrieved = thumbnailAdapter.get();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock).setAccept(eq("image/jpeg"));
            verify(jerseyRequestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"thumbnails", "", "public", "Samples", "Reports", "07g.RevenueDetailReport"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    /**
     * for {@link SingleThumbnailAdapter#get()} — throws when reportUri is null
     */
    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception() {
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report(null);
        thumbnailAdapter.get();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String fieldName) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (T) field.get(target);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
