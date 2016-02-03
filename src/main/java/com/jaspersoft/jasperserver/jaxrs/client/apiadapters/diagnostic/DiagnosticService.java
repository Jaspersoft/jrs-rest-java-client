package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DiagnosticService extends AbstractAdapter {

    public DiagnosticService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleDiagnosticCollectorAdapter forCollector(CollectorSettings collector) {
        if (collector == null) {
            throw new MandatoryParameterNotFoundException("Collector must not be null");
        }
        return new SingleDiagnosticCollectorAdapter(sessionStorage, collector);
    }

    public SingleDiagnosticCollectorAdapter forCollector(String collectorId) {
        if (collectorId == null || "".equals(collectorId)) {
            throw new MandatoryParameterNotFoundException("Collector's ID is not valid");
        }
        CollectorSettings collectorSettings = new CollectorSettings();
        collectorSettings.setId(collectorId);
        return this.forCollector(collectorSettings);
    }


    public BatchDiagnosticCollectorsAdapter allCollectors() {
        return new BatchDiagnosticCollectorsAdapter(sessionStorage);
    }

}
