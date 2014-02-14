package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

public class CronCalendar extends Calendar {

    private String cronExpression;

    public CronCalendar() {
        super();
        this.calendarType = TYPE_CRON;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CronCalendar that = (CronCalendar) o;

        if (cronExpression != null ? !cronExpression.equals(that.cronExpression) : that.cronExpression != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CronCalendar{" +
                "cronExpression='" + cronExpression + '\'' +
                "} " + super.toString();
    }
}
