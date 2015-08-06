package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles.BundlesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SettingsService;

/**
 * @author Tetiana Iefimenko
 */
public class AnonymousSession {

    protected SessionStorage storage;

    public AnonymousSession(SessionStorage storage) {
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

    public SettingsService settingsService() {
        return getService(SettingsService.class);
    }

    public BundlesService bundlesService() {return  getService(BundlesService.class);}
}
