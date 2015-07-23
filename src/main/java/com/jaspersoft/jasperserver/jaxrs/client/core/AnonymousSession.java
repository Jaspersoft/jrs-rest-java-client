package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SettingsService;

/**
 * Created by tetiana.iefimenko on 7/23/2015.
 */
public class AnonymousSession {

    protected SessionStorage storage;

    protected AnonymousSession() {
    }

    public SessionStorage getStorage() {
        return storage;
    }

    public AnonymousSession(SessionStorage storage) {
        this.storage = storage;
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

    public SettingsService settingsService() {
        return getService(SettingsService.class);
    }
}
