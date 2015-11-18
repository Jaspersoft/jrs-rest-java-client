package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsAdapter extends AbstractAdapter{

    private String reportUri;

    public InputControlsAdapter container(String uri) {
        this.reportUri = uri;
        return  this;
    }

    public InputControlsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public InputControlsValuesAdapter values() {
        return new InputControlsValuesAdapter(sessionStorage);
    }
}
