package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.Map;

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

    private JerseyRequest<Map> request() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                Map.class,
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
        INPUT_CONTROL("inputControls"),
        METADATA("metadata"),
        ADHOC_VIEW("adhocview");

        private String group;

        ServerSettingsGroup(String group) {
            this.group = group;
        }

        public String getGroup() {
            return group;
        }
    }
}
