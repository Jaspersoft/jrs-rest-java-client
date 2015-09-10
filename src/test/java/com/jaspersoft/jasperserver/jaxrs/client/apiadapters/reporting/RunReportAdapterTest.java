package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.MultivaluedHashMap;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link RunReportAdapter}
 */
@PrepareForTest({JerseyRequest.class, RunReportAdapter.class})
public class RunReportAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<InputStream> requestMock;
    @Mock
    private OperationResult<InputStream> resultMock;
    @Mock
    private RestClientConfiguration configurationMock;

    @Captor
    private ArgumentCaptor<String> captor;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_a_RequestExecution_instance() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        RunReportAdapter adapterSpy = spy(new RunReportAdapter(sessionStorageMock, "fakeReportUnitUri", ReportOutputFormat.PDF.toString().toLowerCase(), new PageRange(1L, 100L)));

        Callback<OperationResult<InputStream>, Void> callbackSpy =
                spy(new Callback<OperationResult<InputStream>, Void>() {
                    @Override
                    public Void execute(OperationResult<InputStream> data) {
                        newThreadId.set((int) Thread.currentThread().getId());
                        synchronized (this) {
                            this.notify();
                        }
                        return null;
                    }
                });

        doReturn(requestMock).when(adapterSpy, "prepareRunRequest"); // private method mock
        doReturn(resultMock).when(requestMock).get();
        doReturn(null).when(callbackSpy).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncRun(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getFuture());
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        verify(callbackSpy, times(1)).execute(resultMock);
        verify(requestMock, times(1)).get();
    }

    @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", ReportOutputFormat.CSV.toString().toLowerCase(), new PageRange(1, 10));
        adapter.parameter("key", "val");
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock).addParam(anyString(), captor.capture());
        assertEquals(captor.getValue(), "1-10");
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS_if_there_is_more_Then_one_page_and_there_are_unranged() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", "csv", new Integer[]{1, 5, 6, 9, 100});
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock).addParam("pages", "1", "5", "6", "9", "100");
        verifyNoMoreInteractions(requestMock);
    }


    @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS_if_passed_wrong_numbers_of_pages() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", "csv", new Integer[]{0, -1, 1});
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock).addParam("page", "1");
        verify(requestMock, never()).addParam("pages", "0", "-1", "1");
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS_if_passed_all_wrong_numbers_of_pages() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", "csv", new Integer[]{0, -1});
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock, never()).addParam("pages", "0", "-1", "1");
        verify(requestMock, never()).addParam(eq("page"), anyString());
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS_if_there_is_only_one_page() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", "CSV", new Integer[]{5});
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock).addParam("page", "5");
        verifyNoMoreInteractions(requestMock);
    }
   @Test
    /**
     * for {@link RunReportAdapter#run()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_report_as_IS_if_number_of_page_is_zero() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/reports", "uri" + "." + ReportOutputFormat.CSV.toString().toLowerCase()}), any(RunReportErrorHandler.class))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        RunReportAdapter adapter = new RunReportAdapter(sessionStorageMock, "uri", "CSV", new Integer[]{0});
        OperationResult<InputStream> retrieved = adapter.run();

        assertSame(retrieved, resultMock);
        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verify(requestMock,never()).addParam("page", "0");
        verifyNoMoreInteractions(requestMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock, configurationMock);
    }
}