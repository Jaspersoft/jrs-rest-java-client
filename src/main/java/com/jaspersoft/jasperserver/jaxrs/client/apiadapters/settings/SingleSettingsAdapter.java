package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.*;

import jakarta.ws.rs.core.GenericType;
import java.util.List;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.3-ALPHA
 */
public class SingleSettingsAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "settings";
    private String groupKey;

    public SingleSettingsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public <T> OperationResult<T> group(String group, Class<T> resultClass) {
        this.groupKey = group;
        return request(resultClass).get();
    }

    public <T> OperationResult<T> group(String group, GenericType<T> genericType) {
        this.groupKey = group;
        return request(genericType).get();
    }

    public OperationResult<RequestSettings> ofRequestGroup() {
        this.groupKey = "request";
        return request(RequestSettings.class).get();
    }

    public OperationResult<DataSourcePatternsSettings> ofDataSourcePatternsGroup() {
        this.groupKey = "dataSourcePatterns";
        return request(DataSourcePatternsSettings.class).get();
    }

    public OperationResult<List<UserTimeZone>> ofUserTimeZonesGroup() {
        this.groupKey = "userTimeZones";
        return request(new GenericType<List<UserTimeZone>>(){}).get();
    }

    public OperationResult<AwsSettings> ofAwsGroup() {
        this.groupKey = "awsSettings";
        return request(AwsSettings.class).get();
    }

    public OperationResult<DecimalFormatSymbolsSettings> ofDecimalFormatSymbolsGroup() {
        this.groupKey = "decimalFormatSymbols";
        return request(DecimalFormatSymbolsSettings.class).get();
    }

    public OperationResult<DashboardSettings> ofDashboardGroup() {
        this.groupKey = "dashboardSettings";
        return request(DashboardSettings.class).get();
    }

    public OperationResult<GlobalConfigurationSettings> ofGlobalConfigurationGroup() {
        this.groupKey = "globalConfiguration";
        return request(GlobalConfigurationSettings.class).get();
    }

    public OperationResult<DateTimeSettings> ofDateTimeGroup() {
        this.groupKey = "dateTimeSettings";
        return request(DateTimeSettings.class).get();
    }

    public OperationResult<InputControlsSettings> ofInputControlsGroup() {
        this.groupKey = "inputControls";
        return request(InputControlsSettings.class).get();
    }

    private <T> JerseyRequest<T> request(Class<T> resultClass) {
        return JerseyRequest.buildRequest(
                sessionStorage,
                resultClass,
                new String[]{SERVICE_URI, groupKey},
                new DefaultErrorHandler());
    }

    private <T> JerseyRequest<T> request(GenericType<T> genericType) {
        return JerseyRequest.buildRequest(
                sessionStorage,
                genericType,
                new String[]{SERVICE_URI, groupKey},
                new DefaultErrorHandler());
    }
}
