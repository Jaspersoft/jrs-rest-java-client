package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

public class JobTrigger {

    private long id;
    private int version;
    private String timezone;
    private String calendarName;
    private String startType;
    private String startDate;
    private String endDate;
    private int misfireInstruction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(int misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobTrigger that = (JobTrigger) o;

        if (id != that.id) return false;
        if (misfireInstruction != that.misfireInstruction) return false;
        if (version != that.version) return false;
        if (calendarName != null ? !calendarName.equals(that.calendarName) : that.calendarName != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (startType != null ? !startType.equals(that.startType) : that.startType != null) return false;
        if (timezone != null ? !timezone.equals(that.timezone) : that.timezone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + version;
        result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
        result = 31 * result + (calendarName != null ? calendarName.hashCode() : 0);
        result = 31 * result + (startType != null ? startType.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + misfireInstruction;
        return result;
    }

    @Override
    public String toString() {
        return "JobTrigger{" +
                "id=" + id +
                ", version=" + version +
                ", timezone='" + timezone + '\'' +
                ", calendarName='" + calendarName + '\'' +
                ", startType='" + startType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", misfireInstruction=" + misfireInstruction +
                '}';
    }
}
