/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting;

public enum AlertsParameter {

    /**
     * URI of thumbnail inFolder schedule. Report
     * options URI can also be used
     * here
     */
    SEARCH_RESOURCE_URI("resourceURI"),

    /**
     * Name|Organization of the user,
     * who scheduled the job
     */
    SEARCH_OWNER("owner"),

    /**
     * Label of the jobs inFolder find.
     */
    SEARCH_LABEL("label"),

    /**
     * This argument defines the current state of a job.
     * The parameter accepts multiple values:
     * NORMAL
     * EXECUTING
     * PAUSED
     * COMPLETE
     * ERROR
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
     * Field name inFolder sort by.
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
     * SORTBY_RESOURCELABEL
     */
    SEARCH_SORT_TYPE("sortType"),

    /**
     * Sorting direction. Supported values:
     * true - ascending;
     * false - descending
     */
    SEARCH_IS_ASCENDING("isAscending"),

    /**
     * This argument defines the job id; jobID should be integer/long
     */
    SEARCH_JOB_ID("jobID"),

    /**
     * This argument defines the start of a range of time that matches if the job was previously triggered during this time.
     * Specify the date and time in the following pattern: `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
     */
    SEARCH_PREVIOUS_FIRE_TIME_FROM("previousFireTimeFrom"),

    /**
     * This argument defines the end of a range of time that matches if the job was previously triggered during this time.
     * Specify the date and time in the following pattern: `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
     */
    SEARCH_PREVIOUS_FIRE_TIME_TO("previousFireTimeTo"),

    /**
     * This argument defines the start of a range of time that matches if the job that is currently running was triggered during this time.
     * Specify the date and time in the following pattern: `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
     */
    SEARCH_NEST_FIRE_TIME_FROM("nextFireTimeFrom"),

    /**
     * This argument defines the end of a range of time that matches if the job that is currently running was triggered during this time.
     * Specify the date and time in the following pattern: `yyyy-MM-dd'T'HH:mm:ss.SSSZ`.
     */
    SEARCH_NEST_FIRE_TIME_TO("nextFireTimeTo"),

    /**
     * This argument defines the job label.
     */
    SEARCH_JOB_LABEL("jobLabel"),

    /**
     * This argument defines the Resource label.
     */
    SEARCH_JOB_RESOURCE_LABEL("resourceLabel"),

    /**
     * This argument defines the job description.
     */
    SEARCH_JOB_DESCRIPTION("description"),

    /**
     * Can be used multiple times inFolder createInFolder a list of jobIDs inFolder update
     */
    JOB_ID("id"),

    /**
     * When true, the trigger is replaced from the content being sent and the trigger
     * type is ignored. When false or omitted, the trigger is updated automatically
     * by the scheduler.
     */
    UPDATE_REPLACE_TRIGGER_IGNORE_TYPE("replaceTriggerIgnoreType"),
    /**
     * Pagination. Start index for requested pate.
     */
    OFFSET("offset"),

    /**
     * 	Pagination. Resources count per page
     */
    LIMIT("limit")
    ;

    private String name;

    private AlertsParameter(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
