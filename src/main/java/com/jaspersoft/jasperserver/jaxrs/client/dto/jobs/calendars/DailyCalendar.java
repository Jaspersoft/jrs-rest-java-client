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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

public class DailyCalendar extends Calendar {

    private java.util.Calendar rangeStartingCalendar;
    private java.util.Calendar rangeEndingCalendar;
    private Boolean invertTimeRange;

    public DailyCalendar() {
        super();
        this.calendarType = TYPE_DAILY;
    }

    public java.util.Calendar getRangeStartingCalendar() {
        return rangeStartingCalendar;
    }

    public void setRangeStartingCalendar(java.util.Calendar rangeStartingCalendar) {
        this.rangeStartingCalendar = rangeStartingCalendar;
    }

    public java.util.Calendar getRangeEndingCalendar() {
        return rangeEndingCalendar;
    }

    public void setRangeEndingCalendar(java.util.Calendar rangeEndingCalendar) {
        this.rangeEndingCalendar = rangeEndingCalendar;
    }

    public Boolean getInvertTimeRange() {
        return invertTimeRange;
    }

    public void setInvertTimeRange(Boolean invertTimeRange) {
        this.invertTimeRange = invertTimeRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DailyCalendar that = (DailyCalendar) o;

        if (invertTimeRange != null ? !invertTimeRange.equals(that.invertTimeRange) : that.invertTimeRange != null)
            return false;
        if (rangeEndingCalendar != null ? !rangeEndingCalendar.equals(that.rangeEndingCalendar) : that.rangeEndingCalendar != null)
            return false;
        if (rangeStartingCalendar != null ? !rangeStartingCalendar.equals(that.rangeStartingCalendar) : that.rangeStartingCalendar != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rangeStartingCalendar != null ? rangeStartingCalendar.hashCode() : 0);
        result = 31 * result + (rangeEndingCalendar != null ? rangeEndingCalendar.hashCode() : 0);
        result = 31 * result + (invertTimeRange != null ? invertTimeRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyCalendar{" +
                "rangeStartingCalendar=" + rangeStartingCalendar +
                ", rangeEndingCalendar=" + rangeEndingCalendar +
                ", invertTimeRange=" + invertTimeRange +
                "} " + super.toString();
    }
}
