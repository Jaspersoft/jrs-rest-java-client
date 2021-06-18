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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.AddressesXmlAdapter;

import java.util.LinkedList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "reportJobAlert")
public class JobAlert {

    private Long id;
    private Integer version;
    private JobAlertRecipient recipient;
    private JobAlertJobState jobState;
    private String messageText;
    private String messageTextWhenJobFails;
    private String subject;
    private Boolean includingStackTrace;
    private Boolean includingReportJobInfo;
    private List<String> toAddresses;

    public JobAlert() {
    }

    public JobAlert(JobAlert other) {
        this.id = other.id;
        this.includingReportJobInfo = other.includingReportJobInfo;
        this.includingStackTrace = other.includingStackTrace;
        this.jobState = other.jobState;
        this.messageText = other.messageText;
        this.messageTextWhenJobFails = other.messageTextWhenJobFails;
        this.recipient = other.recipient;
        this.subject = other.subject;
        this.toAddresses = (other.toAddresses != null) ? new LinkedList<String>(other.toAddresses) : null;
        this.version = other.version;

    }

    @XmlElement(name = "toAddresses")
    public List<String> getToAddresses() {
        return toAddresses;
    }

    @XmlJavaTypeAdapter(AddressesXmlAdapter.class)
    public JobAlert setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
        return this;
    }

    public Long getId() {
        return id;
    }

    public JobAlert setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public JobAlert setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public JobAlertRecipient getRecipient() {
        return recipient;
    }

    public JobAlert setRecipient(JobAlertRecipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public JobAlertJobState getJobState() {
        return jobState;
    }

    public JobAlert setJobState(JobAlertJobState jobState) {
        this.jobState = jobState;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public JobAlert setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public String getMessageTextWhenJobFails() {
        return messageTextWhenJobFails;
    }

    public JobAlert setMessageTextWhenJobFails(String messageTextWhenJobFails) {
        this.messageTextWhenJobFails = messageTextWhenJobFails;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public JobAlert setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Boolean isIncludingStackTrace() {
        return includingStackTrace;
    }

    public JobAlert setIncludingStackTrace(Boolean includingStackTrace) {
        this.includingStackTrace = includingStackTrace;
        return this;
    }

    public Boolean isIncludingReportJobInfo() {
        return includingReportJobInfo;
    }

    public JobAlert setIncludingReportJobInfo(Boolean includingReportJobInfo) {
        this.includingReportJobInfo = includingReportJobInfo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobAlert jobAlert = (JobAlert) o;

        if (id != null ? !id.equals(jobAlert.id) : jobAlert.id != null) return false;
        if (includingReportJobInfo != null ? !includingReportJobInfo.equals(jobAlert.includingReportJobInfo) : jobAlert.includingReportJobInfo != null)
            return false;
        if (includingStackTrace != null ? !includingStackTrace.equals(jobAlert.includingStackTrace) : jobAlert.includingStackTrace != null)
            return false;
        if (jobState != null ? !jobState.equals(jobAlert.jobState) : jobAlert.jobState != null) return false;
        if (messageText != null ? !messageText.equals(jobAlert.messageText) : jobAlert.messageText != null)
            return false;
        if (messageTextWhenJobFails != null ? !messageTextWhenJobFails.equals(jobAlert.messageTextWhenJobFails) : jobAlert.messageTextWhenJobFails != null)
            return false;
        if (recipient != null ? !recipient.equals(jobAlert.recipient) : jobAlert.recipient != null) return false;
        if (subject != null ? !subject.equals(jobAlert.subject) : jobAlert.subject != null) return false;
        if (toAddresses != null ? !toAddresses.equals(jobAlert.toAddresses) : jobAlert.toAddresses != null)
            return false;
        if (version != null ? !version.equals(jobAlert.version) : jobAlert.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (jobState != null ? jobState.hashCode() : 0);
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (messageTextWhenJobFails != null ? messageTextWhenJobFails.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (includingStackTrace != null ? includingStackTrace.hashCode() : 0);
        result = 31 * result + (includingReportJobInfo != null ? includingReportJobInfo.hashCode() : 0);
        result = 31 * result + (toAddresses != null ? toAddresses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobAlert{" +
                "id=" + id +
                ", version=" + version +
                ", recipient='" + recipient + '\'' +
                ", jobState='" + jobState + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageTextWhenJobFails='" + messageTextWhenJobFails + '\'' +
                ", subject='" + subject + '\'' +
                ", includingStackTrace=" + includingStackTrace +
                ", includingReportJobInfo=" + includingReportJobInfo +
                ", toAddresses=" + toAddresses +
                '}';
    }
}
