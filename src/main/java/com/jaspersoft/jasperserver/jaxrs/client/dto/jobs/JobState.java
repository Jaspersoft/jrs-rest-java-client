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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.DateToStringXmlAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
/**
 * @deprecated (use server DTO).
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "state")
public class JobState {

    private Date previousFireTime;
    private Date nextFireTime;
    private JobStateType value;

    public JobState() {
    }

    public JobState(JobState other) {
        this.nextFireTime = (other.nextFireTime != null) ? new Date(other.nextFireTime.getTime()) : null;
        this.previousFireTime = (other.previousFireTime != null) ? new Date(other.previousFireTime.getTime()) : null;
        this.value = other.value;
    }

    @XmlJavaTypeAdapter(DateToStringXmlAdapter.class)
    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public JobState setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
        return this;
    }

    @XmlJavaTypeAdapter(DateToStringXmlAdapter.class)
    public Date getNextFireTime() {
        return nextFireTime;
    }

    public JobState setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
        return this;
    }

    public JobStateType getValue() {
        return value;
    }

    public JobState setValue(JobStateType value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobState)) return false;

        JobState jobState = (JobState) o;

        if (nextFireTime != null ? !nextFireTime.equals(jobState.nextFireTime) : jobState.nextFireTime != null)
            return false;
        if (previousFireTime != null ? !previousFireTime.equals(jobState.previousFireTime) : jobState.previousFireTime != null)
            return false;
        if (value != null ? !value.equals(jobState.value) : jobState.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = previousFireTime != null ? previousFireTime.hashCode() : 0;
        result = 31 * result + (nextFireTime != null ? nextFireTime.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobState{" +
                "previousFireTime='" + previousFireTime + '\'' +
                ", nextFireTime='" + nextFireTime + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
