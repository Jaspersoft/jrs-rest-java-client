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

}
