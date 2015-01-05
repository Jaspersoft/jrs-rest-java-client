package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import junit.framework.Assert;
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
import java.io.InputStream;
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
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        SessionStorage retrieved = thumbnailAdapter.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#report(String)}
     */
    public void should_set_report_uri() {

        /** Given **/
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        /** When **/
        SingleThumbnailAdapter retrieved = thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");

        /** Then **/
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) Whitebox.getInternalState(thumbnailAdapter, "params");
        List<String> list = params.get("uri");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(list.get(0), "/public/Samples/Reports/07g.RevenueDetailReport");
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#parameter(ThumbnailsParameter, Boolean)}
     */
    public void should_set_thumbnails_parameter() {

        /** Given **/
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);

        /** When **/
        SingleThumbnailAdapter retrieved = thumbnailAdapter.parameter(ThumbnailsParameter.DEFAULT_ALLOWED, true);

        /** Then **/
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) Whitebox.getInternalState(thumbnailAdapter, "params");
        List<String> list = params.get("default_allowed");
        assertSame(retrieved, thumbnailAdapter);
        assertEquals(list.get(0), Boolean.TRUE.toString());
    }

    @Test
    /**
     * for {@link SingleThumbnailAdapter#get()}
     */
    public void should_return_proper_operation_result() {

        /** Given **/
        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"/thumbnails/public/Samples/Reports/07g.RevenueDetailReport"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        Mockito.when(jerseyRequestMock.setAccept("image/jpeg")).thenReturn(requestBuilderMock);
        Mockito.when(requestBuilderMock.get()).thenReturn(operationResultMock);
        SingleThumbnailAdapter thumbnailAdapter = new SingleThumbnailAdapter(sessionStorageMock);
        thumbnailAdapter.report("/public/Samples/Reports/07g.RevenueDetailReport");


        /** When **/
        OperationResult<InputStream> retrieved = thumbnailAdapter.get();


        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"/thumbnails/public/Samples/Reports/07g.RevenueDetailReport"}),
                any(DefaultErrorHandler.class));
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, requestBuilderMock, operationResultMock);
    }
}