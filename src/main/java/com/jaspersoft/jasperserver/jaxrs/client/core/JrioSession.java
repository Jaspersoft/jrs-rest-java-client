/*
 * Copyright © 2014-2018. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;

/**
 * @author Tatyana Matveyeva
 */
public class JrioSession {

    protected SessionStorage storage;

    public JrioSession(SessionStorage storage) {
        this.storage = storage;
    }

    public SessionStorage getStorage() {
        return storage;
    }

    protected  <ServiceType extends AbstractAdapter> ServiceType getService(Class<ServiceType> serviceClass) {
        try {
            return serviceClass.getConstructor(SessionStorage.class).newInstance(storage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ServerInfoService serverInfoService() {
        return getService(ServerInfoService.class);
    }

    public ReportingService reportingService() {
        return getService(ReportingService.class);
    }

}
