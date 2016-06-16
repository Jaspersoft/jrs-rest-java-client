package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;

/**
 * <p/>
 * <p/>
 * ``
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleDiagnosticCollectorAdapter extends AbstractAdapter {

    private CollectorSettings collector;

    public SingleDiagnosticCollectorAdapter(SessionStorage sessionStorage, CollectorSettings collector) {
        super(sessionStorage);
        this.collector = collector;
    }

    public OperationResult<CollectorSettings> create() {
        return JerseyRequest.buildRequest(sessionStorage,
                CollectorSettings.class,
                new String[]{"diagnostic", "collectors"},
                new DefaultErrorHandler()).post(collector);
    }

    public OperationResult<CollectorSettings> delete() {
        return buildCollectorRequest()
                .delete();
    }


    public OperationResult<CollectorSettings> collectorSettings() {
        return buildCollectorRequest().get();
    }

    /**
     * This method can be used to stop the
     * collector by setting "status" to "STOPPED" in the request.
     * Stopping the collector will turn off logging and begin resource export
     * (if "includeDataSnapshots"=true and resourceUri not empty).
     */
    public OperationResult<CollectorSettings> updateCollectorSettings(CollectorSettings newData) {
        return buildCollectorRequest().put(newData);
    }

    public OperationResult<CollectorSettings> updateCollectorSettings(PatchDescriptor newData) {
        return buildCollectorRequest()
                .addHeader("X-HTTP-Method-Override", "PATCH")
                .post(newData);
    }

    public OperationResult<InputStream> collectorContent() {
        JerseyRequest<InputStream> request = JerseyRequest.buildRequest(sessionStorage,
                InputStream.class,
                new String[]{"diagnostic", "collectors", collector.getId(), "content"},
                new DefaultErrorHandler());
        request.setAccept("application/zip");
        return request.get();
    }

    protected JerseyRequest<CollectorSettings> buildCollectorRequest() {
        return JerseyRequest.buildRequest(sessionStorage,
                CollectorSettings.class,
                new String[]{"diagnostic", "collectors", collector.getId()},
                new DefaultErrorHandler());
    }

}
