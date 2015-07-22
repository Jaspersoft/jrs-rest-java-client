package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by tetiana.iefimenko on 7/22/2015.
 */
@XmlRootElement
public class DateTimeSettings {

    private  DatePicker datePicker;
    private TimePicker timePicker;

    public DateTimeSettings() {
    }

    public DateTimeSettings(DateTimeSettings other) {
        this.datePicker = other.datePicker;
        this.timePicker = other.timePicker;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public DateTimeSettings setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
        return this;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public DateTimeSettings setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeSettings)) return false;

        DateTimeSettings that = (DateTimeSettings) o;

        if (getDatePicker() != null ? !getDatePicker().equals(that.getDatePicker()) : that.getDatePicker() != null)
            return false;
        return !(getTimePicker() != null ? !getTimePicker().equals(that.getTimePicker()) : that.getTimePicker() != null);

    }

    @Override
    public int hashCode() {
        int result = getDatePicker() != null ? getDatePicker().hashCode() : 0;
        result = 31 * result + (getTimePicker() != null ? getTimePicker().hashCode() : 0);
        return result;
    }

//    @Override
//    public String toString() {
//        return "DateTimeSettings{" +
//                "datePicker=" + datePicker +
//                ", timePicker=" + timePicker +
//                '}';
//    }
}
