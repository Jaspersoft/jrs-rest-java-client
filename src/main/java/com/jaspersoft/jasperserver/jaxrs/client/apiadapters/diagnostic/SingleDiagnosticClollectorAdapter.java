package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettingsList;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;

/**
 * <p/>
 * <p/>
 *``
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleDiagnosticClollectorAdapter extends AbstractAdapter {

    private CollectorSettings collector;

    public SingleDiagnosticClollectorAdapter(SessionStorage sessionStorage, CollectorSettings collector) {
        super(sessionStorage);
        this.collector = this.collector;
    }

    public OperationResult<CollectorSettings> create() {

        return JerseyRequest.buildRequest(sessionStorage,
                CollectorSettings.class,
                new String[]{"/collectors"},
                new DefaultErrorHandler())
                .post(collector);
    }


    public OperationResult<CollectorSettingsList> collectorMetadata() {
        return JerseyRequest.buildRequest(sessionStorage,
                CollectorSettingsList.class,
                new String[]{"/collectors", collector.getId()},
                new DefaultErrorHandler())
                .get();
    }

    public OperationResult<InputStream> collectorContent() {
        JerseyRequest<InputStream> request = JerseyRequest.buildRequest(sessionStorage,
                InputStream.class,
                new String[]{"/collectors", "content", collector.getId()},
                new DefaultErrorHandler());
        request.setAccept("application/zip");
        return request.get();
    }
}
