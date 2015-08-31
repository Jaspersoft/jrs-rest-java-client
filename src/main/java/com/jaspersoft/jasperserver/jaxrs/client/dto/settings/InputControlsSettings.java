package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 *  @author Tetiana Iefimenko
 */
public class InputControlsSettings {

    private Boolean useUrlParametersOnReset;

    public InputControlsSettings() {
    }

    public InputControlsSettings(InputControlsSettings other) {
        this.useUrlParametersOnReset = other.useUrlParametersOnReset;
    }

    public Boolean getUseUrlParametersOnReset() {
        return useUrlParametersOnReset;
    }

    public InputControlsSettings setUseUrlParametersOnReset(Boolean useUrlParametersOnReset) {
        this.useUrlParametersOnReset = useUrlParametersOnReset;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InputControlsSettings)) return false;

        InputControlsSettings that = (InputControlsSettings) o;

        return !(getUseUrlParametersOnReset() != null ? !getUseUrlParametersOnReset().equals(that.getUseUrlParametersOnReset()) : that.getUseUrlParametersOnReset() != null);

    }

    @Override
    public int hashCode() {
        return getUseUrlParametersOnReset() != null ? getUseUrlParametersOnReset().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "InputControlsSettings{" +
                "useUrlParametersOnReset=" + useUrlParametersOnReset +
                '}';
    }
}
