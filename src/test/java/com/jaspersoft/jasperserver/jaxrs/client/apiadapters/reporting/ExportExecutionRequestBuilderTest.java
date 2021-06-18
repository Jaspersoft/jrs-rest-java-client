package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.AttachmentDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;
import org.apache.commons.io.IOUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
* Unit tests for {@link ExportExecutionRequestBuilder}
*/
@PrepareForTest({JerseyRequest.class, IOUtils.class, ExportExecutionRequestBuilder.class})
public class ExportExecutionRequestBuilderTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ExportDescriptor descriptorMock;

    @Mock
    private JerseyRequest<String> stringJerseyRequestMock;

    @Mock
    private JerseyRequest<InputStream> streamJerseyRequestMock;

    @Mock
    private OperationResult<String> resultMock;

    @Mock
    private OperationResult<InputStream> streamedResultMock;

    @Mock
    private JerseyRequest<ReportExecutionStatusEntity> entityJerseyRequestMock;

    @Mock
    private OperationResult<ReportExecutionStatusEntity> statusEntityOperationResultMock;

    @Mock
    private InputStream streamMock;

    @Mock
    private ReportExecutionStatusEntity statusEntityMock;

    @Mock
    private HtmlReport reportMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test (enabled = false)
    public void should_return_HtmlReport_instance_if_ExportDescriptor_is_a_proper_val() throws Exception {

        /* Given */
        String exportId = "exportId";
        String requestId = "requestId";

        final AttachmentDescriptor desc = spy(new AttachmentDescriptor());
        desc.setContentType("json");
        desc.setFileName("myFile");

        mockStatic(JerseyRequest.class);
        when(buildRequest(sessionStorageMock, String.class, new String[]{"/reportExecutions", requestId, "/exports", exportId, "/outputResource"})).thenReturn(stringJerseyRequestMock);

        doReturn(resultMock).when(stringJerseyRequestMock).get();
        doReturn("entity").when(resultMock).getEntity();

        doReturn(new ArrayList<AttachmentDescriptor>() {{
            add(desc);
        }}).when(descriptorMock).getAttachments();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));
        doReturn(statusEntityOperationResultMock).when(builderSpy).attachment(anyString());
        doReturn(streamMock).when(statusEntityOperationResultMock).getEntity();

        mockStatic(IOUtils.class);
        when(IOUtils.toByteArray(streamMock)).thenReturn(new byte[]{});


        /* When */
        HtmlReport retrieved = builderSpy.htmlReport(descriptorMock);


        /* Then */
        assertNotNull(retrieved);

        verifyStatic(times(1));
        buildRequest(sessionStorageMock, String.class, new String[]{"/reportExecutions", requestId, "/exports", exportId, "/outputResource"});

        verifyStatic(times(1));
        IOUtils.toByteArray(streamMock);

        verify(stringJerseyRequestMock, times(1)).get();
        verify(resultMock, times(1)).getEntity();
        verify(descriptorMock, times(1)).getAttachments();
        verify(desc, times(2)).getFileName();
        verify(builderSpy, times(1)).attachment(anyString());
    }

    @Test
    public void should_retrieve_attachment_asynchronously() throws InterruptedException {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";
        String attachmentId = "attachmentId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportExecutionRequestBuilder builderSpy = Mockito.spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));

        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "attachments", attachmentId}))).thenReturn(streamJerseyRequestMock);
        Mockito.when(buildRequest(eq(sessionStorageMock),
                eq(ReportExecutionStatusEntity.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "status"}))).thenReturn(entityJerseyRequestMock);

        Mockito.doReturn(statusEntityOperationResultMock).when(entityJerseyRequestMock).get();
        Mockito.doReturn(streamedResultMock).when(streamJerseyRequestMock).get();
        Mockito.doReturn(statusEntityMock).when(statusEntityOperationResultMock).getEntity();
        Mockito.doReturn("execution").doReturn("ready").when(statusEntityMock).getValue();

        final Callback<OperationResult<InputStream>, Void> callback = Mockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });


        /** When **/
        RequestExecution retrieved = builderSpy.asyncAttachment(attachmentId, callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        verify(streamJerseyRequestMock, times(1)).get();
        verify(entityJerseyRequestMock, times(2)).get();
        verify(statusEntityOperationResultMock, times(2)).getEntity();
        verify(statusEntityMock, times(2)).getValue();
        verify(callback).execute(streamedResultMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_invalid_attachmentId() {

        String requestId = "requestId";
        String exportId = "exportId";

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));
        builderSpy.asyncAttachment("", new Callback<OperationResult<InputStream>, Object>() {
            @Override
            public Object execute(OperationResult<InputStream> data) {
                return null;
            }
        });
    }

    @Test
    public void should_retrieve_output_resource_asynchronously() throws InterruptedException {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "outputResource"}))).thenReturn(streamJerseyRequestMock);


        doReturn(streamedResultMock).when(streamJerseyRequestMock).get();

        final Callback<OperationResult<InputStream>, Void> callback = spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        /** When **/
        RequestExecution retrieved = builderSpy.asyncOutputResource(callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        verify(streamJerseyRequestMock, times(1)).get();
        verify(callback).execute(streamedResultMock);
    }

    @Test
    public void should_send_html_report_asynchronously() throws InterruptedException {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));
        PowerMockito.doReturn(reportMock).when(builderSpy).htmlReport(any(ExportDescriptor.class));

        final Callback<HtmlReport, Void> callback = spy(new Callback<HtmlReport, Void>() {
            @Override
            public Void execute(HtmlReport data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });


        /** When **/
        RequestExecution retrieved = builderSpy.asyncHtmlReport(new ExportDescriptor(), callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(callback).execute(reportMock);
        Mockito.verify(builderSpy).htmlReport(any(ExportDescriptor.class));
    }

    @Test
    public void should_return_operation_result() {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "outputResource"}))).thenReturn(streamJerseyRequestMock);
        doReturn(streamedResultMock).when(streamJerseyRequestMock).get();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));
        OperationResult<InputStream> retrieved = builderSpy.outputResource();

        assertSame(retrieved, streamedResultMock);
        verifyStatic();
        buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "outputResource"}));
    }


    @Test
    public void should_retrieve_status_entity_asynchronously() throws InterruptedException {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ReportExecutionStatusEntity.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "status"}))).thenReturn(entityJerseyRequestMock);
        doReturn(statusEntityOperationResultMock).when(entityJerseyRequestMock).get();

        final Callback<OperationResult<ReportExecutionStatusEntity>, Void> callback = spy(new Callback<OperationResult<ReportExecutionStatusEntity>, Void>() {
            @Override
            public Void execute(OperationResult<ReportExecutionStatusEntity> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });


        /** When **/
        RequestExecution retrieved = builderSpy.asyncStatus(callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        verify(entityJerseyRequestMock, times(1)).get();
        verify(callback).execute(statusEntityOperationResultMock);

    }

    @Test
    public void should_get_attachment_as_stream() {

        /** Given **/
        String requestId = "requestId";
        String exportId = "exportId";
        String attachmentId = "attachmentId";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "attachments", attachmentId}))).thenReturn(streamJerseyRequestMock);
        PowerMockito.doReturn(streamedResultMock).when(streamJerseyRequestMock).get();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));

        PowerMockito.doReturn(statusEntityOperationResultMock).when(builderSpy).status();
        PowerMockito.doReturn(statusEntityMock).when(statusEntityOperationResultMock).getEntity();
        PowerMockito.doReturn("execution").doReturn("ready").when(statusEntityMock).getValue();
        //
        /** When **/
        OperationResult<InputStream> retrieved = builderSpy.attachment(attachmentId);

        /** Then **/
        verifyStatic();
        buildRequest(eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "attachments", attachmentId}));

        assertNotNull(retrieved);
        Mockito.verify(builderSpy, times(2)).status();
        Mockito.verify(streamJerseyRequestMock).get();
        Mockito.verify(statusEntityOperationResultMock, times(2)).getEntity();
        Mockito.verify(statusEntityMock, times(2)).getValue();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_invalid_id() {

        String requestId = "requestId";
        String exportId = "exportId";
        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));
        builderSpy.attachment("/");

    }

    @Test
    public void should_throw_exception_when_cannot_read_stream() throws FileNotFoundException {

        String requestId = "requestId";
        String exportId = "exportId";

        mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(String.class),
                eq(new String[]{"reportExecutions", requestId, "exports", exportId, "outputResource"}))).thenReturn(stringJerseyRequestMock);

        PowerMockito.doReturn(resultMock).when(stringJerseyRequestMock).get();
        PowerMockito.doReturn("____").when(resultMock).getEntity();

        ExportExecutionRequestBuilder builderSpy = spy(new ExportExecutionRequestBuilder(sessionStorageMock, requestId, exportId));

        PowerMockito.doReturn(streamedResultMock).when(builderSpy).attachment(anyString());
        PowerMockito.doReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        }).when(streamedResultMock).getEntity();

        ExportDescriptor descriptor = new ExportDescriptor();
        descriptor.setAttachments(Arrays.asList(new AttachmentDescriptor()));

        try {
            builderSpy.htmlReport(descriptor);
        } catch (Exception e) {
            assertTrue(instanceOf(JSClientException.class).matches(e));
            assertEquals(e.getMessage(), "Error while reading thumbnail content");
        }
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, descriptorMock, stringJerseyRequestMock, resultMock, streamMock, statusEntityOperationResultMock);
    }
}