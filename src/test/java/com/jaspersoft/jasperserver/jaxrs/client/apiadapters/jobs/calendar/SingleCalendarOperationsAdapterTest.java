package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.ReportJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.AnnualCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.BaseCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.CronCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.DailyCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.HolidayCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.MonthlyCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.WeeklyCalendar;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link SingleCalendarOperationsAdapter}
 */
@PrepareForTest({JerseyRequest.class, SingleCalendarOperationsAdapter.class, MultivaluedHashMap.class})
public class SingleCalendarOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ReportJobCalendar> requestMock;

    @Mock
    private JerseyRequest<Object> objRequestMock;

    @Mock
    private OperationResult<ReportJobCalendar> getResultMock;

    @Mock
    private OperationResult<Object> delResultMock;

    @Mock
    private OperationResult<Calendar> operationResultMock;

    @Mock
    private ReportJobCalendar reportJobCalendarMock;

    @Mock
    private Response responseMock;

    @Mock
    private WithEntityOperationResult<Calendar> withEntityOperationResultMock;

    @Mock
    private Calendar calendarEntityMock;

    private MultivaluedHashMap<String, String> paramsSpy;

    @BeforeMethod
    public void before() {
        initMocks(this);
        paramsSpy = spy(new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_pass_proper_params_to_super_class() {

        // When
        SingleCalendarOperationsAdapter calendarOperationsAdapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName");

        // Than
        assertSame(calendarOperationsAdapter.getSessionStorage(), sessionStorageMock);
        Object calendarName = Whitebox.getInternalState(calendarOperationsAdapter, "calendarName");
        Object params = Whitebox.getInternalState(calendarOperationsAdapter, "params");

        assertNotNull(params);
        assertSame(calendarName, "testCalendarName");

        assertTrue(instanceOf(String.class).matches(calendarName));
        assertTrue(instanceOf(MultivaluedHashMap.class).matches(params));


    }

    @Test
    public void parameter() throws Exception {

        // Given
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(paramsSpy);
        SingleCalendarOperationsAdapter adapterSpy =
                Mockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        SingleCalendarOperationsAdapter retrieved = adapterSpy.parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");

        // Than
        verify(adapterSpy, times(1)).parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");
        verify(paramsSpy, times(1)).add(CalendarParameter.UPDATE_TRIGGERS.getName(), "testValue");
        verify(paramsSpy, never()).getFirst(anyString());

        Assert.assertSame(retrieved, adapterSpy);
        Assert.assertTrue(paramsSpy.size() == 1);
    }

    @Test
    public void get() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class),
                eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        doReturn(getResultMock).when(requestMock).get();
        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock,
                "testCalendarName"));
        doReturn(operationResultMock).when(adapterSpy, "convertToLocalCalendarType", getResultMock);

        // When
        OperationResult<Calendar> retrieved = adapterSpy.get();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class),
                eq(new String[]{"/jobs", "/calendars", "testCalendarName"}));
        verifyPrivate(adapterSpy, times(1)).invoke("convertToLocalCalendarType", getResultMock);
        assertSame(retrieved, operationResultMock);
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType1() throws Exception {

        // Given
        final Calendar expected = new BaseCalendar();
        expected.setCalendarType(CalendarType.base);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("base").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult<Calendar> retrieved = adapterSpy.get();

        // Than
        assertEquals(retrieved.getEntity(), expected);
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new BaseCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType2() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(AnnualCalendar.class);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.annual);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(sessionStorageMock, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", "testCalendarName"})).thenReturn(requestMock);
        PowerMockito.whenNew(AnnualCalendar.class).withNoArguments().thenReturn((AnnualCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("annual").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult<Calendar> retrieved = adapterSpy.get();

        // Than
        assertEquals(retrieved.getEntity(), expected);
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", expected, reportJobCalendarMock);
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }


    @Test(suiteName = "private")
    public void convertToLocalCalendarType3() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(CronCalendar.class);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.cron);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.whenNew(CronCalendar.class).withNoArguments().thenReturn((CronCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("cron").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult<Calendar> retrieved = adapterSpy.get();

        // Than
        assertEquals(retrieved.getEntity(), expected);
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new CronCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType4() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(DailyCalendar.class);
        Whitebox.setInternalState(expected, "invertTimeRange", false);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.daily);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.whenNew(DailyCalendar.class).withNoArguments().thenReturn((DailyCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("daily").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        adapterSpy.get();

        // Than
        //assertEquals(retrieved.getEntity(), expected);
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new DailyCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType5() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(HolidayCalendar.class);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.holiday);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.whenNew(HolidayCalendar.class).withNoArguments().thenReturn((HolidayCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("holiday").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        adapterSpy.get();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new HolidayCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType6() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(MonthlyCalendar.class);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.monthly);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.whenNew(MonthlyCalendar.class).withNoArguments().thenReturn((MonthlyCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("monthly").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        adapterSpy.get();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new MonthlyCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test(suiteName = "private")
    public void convertToLocalCalendarType7() throws Exception {

        // Given
        final Calendar expected = PowerMockito.mock(WeeklyCalendar.class);
        Whitebox.setInternalState(expected, "calendarType", CalendarType.weekly);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.whenNew(WeeklyCalendar.class).withNoArguments().thenReturn((WeeklyCalendar) expected);
        PowerMockito.doReturn(getResultMock).when(requestMock).get();
        PowerMockito.doReturn(responseMock).when(getResultMock).getResponse();
        PowerMockito.doReturn(reportJobCalendarMock).when(getResultMock).getEntity();
        PowerMockito.doReturn("weekly").when(reportJobCalendarMock).getCalendarType();

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        adapterSpy.get();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("setCommonCalendarFields", eq(new WeeklyCalendar()), eq(reportJobCalendarMock));
        verify(getResultMock, times(1)).getEntity();
        verify(reportJobCalendarMock, times(2)).getCalendarType();
    }

    @Test
    public void delete() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(objRequestMock);
        doReturn(delResultMock).when(objRequestMock).delete();
        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}));
        assertSame(retrieved, delResultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createNew() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.when(requestMock.put(calendarEntityMock)).thenReturn(getResultMock);
        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult<ReportJobCalendar> retrieved = adapterSpy.createNew(calendarEntityMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ReportJobCalendar.class), eq(new String[]{"/jobs", "/calendars", "testCalendarName"}));
        assertSame(retrieved, getResultMock);

        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
        Mockito.verify(requestMock).addParams(retrievedParams);
        Mockito.verify(requestMock).put(calendarEntityMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, objRequestMock, getResultMock, delResultMock,
                operationResultMock, reportJobCalendarMock, responseMock, withEntityOperationResultMock,
                calendarEntityMock);
    }
}