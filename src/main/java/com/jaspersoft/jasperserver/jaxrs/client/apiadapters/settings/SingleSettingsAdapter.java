package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.*;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SingleSettingsAdapter.ServerSettingsGroup.REQUEST;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.3-ALPHA
 */
public class SingleSettingsAdapter extends AbstractAdapter {

    private String groupKey;

    public SingleSettingsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<Map> group(String groupKey) {
        this.groupKey = groupKey;
        return request().get();
    }

    public OperationResult<Map> group(ServerSettingsGroup group) {
        this.groupKey = group.getGroup();

        return request().get();
    }

    public <T> OperationResult<T> group(ServerSettingsGroup group, Class<T> resultClass) {
        this.groupKey = group.getGroup();
        return request(resultClass).get();
    }

    public <T> OperationResult<T> group(ServerSettingsGroup group, GenericType<T> genericType) {
        this.groupKey = group.getGroup();
        return request(genericType).get();
    }
// new shortCut methods

    public OperationResult<RequestSettings> ofRequestGroupSettings() {
        this.groupKey = "request";
        return request(RequestSettings.class).get();
    }

    public OperationResult<DataSourcePatternsSettings> ofDataSourcePatternsGroupSettings() {
        this.groupKey = "dataSourcePatterns";
        return request(DataSourcePatternsSettings.class).get();
    }

    public OperationResult<List<UserTimeZone>> ofUserTimeZonesGroupSettings() {
        this.groupKey = "userTimeZones";
        return request(new GenericType<List<UserTimeZone>>(){}).get();
    }

    public OperationResult<AwsSettings> ofAwsGroupSettings() {
        this.groupKey = "awsSettings";
        return request(AwsSettings.class).get();
    }

    public OperationResult<DecimalFormatSymbolsSettings> ofDecimalFormatSymbolsGroupSettings() {
        this.groupKey = "decimalFormatSymbols";
        return request(DecimalFormatSymbolsSettings.class).get();
    }

    public OperationResult<DashboardSettings> ofDashboardGroupSettings() {
        this.groupKey = "dashboardSettings";
        return request(DashboardSettings.class).get();
    }

    public OperationResult<GlobalConfigurationSettings> ofGlobalConfigurationGroupSettings() {
        this.groupKey = "globalConfiguration";
        return request(GlobalConfigurationSettings.class).get();
    }

    public OperationResult<DateTimeSettings> ofDateTimeGroupSettings() {
        this.groupKey = "dateTimeSettings";
        return request(DateTimeSettings.class).get();
    }

    public OperationResult<InputControlsSettings> ofInputControlsGroupSettings() {
        this.groupKey = "inputControls";
        return request(InputControlsSettings.class).get();
    }

    private JerseyRequest<Map> request() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                Map.class,
                new String[]{"/settings/" + groupKey},
                new DefaultErrorHandler());
    }

    private  <T> JerseyRequest<T> request(Class<T> resultClass) {
        return JerseyRequest.buildRequest(
                sessionStorage,
                resultClass,
                new String[]{"/settings/" + groupKey},
                new DefaultErrorHandler());
    }

    private  <T> JerseyRequest<T> request(GenericType<T> genericType) {
        return JerseyRequest.buildRequest(
                sessionStorage,
                genericType,
                new String[]{"/settings/" + groupKey},
                new DefaultErrorHandler());
    }

    public enum ServerSettingsGroup {

        REQUEST("request"),
        DATA_SOURCE_PATTERNS("dataSourcePatterns"),
        USER_TIME_ZONES("userTimeZones"),
        GLOBAL_CONFIGURATION("globalConfiguration"),
        AWS_SETTINGS("awsSettings"),
        DECIMAL_FORMAT_SYMBOLS("decimalFormatSymbols"),
        DATE_TIME_SETTINGS("dateTimeSettings"),
        DASHBOARD_SETTINGS("dashboardSettings"),
        INPUT_CONTROL("inputControls");
//        proposal settings
//        METADATA("metadata"),
//        ADHOC_VIEW("adhocview");

        private String group;
//        private Class<T> resultClass;
//        private GenericType<T> resultGenericType;

        ServerSettingsGroup(String group) {
            this.group = group;
        }

        public String getGroup() {
            return group;
        }

    }
}
