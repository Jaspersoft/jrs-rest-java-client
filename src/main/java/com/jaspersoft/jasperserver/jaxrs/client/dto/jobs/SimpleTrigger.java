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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "simpleTrigger")
public class SimpleTrigger extends JobTrigger {

    private Integer occurrenceCount;
    private Integer recurrenceInterval;
    private IntervalUnitType recurrenceIntervalUnit;

    public SimpleTrigger() {
        super();
    }

    public SimpleTrigger(SimpleTrigger other) {
        super(other);
        this.occurrenceCount = other.occurrenceCount;
        this.recurrenceInterval = other.recurrenceInterval;
        this.recurrenceIntervalUnit = other.recurrenceIntervalUnit;
    }

    public Integer getOccurrenceCount() {
        return occurrenceCount;
    }

    public SimpleTrigger setOccurrenceCount(Integer occurrenceCount) {
        this.occurrenceCount = occurrenceCount;
        return this;
    }

    public Integer getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public SimpleTrigger setRecurrenceInterval(Integer recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
        return this;
    }

    public IntervalUnitType getRecurrenceIntervalUnit() {
        return recurrenceIntervalUnit;
    }

    public SimpleTrigger setRecurrenceIntervalUnit(IntervalUnitType recurrenceIntervalUnit) {
        this.recurrenceIntervalUnit = recurrenceIntervalUnit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleTrigger)) return false;
        if (!super.equals(o)) return false;

        SimpleTrigger that = (SimpleTrigger) o;

        if (occurrenceCount != null ? !occurrenceCount.equals(that.occurrenceCount) : that.occurrenceCount != null)
            return false;
        if (recurrenceInterval != null ? !recurrenceInterval.equals(that.recurrenceInterval) : that.recurrenceInterval != null)
            return false;
        if (recurrenceIntervalUnit != null ? !recurrenceIntervalUnit.equals(that.recurrenceIntervalUnit) : that.recurrenceIntervalUnit != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (occurrenceCount != null ? occurrenceCount.hashCode() : 0);
        result = 31 * result + (recurrenceInterval != null ? recurrenceInterval.hashCode() : 0);
        result = 31 * result + (recurrenceIntervalUnit != null ? recurrenceIntervalUnit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SimpleTrigger{" +
                "occurrenceCount=" + occurrenceCount +
                ", recurrenceInterval=" + recurrenceInterval +
                ", recurrenceIntervalUnit='" + recurrenceIntervalUnit + '\'' +
                "} " + super.toString();
    }
}
