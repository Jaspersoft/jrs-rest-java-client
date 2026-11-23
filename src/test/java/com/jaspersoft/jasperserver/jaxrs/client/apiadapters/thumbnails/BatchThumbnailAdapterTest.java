package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnailsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.RequestMethod;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link BatchThumbnailAdapter}
 */
@SuppressWarnings("unchecked")
public class BatchThumbnailAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ResourceThumbnailsListWrapper> jerseyRequestMock;
    @Mock
    private OperationResult<ResourceThumbnailsListWrapper> operationResultMock;
    @Captor
    private ArgumentCaptor<MultivaluedHashMap<String, String>> argument;

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
     * for {@link BatchThumbnailAdapter#BatchThumbnailAdapter(SessionStorage)}
     */
    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);
        SessionStorage retrieved = thumbnailAdapter.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    /**
     * for {@link BatchThumbnailAdapter#report(String)}
     */
    @Test
    public void should_set_report_uri() throws Exception {
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

        BatchThumbnailAdapter retrieved = thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        MultivaluedHashMap<String, String> params = getField(retrieved, "params");
        List<String> list = params.get("uri");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(list.get(0), "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    /**
     * for {@link BatchThumbnailAdapter#defaultAllowed(Boolean)}
     */
    @Test
    public void should_set_thumbnails_parameter() throws Exception {
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

        BatchThumbnailAdapter retrieved = thumbnailAdapter.defaultAllowed(true);

        Boolean param = getField(retrieved, "defaultAllowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(param, Boolean.TRUE);
    }

    /**
     * for {@link BatchThumbnailAdapter#get()} — POST mode (default)
     */
    @Test
    public void should_return_proper_operation_result_with_default_request_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(ResourceThumbnailsListWrapper.class),
                    eq(new String[]{"thumbnails"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.setContentType("application/x-www-form-urlencoded")).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.post(any(MultivaluedHashMap.class))).thenReturn(operationResultMock);

            BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

            OperationResult<ResourceThumbnailsListWrapper> retrieved = thumbnailAdapter
                    .reports("/public/Samples/Reports/07g.RevenueDetailReport",
                            "/public/Samples/Reports/03._Store_Segment_Performance_Report")
                    .get();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(ResourceThumbnailsListWrapper.class),
                    eq(new String[]{"thumbnails"}),
                    any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock).setContentType(eq("application/x-www-form-urlencoded"));
            verify(jerseyRequestMock).post(any(MultivaluedHashMap.class));
            verify(jerseyRequestMock, never()).addParams(argument.capture());
            verify(jerseyRequestMock, never()).get();
            verify(jerseyRequestMock).post(argument.capture());
            assertNotNull(argument.getValue());
            assertTrue(argument.getValue().containsKey("uri"));
            assertSame(argument.getValue().get("uri").get(0),
                    "/public/Samples/Reports/07g.RevenueDetailReport");
        }
    }

    /**
     * for {@link BatchThumbnailAdapter#get()} — GET mode
     */
    @Test
    public void should_return_proper_operation_result_with_get_request_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(ResourceThumbnailsListWrapper.class),
                    eq(new String[]{"thumbnails"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(any(MultivaluedHashMap.class))).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

            OperationResult<ResourceThumbnailsListWrapper> retrieved = thumbnailAdapter
                    .reports("/public/Samples/Reports/07g.RevenueDetailReport",
                            "/public/Samples/Reports/03._Store_Segment_Performance_Report")
                    .requestMethod(RequestMethod.GET)
                    .get();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(ResourceThumbnailsListWrapper.class),
                    eq(new String[]{"thumbnails"}),
                    any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock, never()).setContentType(eq("application/x-www-form-urlencoded"));
            verify(jerseyRequestMock, never()).post(argument.capture());
            verify(jerseyRequestMock).addParams(argument.capture());
            verify(jerseyRequestMock).get();
            assertNotNull(argument.getValue());
            assertTrue(argument.getValue().containsKey("uri"));
            assertSame(argument.getValue().get("uri").get(0),
                    "/public/Samples/Reports/07g.RevenueDetailReport");
        }
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
