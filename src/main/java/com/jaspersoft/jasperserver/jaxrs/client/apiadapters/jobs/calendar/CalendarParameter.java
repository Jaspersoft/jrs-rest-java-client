package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

public enum CalendarParameter {

    /**
     * If true, any calendar existing in the JobStore with the same name should be
     * over-written.
     */
    REPLACE("replace"),

    /**
     * whether or not to update existing triggers that referenced the already
     * existing calendar so that they are 'correct' based on the new trigger.
     */
    UPDATE_TRIGGERS("updateTriggers")
    ;

    private String name;

    private CalendarParameter(String name){
        this.name = name;
    }

    public String getName() {
        return null;
    }
}
