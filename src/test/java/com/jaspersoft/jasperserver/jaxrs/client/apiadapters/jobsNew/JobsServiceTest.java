package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobsNew;

import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;
import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientCalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.concurrent.atomic.AtomicInteger;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService}
 */
@PrepareForTest({JerseyRequest.class, JobsService.class})
public class JobsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private BatchJobsOperationsAdapter expectedAdapterMock;

    @Mock
    private SingleJobOperationsAdapter expectedJobOperationsAdapter;

    @Mock
    private JerseyRequest<ClientReportJob> jobRequestMock;

    @Mock
    private JerseyRequest<ClientCalendarNameListWrapper> wrapperRequestMock;

    @Mock
    private OperationResult<ClientReportJob> expectedJobOperationResultMock;

    @Mock
    private OperationResult<ClientCalendarNameListWrapper> expectedWrapperOperationResultMock;

    @Mock
    private ClientReportJob reportMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private Callback<OperationResult<ClientCalendarNameListWrapper>, Object> callbackMock;

    @Mock
    private RequestExecution executionMock;

    @Mock
    private SingleCalendarOperationsAdapter expectedCalendarOperationsAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "jobs")
    public void should_return_proper_adapter() throws Exception {

        // Given
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        whenNew(BatchJobsOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class)
                .withArguments(sessionStorageMock)
                .thenReturn(expectedAdapterMock);

        // When
        BatchJobsOperationsAdapter retrieved = serviceSpy.jobs();

        // Then
        assertSame(retrieved, expectedAdapterMock);
    }

    @Test(testName = "job")
    public void should_return_proper_SingleJobOperationsAdapter() throws Exception {

        // Given
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        whenNew(SingleJobOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class, String.class)
                .withArguments(sessionStorageMock, "9056")
                .thenReturn(expectedJobOperationsAdapter);

        // When
        SingleJobOperationsAdapter retrieved = serviceSpy.job(9056);

        // Then
        verify(serviceSpy, times(1)).job(9056);
        assertSame(retrieved, expectedJobOperationsAdapter);
    }

    @Test(testName = "scheduleReport_for_v5_6_1")
    public void should_return_proper_OperationResult_when_JRS_version_is_v5_6_1() {

        // Given
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));

        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.put(reportMock)).thenReturn(expectedJobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);

        // When
        serviceSpy.scheduleReport(reportMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).put(reportMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+xml");
        verify(jobRequestMock, times(1)).setAccept("application/job+xml");
    }

    @Test(testName = "scheduleReport_for_v4_7_0")
    public void should_return_proper_OperationResult_when_JRS_version_is_v4_7_0() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.put(reportMock)).thenReturn(expectedJobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));

        // When
        OperationResult<ClientReportJob> retrieved = serviceSpy.scheduleReport(reportMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).put(reportMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+json");
        verify(jobRequestMock, times(1)).setAccept("application/job+json");

        assertNotNull(retrieved);
        assertSame(retrieved, expectedJobOperationResultMock);
    }

    @Test(testName = "allCalendars")
    public void should_return_proper_op_result_object() {

        // Given
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        doReturn(expectedWrapperOperationResultMock).when(serviceSpy).calendar((ClientJobCalendar.Type )null);

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.allCalendars();

        // Then
        verify(serviceSpy, times(1)).allCalendars();
        verify(serviceSpy, times(1)).calendar(((ClientJobCalendar.Type)null));
        verify(serviceSpy, never()).calendar(ClientJobCalendar.Type.holiday);
        verifyNoMoreInteractions(serviceSpy);

        assertSame(retrieved, expectedWrapperOperationResultMock);
    }

    @Test(testName = "asyncCalendar")
    public void should_return_RequestExecution_with_CalendarNameListWrapper_instance() {

        // Given
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        doReturn(executionMock).when(serviceSpy).asyncCalendar(null, callbackMock);

        // When
        RequestExecution retrieved = serviceSpy.asyncCalendar(callbackMock);

        // Then
        verify(serviceSpy, times(1)).asyncCalendar(null, callbackMock);
        verify(serviceSpy, never()).asyncCalendar(ClientJobCalendar.Type.holiday, callbackMock);

        assertSame(retrieved, executionMock);
    }

    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_not_null() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"jobs", "calendars"}))).thenReturn(wrapperRequestMock);
        when(wrapperRequestMock.get()).thenReturn(expectedWrapperOperationResultMock);
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar(ClientJobCalendar.Type.daily);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedWrapperOperationResultMock);

        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"jobs", "calendars"}));

        verify(wrapperRequestMock, times(1)).get();
        verify(wrapperRequestMock, times(1)).addParam("calendarType", CalendarType.daily.toString().toLowerCase());
    }


    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_null() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"jobs", "calendars"}))).thenReturn(wrapperRequestMock);
        when(wrapperRequestMock.get()).thenReturn(expectedWrapperOperationResultMock);
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar((ClientJobCalendar.Type)null);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedWrapperOperationResultMock);

        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"jobs", "calendars"}));

        verify(wrapperRequestMock, times(1)).get();
        verify(wrapperRequestMock, never()).addParam("calendarType", CalendarType.daily.toString().toLowerCase());
    }


    @Test(testName = "calendar")
    public void should_return_an_calendar_adapter() throws Exception {

        // Given
        final String calendarName = "testCalendar";
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        whenNew(SingleCalendarOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class, String.class)
                .withArguments(sessionStorageMock, calendarName)
                .thenReturn(expectedCalendarOperationsAdapterMock);

        // When
        SingleCalendarOperationsAdapter retrieved = serviceSpy.calendar(calendarName);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedCalendarOperationsAdapterMock);
    }

    @Test(testName = "calendar", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_invalid_calendar_name() throws Exception {
        JobsService serviceSpy = spy(new JobsService(sessionStorageMock));
        serviceSpy.calendar("");
    }

    @Test
    public void should_get_calendars_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"jobs", "calendars"}))).thenReturn(wrapperRequestMock);

        PowerMockito.doReturn(expectedWrapperOperationResultMock).when(wrapperRequestMock).get();
        JobsService serviceSpy = PowerMockito.spy(new JobsService(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientCalendarNameListWrapper>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientCalendarNameListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<ClientCalendarNameListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedWrapperOperationResultMock);

        /* When */
        RequestExecution retrieved = serviceSpy.asyncCalendar(ClientJobCalendar.Type.annual, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(wrapperRequestMock).get();
        Mockito.verify(callback).execute(expectedWrapperOperationResultMock);
        Mockito.verify(wrapperRequestMock).addParam("calendarType", "annual");
    }

    @Test
    public void should_send_ScheduleReport_asynchronously_() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);

        PowerMockito.doReturn(expectedJobOperationResultMock).when(jobRequestMock).put(reportMock);
        JobsService serviceSpy = PowerMockito.spy(new JobsService(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientReportJob>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            @Override
            public Void execute(OperationResult<ClientReportJob> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v5_0_0).when(configurationMock).getJrsVersion();
        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);
        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);

        /* When */
        RequestExecution retrieved = serviceSpy.asyncScheduleReport(reportMock, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(jobRequestMock).put(reportMock);
        Mockito.verify(jobRequestMock).setContentType("application/job+json");
        Mockito.verify(jobRequestMock).setAccept("application/job+json");
        Mockito.verify(callback).execute(expectedJobOperationResultMock);
        Mockito.verify(sessionStorageMock).getConfiguration();
        Mockito.verify(configurationMock).getJrsVersion();
    }

    @Test
    public void should_send_ScheduleReport_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientReportJob.class),
                eq(new String[]{"jobs"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);

        PowerMockito.doReturn(expectedJobOperationResultMock).when(jobRequestMock).put(reportMock);
        JobsService serviceSpy = PowerMockito.spy(new JobsService(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientReportJob>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            @Override
            public Void execute(OperationResult<ClientReportJob> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);
        PowerMockito.doReturn(null).when(callback).execute(expectedJobOperationResultMock);

        /* When */
        RequestExecution retrieved = serviceSpy.asyncScheduleReport(reportMock, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(jobRequestMock).put(reportMock);
        Mockito.verify(jobRequestMock).setContentType("application/job+xml");
        Mockito.verify(jobRequestMock).setAccept("application/job+xml");
        Mockito.verify(callback).execute(expectedJobOperationResultMock);
        Mockito.verify(sessionStorageMock, times(3)).getConfiguration();
        Mockito.verify(configurationMock).getJrsVersion();
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, expectedAdapterMock, expectedJobOperationsAdapter,
              jobRequestMock, expectedJobOperationResultMock, reportMock, configurationMock);
    }
}