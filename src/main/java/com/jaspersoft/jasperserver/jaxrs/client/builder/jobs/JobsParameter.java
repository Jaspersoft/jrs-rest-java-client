package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

public enum JobsParameter {

    /**
     * URI of report to schedule. Report
     * options URI can also be used
     * here
     */
    SEARCH_REPORT_UNIT_URI("reportUnitURI"),

    /**
     * Name|Organization of the user,
     * who scheduled the job
     */
    SEARCH_OWNER("owner"),

    /**
     * Label of the jobs to find.
     */
    SEARCH_LABEL("label"),

    /**
     * Reserved, not implemented
     */
    SEARCH_STATE("state"),

    /**
     * Reserved, not implemented
     */
    SEARCH_PREVIOUS_FIRE_TIME("previousFireTime"),

    /**
     * Reserved, not implemented
     */
    SEARCH_NEXT_FIRE_TIME("nextFireTime"),

    /**
     * Pagination, start index
     */
    SEARCH_START_INDEX("startIndex"),

    /**
     * Pagination, results count in a
     * page
     */
    SEARCH_NUMBER_OF_ROWS("numberOfRows"),

    /**
     * Field name to sort by.
     * Supported values:
     * NONE
     * SORTBY_JOBID
     * SORTBY_JOBNAME
     * SORTBY_REPORTURI
     * SORTBY_REPORTNAME
     * SORTBY_REPORTFOLDER
     * SORTBY_OWNER
     * SORTBY_STATUS
     * SORTBY_LASTRUN
     * SORTBY_NEXTRUN
     */
    SEARCH_SORT_TYPE("sortType"),

    /**
     * Sorting direction. Supported values:
     * true - ascending;
     * false - descending
     */
    SEARCH_IS_ASCENDING("isAscending"),

    /**
     * Can be used multiple times to create a list of jobIDs to update
     */
    JOB_ID("id"),

    /**
     * When true, the trigger is replaced from the content being sent and the trigger
     * type is ignored. When false or omitted, the trigger is updated automatically
     * by the scheduler.
     */
    UPDATE_REPLACE_TRIGGER_IGNORE_TYPE("replaceTriggerIgnoreType")
    ;

    private String name;

    private JobsParameter(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
