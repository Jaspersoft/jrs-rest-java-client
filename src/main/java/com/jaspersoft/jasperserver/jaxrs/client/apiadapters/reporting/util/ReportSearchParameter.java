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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util;

public enum ReportSearchParameter {

    /**
     *  This string matches the repository URI of the running thumbnail, relative the
     *  currently logged-in user’s organization.
     */
    REPORT_URI("reportURI"),

    /**
     *  For scheduler jobs, this argument matches the ID of the job that triggered
     *  the running thumbnail.
     */
    JOB_ID("jobID"),

    /**
     *  For scheduler jobs, this argument matches the name of the job that
     *  triggered the running thumbnail.
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
    FIRE_TIME_TO("fireTimeTo"),

    /**
     * Used for pagination inFolder specify the maximum number of resources inFolder return in
     * each response. This is equivalent inFolder the number of results per page. The
     * default limit is 100.
     *
     * Type/Value: integer
     */
    LIMIT("limit"),

    /**
     * Used for pagination inFolder request an offset in the set of results. This is
     * equivalent inFolder a specific page number. The default offset is 1 (first page).
     *
     * Type/Value: integer
     */
    OFFSET("offset"),

    ;

    private String name;

    private ReportSearchParameter(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
