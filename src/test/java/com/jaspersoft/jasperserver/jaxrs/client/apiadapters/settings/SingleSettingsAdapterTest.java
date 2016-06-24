package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.AwsSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.DashboardSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.DataSourcePatternsSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.DateTimeSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.DecimalFormatSymbolsSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.GlobalConfigurationSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.InputControlsSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.RequestSettings;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.UserTimeZone;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class})
public class SingleSettingsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<Map> mapJerseyRequest;
    @Mock
    private JerseyRequest<List> listJerseyRequest;
    @Mock
    private JerseyRequest<AwsSettings> awsSettingsJerseyRequest;
    @Mock
    private JerseyRequest<RequestSettings> requestSettingsJerseyRequest;
    @Mock
    private JerseyRequest<DataSourcePatternsSettings> dataSoursePattermsSettingsJerseyRequest;
    @Mock
    private JerseyRequest<DecimalFormatSymbolsSettings> decimalFormatSymbolsSettingsJerseyRequest;
    @Mock
    private JerseyRequest<DashboardSettings> dashboardSettingsJerseyRequest;
    @Mock
    private JerseyRequest<GlobalConfigurationSettings> globalConfigurationSettingsJerseyRequest;
    @Mock
    private JerseyRequest<DateTimeSettings> dateTimeSettingsJerseyRequest;
    @Mock
    private JerseyRequest<InputControlsSettings> inputControlsSettingsJerseyRequest;
    @Mock
    private JerseyRequest<List<UserTimeZone>> userTimeZonesListJerseyRequest;
    @Mock
    private OperationResult<Map> mapOperationResult;
    @Mock
    private OperationResult<List> listOperationResult;
    @Mock
    private OperationResult<AwsSettings> awsSettingsOperationResult;
    @Mock
    private OperationResult<RequestSettings> requestSettingsOperationResult;
    @Mock
    private OperationResult<DataSourcePatternsSettings> dataSourcePatternsSettingsOperationResult;
    @Mock
    private OperationResult<DecimalFormatSymbolsSettings> decimalFormatSymbolsSettingsOperationResult;
    @Mock
    private OperationResult<DashboardSettings> dashboardSettingsOperationResult;
    @Mock
    private OperationResult<GlobalConfigurationSettings> globalConfigurationSettingsOperationResult;
    @Mock
    private OperationResult<DateTimeSettings> dateTimeSettingsOperationResult;
    @Mock
    private OperationResult<InputControlsSettings> inputControlsSettingsOperationResult;
    @Mock
    private OperationResult<List<UserTimeZone>> userTimeZonesListOperationResult;

    private SettingsService service;

    @BeforeMethod
    public void before() {
        initMocks(this);
        service = new SettingsService(sessionStorageMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock,
                mapJerseyRequest,
                listJerseyRequest,
                awsSettingsJerseyRequest,
                requestSettingsJerseyRequest,
                dataSoursePattermsSettingsJerseyRequest,
                decimalFormatSymbolsSettingsJerseyRequest,
                dashboardSettingsJerseyRequest,
                globalConfigurationSettingsJerseyRequest,
                dateTimeSettingsJerseyRequest,
                inputControlsSettingsJerseyRequest,
                userTimeZonesListJerseyRequest,
                mapOperationResult,
                listOperationResult,
                awsSettingsOperationResult,
                requestSettingsOperationResult,
                dataSourcePatternsSettingsOperationResult,
                decimalFormatSymbolsSettingsOperationResult,
                dashboardSettingsOperationResult,
                globalConfigurationSettingsOperationResult,
                dateTimeSettingsOperationResult,
                inputControlsSettingsOperationResult,
                userTimeZonesListOperationResult);
    }

    @Test
    public void should_return_map_of_aws_settings_operationResult() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(Map.class),
                eq(new String[]{"settings", "awsSettings"}),
                any(DefaultErrorHandler.class))).thenReturn(mapJerseyRequest);
        PowerMockito.doReturn(mapOperationResult).when(mapJerseyRequest).get();

        OperationResult<Map> settings = service.settings().group("awsSettings", Map.class);
        //then
        assertSame(settings, mapOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(Map.class),
                eq(new String[]{"settings", "awsSettings"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_awsSettings_operationResult() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(AwsSettings.class),
                eq(new String[]{"settings", "awsSettings"}), any(DefaultErrorHandler.class))).thenReturn(awsSettingsJerseyRequest);
        PowerMockito.doReturn(awsSettingsOperationResult).when(awsSettingsJerseyRequest).get();

        OperationResult<AwsSettings> settings = service.settings().group("awsSettings", AwsSettings.class);
        //then

        assertSame(settings, awsSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(AwsSettings.class),
                eq(new String[]{"settings", "awsSettings"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_list_operationResult() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(List.class),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class))).thenReturn(listJerseyRequest);
        PowerMockito.doReturn(listOperationResult).when(listJerseyRequest).get();

        OperationResult<List> settings = service.settings().group("userTimeZones", List.class);

        //then
        assertSame(settings, listOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(List.class),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_list_of_user_time_zones__operationResult() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(new GenericType<List<UserTimeZone>>() {
                }),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class))).thenReturn(userTimeZonesListJerseyRequest);
        PowerMockito.doReturn(userTimeZonesListOperationResult).when(userTimeZonesListJerseyRequest).get();

        OperationResult<List<UserTimeZone>> settings = service.settings().group("userTimeZones", new GenericType<List<UserTimeZone>>() {
        });

        //then
        assertSame(settings, userTimeZonesListOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(new GenericType<List<UserTimeZone>>() {
                }),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_request_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(RequestSettings.class),
                eq(new String[]{"settings", "request"}),
                any(DefaultErrorHandler.class))).thenReturn(requestSettingsJerseyRequest);
        PowerMockito.doReturn(requestSettingsOperationResult).when(requestSettingsJerseyRequest).get();

        OperationResult<RequestSettings> settings = service.settings().ofRequestGroup();

        //then
        assertSame(settings, requestSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(RequestSettings.class),
                eq(new String[]{"settings", "request"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_dataSourcePatterns_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DataSourcePatternsSettings.class),
                eq(new String[]{"settings", "dataSourcePatterns"}),
                any(DefaultErrorHandler.class))).thenReturn(dataSoursePattermsSettingsJerseyRequest);
        PowerMockito.doReturn(dataSourcePatternsSettingsOperationResult).when(dataSoursePattermsSettingsJerseyRequest).get();

        OperationResult<DataSourcePatternsSettings> settings = service.settings().ofDataSourcePatternsGroup();

        //then
        assertSame(settings, dataSourcePatternsSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DataSourcePatternsSettings.class),
                eq(new String[]{"settings", "dataSourcePatterns"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_userTimeZones_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(new GenericType<List<UserTimeZone>>() {
                }),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class))).thenReturn(userTimeZonesListJerseyRequest);
        PowerMockito.doReturn(userTimeZonesListOperationResult).when(userTimeZonesListJerseyRequest).get();

        OperationResult<List<UserTimeZone>> settings = service.settings().ofUserTimeZonesGroup();

        //then
        assertSame(settings, userTimeZonesListOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(new GenericType<List<UserTimeZone>>() {
                }),
                eq(new String[]{"settings", "userTimeZones"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_aws_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(AwsSettings.class),
                eq(new String[]{"settings", "awsSettings"}),
                any(DefaultErrorHandler.class))).thenReturn(awsSettingsJerseyRequest);
        PowerMockito.doReturn(awsSettingsOperationResult).when(awsSettingsJerseyRequest).get();

        OperationResult<AwsSettings> settings = service.settings().ofAwsGroup();

        //then
        assertSame(settings, awsSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(AwsSettings.class),
                eq(new String[]{"settings", "awsSettings"}),
                any(DefaultErrorHandler.class));
    }


    @Test
    public void should_return_decimalFormatSymbol_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DecimalFormatSymbolsSettings.class),
                eq(new String[]{"settings", "decimalFormatSymbols"}),
                any(DefaultErrorHandler.class))).thenReturn(decimalFormatSymbolsSettingsJerseyRequest);
        PowerMockito.doReturn(decimalFormatSymbolsSettingsOperationResult).when(decimalFormatSymbolsSettingsJerseyRequest).get();

        OperationResult<DecimalFormatSymbolsSettings> settings = service.settings().ofDecimalFormatSymbolsGroup();

        //then
        assertSame(settings, decimalFormatSymbolsSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DecimalFormatSymbolsSettings.class),
                eq(new String[]{"settings", "decimalFormatSymbols"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_dashboard_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DashboardSettings.class),
                eq(new String[]{"settings", "dashboardSettings"}),
                any(DefaultErrorHandler.class))).thenReturn(dashboardSettingsJerseyRequest);
        PowerMockito.doReturn(dashboardSettingsOperationResult).when(dashboardSettingsJerseyRequest).get();

        OperationResult<DashboardSettings> settings = service.settings().ofDashboardGroup();

        //then
        assertSame(settings, dashboardSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DashboardSettings.class),
                eq(new String[]{"settings", "dashboardSettings"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_globalConfiguration_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(GlobalConfigurationSettings.class),
                eq(new String[]{"settings", "globalConfiguration"}),
                any(DefaultErrorHandler.class))).thenReturn(globalConfigurationSettingsJerseyRequest);
        PowerMockito.doReturn(globalConfigurationSettingsOperationResult).when(globalConfigurationSettingsJerseyRequest).get();

        OperationResult<GlobalConfigurationSettings> settings = service.settings().ofGlobalConfigurationGroup();

        //then
        assertSame(settings, globalConfigurationSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(GlobalConfigurationSettings.class),
                eq(new String[]{"settings", "globalConfiguration"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_dateTime_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DateTimeSettings.class),
                eq(new String[]{"settings", "dateTimeSettings"}),
                any(DefaultErrorHandler.class))).thenReturn(dateTimeSettingsJerseyRequest);
        PowerMockito.doReturn(dateTimeSettingsOperationResult).when(dateTimeSettingsJerseyRequest).get();

        OperationResult<DateTimeSettings> settings = service.settings().ofDateTimeGroup();

        //then
        assertSame(settings, dateTimeSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(DateTimeSettings.class),
                eq(new String[]{"settings", "dateTimeSettings"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_inputControls_settings_dto__operationResult_by_specified_method() throws Exception {
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(InputControlsSettings.class),
                eq(new String[]{"settings", "inputControls"}),
                any(DefaultErrorHandler.class))).thenReturn(inputControlsSettingsJerseyRequest);
        PowerMockito.doReturn(inputControlsSettingsOperationResult).when(inputControlsSettingsJerseyRequest).get();

        OperationResult<InputControlsSettings> settings = service.settings().ofInputControlsGroup();

        //then
        assertSame(settings, inputControlsSettingsOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(InputControlsSettings.class),
                eq(new String[]{"settings", "inputControls"}),
                any(DefaultErrorHandler.class));
    }

}