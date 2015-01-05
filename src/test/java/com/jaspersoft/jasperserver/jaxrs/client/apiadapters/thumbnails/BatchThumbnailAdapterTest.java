package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;
import junit.framework.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;

import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link BatchThumbnailAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({JerseyRequest.class})
public class BatchThumbnailAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest jerseyRequestMock;
    @Mock
    private RequestBuilder<ResourceThumbnailListWrapper> requestBuilderMock;
    @Mock
    private OperationResult<ResourceThumbnailListWrapper> operationResultMock;
    @Captor
    private ArgumentCaptor<MultivaluedHashMap> argument = ArgumentCaptor.forClass(MultivaluedHashMap.class);

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link BatchThumbnailAdapter#BatchThumbnailAdapter(SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);
        SessionStorage retrieved = thumbnailAdapter.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link BatchThumbnailAdapter#report(String)}
     */
    public void should_set_report_uri() {

        /** Given **/
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

        /** When **/
        BatchThumbnailAdapter retrieved = thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        /** Then **/
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) Whitebox.getInternalState(thumbnailAdapter, "params");
        List<String> list = params.get("uri");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(list.get(0), "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    @Test
    /**
     * for {@link BatchThumbnailAdapter#parameter(ThumbnailsParameter, Boolean)}
     */
    public void should_set_thumbnails_parameter() {

        /** Given **/
        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

        /** When **/
        BatchThumbnailAdapter retrieved = thumbnailAdapter.parameter(ThumbnailsParameter.DEFAULT_ALLOWED, true);

        /** Then **/
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) Whitebox.getInternalState(thumbnailAdapter, "params");
        List<String> list = params.get("default_allowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(list.get(0), Boolean.TRUE.toString());
    }

    @Test
    /**
     * for {@link BatchThumbnailAdapter#get()}
     */
    public void should_return_proper_operation_result() {

        /** Given **/
        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(ResourceThumbnailListWrapper.class), eq(new String[]{"/thumbnails"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        Mockito.when(jerseyRequestMock.setContentType("application/x-www-form-urlencoded")).thenReturn(requestBuilderMock);
        Mockito.when(requestBuilderMock.post(any(MultivaluedHashMap.class))).thenReturn(operationResultMock);

        BatchThumbnailAdapter thumbnailAdapter = new BatchThumbnailAdapter(sessionStorageMock);

        /** When **/
        OperationResult<ResourceThumbnailListWrapper> retrieved = thumbnailAdapter.reports("/public/Samples/Reports/07g.RevenueDetailReport", "/public/Samples/Reports/03._Store_Segment_Performance_Report").get();

        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ResourceThumbnailListWrapper.class), eq(new String[]{"/thumbnails"}), any(DefaultErrorHandler.class));
        Mockito.verify(requestBuilderMock).post(argument.capture());
        Assert.assertNotNull(argument.getValue());
        Assert.assertTrue(argument.getValue().containsKey("uri"));
        Assert.assertSame(argument.getValue().get("uri").get(0), "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, requestBuilderMock, operationResultMock);
    }
}