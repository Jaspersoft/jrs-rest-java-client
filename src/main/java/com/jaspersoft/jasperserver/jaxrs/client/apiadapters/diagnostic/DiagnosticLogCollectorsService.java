package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DiagnosticLogCollectorsService extends AbstractAdapter {

    public DiagnosticLogCollectorsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleDiagnosticClollectorAdapter forCollector(CollectorSettings collector) {
        return new SingleDiagnosticClollectorAdapter(sessionStorage, collector);
    }


    public BatchDiagnosticCollectorsAdapter allCollectors() {
        return new BatchDiagnosticCollectorsAdapter(sessionStorage);
    }

}
