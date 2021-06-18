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
import com.jaspersoft.jasperserver.dto.job.adapters.ExcludeDaysXmlAdapter;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "reportJobCalendar")
public class AnnualCalendar extends Calendar {

    private ArrayList<java.util.Calendar> excludeDays = new ArrayList<java.util.Calendar>();
    // true, if excludeDays is sorted
    private Boolean dataSorted;

    public AnnualCalendar() {
        super();
        this.calendarType = ClientJobCalendar.Type.annual;
    }

    public AnnualCalendar(AnnualCalendar other) {
        super(other);
        this.excludeDays = new ArrayList<java.util.Calendar>();
        if (other.getExcludeDays() != null) {
            for (java.util.Calendar calendar : other.getExcludeDays()) {
                this.excludeDays.add((java.util.Calendar) calendar.clone());
            }
        }
        this.dataSorted = other.dataSorted;
    }

    @XmlJavaTypeAdapter(ExcludeDaysXmlAdapter.class)
    public ArrayList<java.util.Calendar> getExcludeDays() {
        return excludeDays;
    }

    public AnnualCalendar setExcludeDays(ArrayList<java.util.Calendar> excludeDays) {
        this.excludeDays = excludeDays;
        return this;
    }

    public Boolean getDataSorted() {
        return dataSorted;
    }

    public AnnualCalendar setDataSorted(Boolean dataSorted) {
        this.dataSorted = dataSorted;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnnualCalendar that = (AnnualCalendar) o;

        if (dataSorted != null ? !dataSorted.equals(that.dataSorted) : that.dataSorted != null) return false;
        if (excludeDays != null ? !excludeDays.equals(that.excludeDays) : that.excludeDays != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (excludeDays != null ? excludeDays.hashCode() : 0);
        result = 31 * result + (dataSorted != null ? dataSorted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnnualCalendar{" +
                "excludeDays=" + excludeDays +
                ", dataSorted=" + dataSorted +
                '}';
    }
}
