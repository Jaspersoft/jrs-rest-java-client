package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;


import java.util.LinkedList;
import java.util.List;

/**
 * @author Tetiana Iefimenko
 */
public class Datepicker {
    private String closeText;
    private String prevText;
    private String nextText;
    private String currentText;
    private List<String> monthNames;
    private List<String> monthNamesShort;
    private List<String> dayNames;
    private List<String> dayNamesShort;
    private List<String> dayNamesMin;
    private String weekHeader;
    private String dateFormat;
    private Integer firstDay;
    private Boolean isRTL;
    private Boolean showMonthAfterYear;
    private String yearSuffix;

    public Datepicker() {
    }

    public Datepicker(Datepicker other) {
        this.closeText = other.closeText;
        this.prevText = other.prevText;
        this.nextText = other.nextText;
        this.currentText = other.currentText;
        this.monthNames = (other.monthNames != null) ? new LinkedList<String>(other.monthNames) : null;
        this.monthNamesShort = (other.monthNamesShort != null) ? new LinkedList<String>(other.monthNamesShort) : null;
        this.dayNames = (other.dayNames != null) ? new LinkedList<String>(other.dayNames) : null;
        this.dayNamesShort = (other.dayNamesShort != null) ? new LinkedList<String>(other.dayNamesShort) : null;
        this.dayNamesMin = (other.dayNamesMin != null) ? new LinkedList<String>(other.dayNamesMin) : null;
        this.weekHeader = other.weekHeader;
        this.dateFormat = other.dateFormat;
        this.firstDay = other.firstDay;
        this.isRTL = other.isRTL;
        this.showMonthAfterYear = other.showMonthAfterYear;
        this.yearSuffix = other.yearSuffix;
    }

    public String getCloseText() {
        return closeText;
    }

    public Datepicker setCloseText(String closeText) {
        this.closeText = closeText;
        return this;
    }

    public String getPrevText() {
        return prevText;
    }

    public Datepicker setPrevText(String prevText) {
        this.prevText = prevText;
        return this;
    }

    public String getNextText() {
        return nextText;
    }

    public Datepicker setNextText(String nextText) {
        this.nextText = nextText;
        return this;
    }

    public String getCurrentText() {
        return currentText;
    }

    public Datepicker setCurrentText(String currentText) {
        this.currentText = currentText;
        return this;
    }

    public List<String> getMonthNames() {
        return monthNames;
    }

    public Datepicker setMonthNames(List<String> monthNames) {
        this.monthNames = monthNames;
        return this;
    }

    public List<String> getMonthNamesShort() {
        return monthNamesShort;
    }

    public Datepicker setMonthNamesShort(List<String> monthNamesShort) {
        this.monthNamesShort = monthNamesShort;
        return this;
    }

    public List<String> getDayNames() {
        return dayNames;
    }

    public Datepicker setDayNames(List<String> dayNames) {
        this.dayNames = dayNames;
        return this;
    }

    public List<String> getDayNamesShort() {
        return dayNamesShort;
    }

    public Datepicker setDayNamesShort(List<String> dayNamesShort) {
        this.dayNamesShort = dayNamesShort;
        return this;
    }

    public List<String> getDayNamesMin() {
        return dayNamesMin;
    }

    public Datepicker setDayNamesMin(List<String> dayNamesMin) {
        this.dayNamesMin = dayNamesMin;
        return this;
    }

    public String getWeekHeader() {
        return weekHeader;
    }

    public Datepicker setWeekHeader(String weekHeader) {
        this.weekHeader = weekHeader;
        return this;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Datepicker setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public Integer getFirstDay() {
        return firstDay;
    }

    public Datepicker setFirstDay(Integer firstDay) {
        this.firstDay = firstDay;
        return this;
    }

    public Boolean getIsRTL() {
        return isRTL;
    }

    public Datepicker setIsRTL(Boolean isRTL) {
        this.isRTL = isRTL;
        return this;
    }

    public Boolean getShowMonthAfterYear() {
        return showMonthAfterYear;
    }

    public Datepicker setShowMonthAfterYear(Boolean showMonthAfterYear) {
        this.showMonthAfterYear = showMonthAfterYear;
        return this;
    }

    public String getYearSuffix() {
        return yearSuffix;
    }

    public Datepicker setYearSuffix(String yearSuffix) {
        this.yearSuffix = yearSuffix;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Datepicker)) return false;

        Datepicker that = (Datepicker) o;

        if (getCloseText() != null ? !getCloseText().equals(that.getCloseText()) : that.getCloseText() != null)
            return false;
        if (getPrevText() != null ? !getPrevText().equals(that.getPrevText()) : that.getPrevText() != null)
            return false;
        if (getNextText() != null ? !getNextText().equals(that.getNextText()) : that.getNextText() != null)
            return false;
        if (getCurrentText() != null ? !getCurrentText().equals(that.getCurrentText()) : that.getCurrentText() != null)
            return false;
        if (getMonthNames() != null ? !getMonthNames().equals(that.getMonthNames()) : that.getMonthNames() != null)
            return false;
        if (getMonthNamesShort() != null ? !getMonthNamesShort().equals(that.getMonthNamesShort()) : that.getMonthNamesShort() != null)
            return false;
        if (getDayNames() != null ? !getDayNames().equals(that.getDayNames()) : that.getDayNames() != null)
            return false;
        if (getDayNamesShort() != null ? !getDayNamesShort().equals(that.getDayNamesShort()) : that.getDayNamesShort() != null)
            return false;
        if (getDayNamesMin() != null ? !getDayNamesMin().equals(that.getDayNamesMin()) : that.getDayNamesMin() != null)
            return false;
        if (getWeekHeader() != null ? !getWeekHeader().equals(that.getWeekHeader()) : that.getWeekHeader() != null)
            return false;
        if (getDateFormat() != null ? !getDateFormat().equals(that.getDateFormat()) : that.getDateFormat() != null)
            return false;
        if (getFirstDay() != null ? !getFirstDay().equals(that.getFirstDay()) : that.getFirstDay() != null)
            return false;
        if (getIsRTL() != null ? !getIsRTL().equals(that.getIsRTL()) : that.getIsRTL() != null) return false;
        if (getShowMonthAfterYear() != null ? !getShowMonthAfterYear().equals(that.getShowMonthAfterYear()) : that.getShowMonthAfterYear() != null)
            return false;
        return !(getYearSuffix() != null ? !getYearSuffix().equals(that.getYearSuffix()) : that.getYearSuffix() != null);

    }

    @Override
    public int hashCode() {
        int result = getCloseText() != null ? getCloseText().hashCode() : 0;
        result = 31 * result + (getPrevText() != null ? getPrevText().hashCode() : 0);
        result = 31 * result + (getNextText() != null ? getNextText().hashCode() : 0);
        result = 31 * result + (getCurrentText() != null ? getCurrentText().hashCode() : 0);
        result = 31 * result + (getMonthNames() != null ? getMonthNames().hashCode() : 0);
        result = 31 * result + (getMonthNamesShort() != null ? getMonthNamesShort().hashCode() : 0);
        result = 31 * result + (getDayNames() != null ? getDayNames().hashCode() : 0);
        result = 31 * result + (getDayNamesShort() != null ? getDayNamesShort().hashCode() : 0);
        result = 31 * result + (getDayNamesMin() != null ? getDayNamesMin().hashCode() : 0);
        result = 31 * result + (getWeekHeader() != null ? getWeekHeader().hashCode() : 0);
        result = 31 * result + (getDateFormat() != null ? getDateFormat().hashCode() : 0);
        result = 31 * result + (getFirstDay() != null ? getFirstDay().hashCode() : 0);
        result = 31 * result + (getIsRTL() != null ? getIsRTL().hashCode() : 0);
        result = 31 * result + (getShowMonthAfterYear() != null ? getShowMonthAfterYear().hashCode() : 0);
        result = 31 * result + (getYearSuffix() != null ? getYearSuffix().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Datepicker{" +
                "closeText='" + closeText + '\'' +
                ", prevText='" + prevText + '\'' +
                ", nextText='" + nextText + '\'' +
                ", currentText='" + currentText + '\'' +
                ", monthNames=" + monthNames +
                ", monthNamesShort=" + monthNamesShort +
                ", dayNames=" + dayNames +
                ", dayNamesShort=" + dayNamesShort +
                ", dayNamesMin=" + dayNamesMin +
                ", weekHeader='" + weekHeader + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", firstDay=" + firstDay +
                ", isRTL=" + isRTL +
                ", showMonthAfterYear=" + showMonthAfterYear +
                ", yearSuffix='" + yearSuffix + '\'' +
                '}';
    }
}
