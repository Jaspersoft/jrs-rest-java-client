package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.3-ALPHA
 */
public class SettingsService extends AbstractAdapter {

    public SettingsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleSettingsAdapter settings(){
        return new SingleSettingsAdapter(sessionStorage);
    }
}
