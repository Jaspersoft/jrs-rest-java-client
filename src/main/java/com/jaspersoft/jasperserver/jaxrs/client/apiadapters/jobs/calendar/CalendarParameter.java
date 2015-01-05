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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

public enum CalendarParameter {

    /**
     * If true, any calendar existing in the JobStore with the same name should be
     * over-written.
     */
    REPLACE("replace"),

    /**
     * whether or not inFolder update existing triggers that referenced the already
     * existing calendar so that they are 'correct' based on the new trigger.
     */
    UPDATE_TRIGGERS("updateTriggers");

    private String name;

    private CalendarParameter(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
