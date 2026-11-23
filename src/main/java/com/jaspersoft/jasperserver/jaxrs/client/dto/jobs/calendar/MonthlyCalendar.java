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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendar;


import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;
import java.util.Arrays;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportJobCalendar")
public class MonthlyCalendar extends Calendar {

    private boolean[] excludeDaysFlags;

    public MonthlyCalendar() {
        super();
        this.calendarType = ClientJobCalendar.Type.monthly;
    }

    public MonthlyCalendar(MonthlyCalendar other) {
        super(other);
        if (other.excludeDaysFlags != null) {
            this.excludeDaysFlags = new boolean[other.excludeDaysFlags.length];
            for (int i = 0; i < other.excludeDaysFlags.length; i++) {
                this.excludeDaysFlags[i] = other.excludeDaysFlags[i];
            }
        }
    }

    @XmlElementWrapper(name = "excludeDaysFlags")
    @XmlElement(name = "excludeDayFlag")
    public boolean[] getExcludeDaysFlags() {
        return excludeDaysFlags;
    }

    public MonthlyCalendar setExcludeDaysFlags(boolean[] excludeDaysFlags) {
        this.excludeDaysFlags = excludeDaysFlags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MonthlyCalendar that = (MonthlyCalendar) o;

        if (!Arrays.equals(excludeDaysFlags, that.excludeDaysFlags)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (excludeDaysFlags != null ? Arrays.hashCode(excludeDaysFlags) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MonthlyCalendar{" +
                "excludeDaysFlags=" + excludeDaysFlags +
                "} " + super.toString();
    }
}
