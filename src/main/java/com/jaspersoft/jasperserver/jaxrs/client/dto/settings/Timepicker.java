package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tetiana Iefimenko
 */
public class Timepicker {
    private String timeOnlyTitle;
    private String timeText;
    private String hourText;
    private String minuteText;
    private String secondText;
    private String millisecText;
    private String microsecText;
    private String timezoneText;
    private String currentText;
    private String closeText;
    private String timeFormat;
    private List<String> amNames;
    private List<String> pmNames;
    private Boolean isRTL;
    private String dateFormat;
    private String separator;

    public Timepicker() {
    }

    public Timepicker(Timepicker other) {
        this.timeOnlyTitle = other.timeOnlyTitle;
        this.timeText = other.timeText;
        this.hourText = other.hourText;
        this.minuteText = other.minuteText;
        this.secondText = other.secondText;
        this.millisecText = other.millisecText;
        this.microsecText = other.microsecText;
        this.timezoneText = other.timezoneText;
        this.currentText = other.currentText;
        this.closeText = other.closeText;
        this.timeFormat = other.timeFormat;
        this.amNames = (other.amNames != null) ? new LinkedList<String>(other.amNames) : null;
        this.pmNames = (other.pmNames != null) ? new LinkedList<String>(other.pmNames) : null;
        this.isRTL = other.isRTL;
        this.dateFormat = other.dateFormat;
        this.separator = other.separator;
    }

    public String getTimeOnlyTitle() {
        return timeOnlyTitle;
    }

    public Timepicker setTimeOnlyTitle(String timeOnlyTitle) {
        this.timeOnlyTitle = timeOnlyTitle;
        return this;
    }

    public String getTimeText() {
        return timeText;
    }

    public Timepicker setTimeText(String timeText) {
        this.timeText = timeText;
        return this;
    }

    public String getHourText() {
        return hourText;
    }

    public Timepicker setHourText(String hourText) {
        this.hourText = hourText;
        return this;
    }

    public String getMinuteText() {
        return minuteText;
    }

    public Timepicker setMinuteText(String minuteText) {
        this.minuteText = minuteText;
        return this;
    }

    public String getSecondText() {
        return secondText;
    }

    public Timepicker setSecondText(String secondText) {
        this.secondText = secondText;
        return this;
    }

    public String getMillisecText() {
        return millisecText;
    }

    public Timepicker setMillisecText(String millisecText) {
        this.millisecText = millisecText;
        return this;
    }

    public String getMicrosecText() {
        return microsecText;
    }

    public Timepicker setMicrosecText(String microsecText) {
        this.microsecText = microsecText;
        return this;
    }

    public String getTimezoneText() {
        return timezoneText;
    }

    public Timepicker setTimezoneText(String timezoneText) {
        this.timezoneText = timezoneText;
        return this;
    }

    public String getCurrentText() {
        return currentText;
    }

    public Timepicker setCurrentText(String currentText) {
        this.currentText = currentText;
        return this;
    }

    public String getCloseText() {
        return closeText;
    }

    public Timepicker setCloseText(String closeText) {
        this.closeText = closeText;
        return this;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public Timepicker setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        return this;
    }

    public List<String> getAmNames() {
        return amNames;
    }

    public Timepicker setAmNames(List<String> amNames) {
        this.amNames = amNames;
        return this;
    }

    public List<String> getPmNames() {
        return pmNames;
    }

    public Timepicker setPmNames(List<String> pmNames) {
        this.pmNames = pmNames;
        return this;
    }

    public Boolean getIsRTL() {
        return isRTL;
    }

    public Timepicker setIsRTL(Boolean isRTL) {
        this.isRTL = isRTL;
        return this;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Timepicker setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public String getSeparator() {
        return separator;
    }

    public Timepicker setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timepicker)) return false;

        Timepicker that = (Timepicker) o;

        if (getTimeOnlyTitle() != null ? !getTimeOnlyTitle().equals(that.getTimeOnlyTitle()) : that.getTimeOnlyTitle() != null)
            return false;
        if (getTimeText() != null ? !getTimeText().equals(that.getTimeText()) : that.getTimeText() != null)
            return false;
        if (getHourText() != null ? !getHourText().equals(that.getHourText()) : that.getHourText() != null)
            return false;
        if (getMinuteText() != null ? !getMinuteText().equals(that.getMinuteText()) : that.getMinuteText() != null)
            return false;
        if (getSecondText() != null ? !getSecondText().equals(that.getSecondText()) : that.getSecondText() != null)
            return false;
        if (getMillisecText() != null ? !getMillisecText().equals(that.getMillisecText()) : that.getMillisecText() != null)
            return false;
        if (getMicrosecText() != null ? !getMicrosecText().equals(that.getMicrosecText()) : that.getMicrosecText() != null)
            return false;
        if (getTimezoneText() != null ? !getTimezoneText().equals(that.getTimezoneText()) : that.getTimezoneText() != null)
            return false;
        if (getCurrentText() != null ? !getCurrentText().equals(that.getCurrentText()) : that.getCurrentText() != null)
            return false;
        if (getCloseText() != null ? !getCloseText().equals(that.getCloseText()) : that.getCloseText() != null)
            return false;
        if (getTimeFormat() != null ? !getTimeFormat().equals(that.getTimeFormat()) : that.getTimeFormat() != null)
            return false;
        if (getAmNames() != null ? !getAmNames().equals(that.getAmNames()) : that.getAmNames() != null) return false;
        if (getPmNames() != null ? !getPmNames().equals(that.getPmNames()) : that.getPmNames() != null) return false;
        if (getIsRTL() != null ? !getIsRTL().equals(that.getIsRTL()) : that.getIsRTL() != null) return false;
        if (getDateFormat() != null ? !getDateFormat().equals(that.getDateFormat()) : that.getDateFormat() != null)
            return false;
        return !(getSeparator() != null ? !getSeparator().equals(that.getSeparator()) : that.getSeparator() != null);

    }

    @Override
    public int hashCode() {
        int result = getTimeOnlyTitle() != null ? getTimeOnlyTitle().hashCode() : 0;
        result = 31 * result + (getTimeText() != null ? getTimeText().hashCode() : 0);
        result = 31 * result + (getHourText() != null ? getHourText().hashCode() : 0);
        result = 31 * result + (getMinuteText() != null ? getMinuteText().hashCode() : 0);
        result = 31 * result + (getSecondText() != null ? getSecondText().hashCode() : 0);
        result = 31 * result + (getMillisecText() != null ? getMillisecText().hashCode() : 0);
        result = 31 * result + (getMicrosecText() != null ? getMicrosecText().hashCode() : 0);
        result = 31 * result + (getTimezoneText() != null ? getTimezoneText().hashCode() : 0);
        result = 31 * result + (getCurrentText() != null ? getCurrentText().hashCode() : 0);
        result = 31 * result + (getCloseText() != null ? getCloseText().hashCode() : 0);
        result = 31 * result + (getTimeFormat() != null ? getTimeFormat().hashCode() : 0);
        result = 31 * result + (getAmNames() != null ? getAmNames().hashCode() : 0);
        result = 31 * result + (getPmNames() != null ? getPmNames().hashCode() : 0);
        result = 31 * result + (getIsRTL() != null ? getIsRTL().hashCode() : 0);
        result = 31 * result + (getDateFormat() != null ? getDateFormat().hashCode() : 0);
        result = 31 * result + (getSeparator() != null ? getSeparator().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Timepicker{" +
                "timeOnlyTitle='" + timeOnlyTitle + '\'' +
                ", timeText='" + timeText + '\'' +
                ", hourText='" + hourText + '\'' +
                ", minuteText='" + minuteText + '\'' +
                ", secondText='" + secondText + '\'' +
                ", millisecText='" + millisecText + '\'' +
                ", microsecText='" + microsecText + '\'' +
                ", timezoneText='" + timezoneText + '\'' +
                ", currentText='" + currentText + '\'' +
                ", closeText='" + closeText + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", amNames=" + amNames +
                ", pmNames=" + pmNames +
                ", isRTL=" + isRTL +
                ", dateFormat='" + dateFormat + '\'' +
                ", separator='" + separator + '\'' +
                '}';
    }
}
