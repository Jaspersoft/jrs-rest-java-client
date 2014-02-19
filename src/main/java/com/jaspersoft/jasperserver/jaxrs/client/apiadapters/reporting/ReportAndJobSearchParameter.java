package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

public enum ReportAndJobSearchParameter {

    /**
     *  This string matches the repository URI of the running report, relative the
     *  currently logged-in user’s organization.
     */
    REPORT_URI("reportURI"),

    /**
     *  For scheduler jobs, this argument matches the ID of the job that triggered
     *  the running report.
     */
    JOB_ID("jobID"),

    /**
     *  For scheduler jobs, this argument matches the name of the job that
     *  triggered the running report.
     */
    JOB_LABEL("jobLabel"),

    /**
     *  For scheduler jobs, this argument matches the user ID that created the job.
     */
    USER_NAME("userName"),

    /**
     *  For scheduler jobs, the fire time arguments define a range of time that
     *  matches if the job that is currently running was triggered during this time.
     *  You can specify either or both of the arguments. Specify the date and time
     *  in the following pattern: fireTimeTo yyyy-MM-dd'T'HH:mmZ.
     */
    FIRE_TIME_FROM("fireTimeFrom"),

    /**
     *  For scheduler jobs, the fire time arguments define a range of time that
     *  matches if the job that is currently running was triggered during this time.
     *  You can specify either or both of the arguments. Specify the date and time
     *  in the following pattern: fireTimeTo yyyy-MM-dd'T'HH:mmZ.
     */
    FIRE_TIME_TO("fireTimeTo")

    ;

    private String name;

    private ReportAndJobSearchParameter(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
