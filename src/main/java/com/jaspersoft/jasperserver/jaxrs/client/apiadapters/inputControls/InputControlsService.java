package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsService extends AbstractAdapter {

    public InputControlsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public InputControlsAdapter inputControls() {
        return new InputControlsAdapter (sessionStorage);
    }

}