package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  @author Tetiana Iefimenko
 */
public class DateTimeSettings {

    private  Datepicker datepicker;
    private Timepicker timepicker;

    public DateTimeSettings() {
    }

    public DateTimeSettings(DateTimeSettings other) {
        this.datepicker = other.datepicker;
        this.timepicker = other.timepicker;
    }

    public Datepicker getDatepicker() {
        return datepicker;

    }

    public DateTimeSettings setDatepicker(Datepicker datepicker) {
        this.datepicker = datepicker;
        return this;
    }

    public Timepicker getTimepicker() {
        return timepicker;
    }

    public DateTimeSettings setTimepicker(Timepicker timepicker) {
        this.timepicker = timepicker;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeSettings)) return false;

        DateTimeSettings that = (DateTimeSettings) o;

        if (getDatepicker() != null ? !getDatepicker().equals(that.getDatepicker()) : that.getDatepicker() != null)
            return false;
        return !(getTimepicker() != null ? !getTimepicker().equals(that.getTimepicker()) : that.getTimepicker() != null);

    }

    @Override
    public int hashCode() {
        int result = getDatepicker() != null ? getDatepicker().hashCode() : 0;
        result = 31 * result + (getTimepicker() != null ? getTimepicker().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DateTimeSettings{" +
                "datepicker=" + datepicker +
                ", timepicker=" + timepicker +
                '}';
    }
}
