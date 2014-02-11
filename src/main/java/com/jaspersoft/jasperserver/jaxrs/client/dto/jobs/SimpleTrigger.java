package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "simpleTrigger")
public class SimpleTrigger extends JobTrigger {

    private Integer occurrenceCount;
    private Integer recurrenceInterval;
    private String recurrenceIntervalUnit;

    public Integer getOccurrenceCount() {
        return occurrenceCount;
    }

    public void setOccurrenceCount(Integer occurrenceCount) {
        this.occurrenceCount = occurrenceCount;
    }

    public Integer getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(Integer recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public String getRecurrenceIntervalUnit() {
        return recurrenceIntervalUnit;
    }

    public void setRecurrenceIntervalUnit(String recurrenceIntervalUnit) {
        this.recurrenceIntervalUnit = recurrenceIntervalUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SimpleTrigger that = (SimpleTrigger) o;

        if (occurrenceCount != that.occurrenceCount) return false;
        if (recurrenceInterval != null ? !recurrenceInterval.equals(that.recurrenceInterval) : that.recurrenceInterval != null)
            return false;
        if (recurrenceIntervalUnit != null ? !recurrenceIntervalUnit.equals(that.recurrenceIntervalUnit) : that.recurrenceIntervalUnit != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + occurrenceCount;
        result = 31 * result + (recurrenceInterval != null ? recurrenceInterval.hashCode() : 0);
        result = 31 * result + (recurrenceIntervalUnit != null ? recurrenceIntervalUnit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SimpleTrigger{" +
                "occurrenceCount=" + occurrenceCount +
                ", recurrenceInterval=" + recurrenceInterval +
                ", recurrenceIntervalUnit='" + recurrenceIntervalUnit + '\'' +
                '}';
    }
}
