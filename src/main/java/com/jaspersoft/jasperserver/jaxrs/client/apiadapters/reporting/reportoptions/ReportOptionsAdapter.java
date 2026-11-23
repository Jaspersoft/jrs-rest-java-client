package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummary;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummaryList;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.LinkedList;
import java.util.List;
import jakarta.ws.rs.core.MultivaluedHashMap;

import static java.util.Arrays.asList;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ReportOptionsAdapter extends AbstractAdapter {
    private final String REPORT_SERVICE_URI = "reports";
    private final String OPTIONS_SERVICE_URI = "options";

    private String reportUnitUri;
    private String optionsId;

    private String label;
    private Boolean isOverWrite;

    private MultivaluedHashMap<String, String> options;
    private List<String> path = new LinkedList<>();

    public ReportOptionsAdapter(SessionStorage sessionStorage, String reportUnitUri) {
        super(sessionStorage);
        this.reportUnitUri = reportUnitUri;
        initRequestPath();

    }

    public ReportOptionsAdapter(SessionStorage sessionStorage, String reportUnitUri, String optionsId) {
        super(sessionStorage);
        this.reportUnitUri = reportUnitUri;
        this.optionsId = optionsId;
        initRequestPath();
    }

    public ReportOptionsAdapter(SessionStorage sessionStorage, String reportUnitUri, MultivaluedHashMap<String, String> options) {
        super(sessionStorage);
        this.reportUnitUri = reportUnitUri;
        this.options = options;
        initRequestPath();
    }

    protected void initRequestPath() {
        path.add(REPORT_SERVICE_URI);
        path.addAll(asList(reportUnitUri.split("/")));
        path.add(OPTIONS_SERVICE_URI);
    }

    public ReportOptionsAdapter label(String label) {
        this.label = label;
        return this;
    }

    public ReportOptionsAdapter overwrite(Boolean value) {
        this.isOverWrite = value;
        return this;
    }

    public OperationResult<ReportOptionsSummaryList> get() {
        return JerseyRequest.
                buildRequest(sessionStorage, ReportOptionsSummaryList.class, path.toArray(new String[path.size()])).
                get();
    }

    public OperationResult<ReportOptionsSummary> create() {
        final JerseyRequest<ReportOptionsSummary> request = JerseyRequest.
                buildRequest(sessionStorage, ReportOptionsSummary.class, path.toArray(new String[path.size()]));
        if (label != null && !label.isEmpty()) request.addParam("label", label);
        if (isOverWrite != null) request.addParam("overwrite", isOverWrite.toString());
        return request.post(options);
    }

    public OperationResult<ReportOptionsSummary> update(MultivaluedHashMap<String, String> options) {
        path.add(optionsId);
        final JerseyRequest<ReportOptionsSummary> request = JerseyRequest.
                buildRequest(sessionStorage, ReportOptionsSummary.class, path.toArray(new String[path.size()]));
        return request.put(options);
    }

    public OperationResult<ReportOptionsSummary> update(List<ReportParameter> reportParameters) {
        return update(ReportOptionsUtil.toMap(reportParameters));
    }

    public OperationResult delete() {
        path.add(optionsId);
        return JerseyRequest.
                buildRequest(sessionStorage, Object.class, path.toArray(new String[path.size()])).delete();
    }

}
