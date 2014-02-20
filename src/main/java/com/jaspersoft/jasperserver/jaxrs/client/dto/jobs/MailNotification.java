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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.AddressListWrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mailNotification")
public class MailNotification {

    @XmlElement(name = "bccAddresses")
    private AddressListWrapper bccAddresses;

    @XmlElement(name = "ccAddresses")
    private AddressListWrapper ccAddresses;

    @XmlElement(name = "toAddresses")
    private AddressListWrapper toAddresses;

    private Integer version;
    private Long id;
    private Boolean includingStackTraceWhenJobFails;
    private String messageText;
    private String resultSendType;
    private Boolean skipEmptyReports;
    private Boolean skipNotificationWhenJobFails;
    private String subject;
    private String messageTextWhenJobFails;

    public AddressListWrapper getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(AddressListWrapper ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public AddressListWrapper getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(AddressListWrapper toAddresses) {
        this.toAddresses = toAddresses;
    }

    public AddressListWrapper getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(AddressListWrapper bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIncludingStackTraceWhenJobFails() {
        return includingStackTraceWhenJobFails;
    }

    public void setIncludingStackTraceWhenJobFails(Boolean includingStackTraceWhenJobFails) {
        this.includingStackTraceWhenJobFails = includingStackTraceWhenJobFails;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean isSkipEmptyReports() {
        return skipEmptyReports;
    }

    public void setSkipEmptyReports(Boolean skipEmptyReports) {
        this.skipEmptyReports = skipEmptyReports;
    }

    public Boolean isSkipNotificationWhenJobFails() {
        return skipNotificationWhenJobFails;
    }

    public void setSkipNotificationWhenJobFails(Boolean skipNotificationWhenJobFails) {
        this.skipNotificationWhenJobFails = skipNotificationWhenJobFails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageTextWhenJobFails() {
        return messageTextWhenJobFails;
    }

    public void setMessageTextWhenJobFails(String messageTextWhenJobFails) {
        this.messageTextWhenJobFails = messageTextWhenJobFails;
    }

    public String getResultSendType() {
        return resultSendType;
    }

    public void setResultSendType(String resultSendType) {
        this.resultSendType = resultSendType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailNotification)) return false;

        MailNotification that = (MailNotification) o;

        if (bccAddresses != null ? !bccAddresses.equals(that.bccAddresses) : that.bccAddresses != null) return false;
        if (ccAddresses != null ? !ccAddresses.equals(that.ccAddresses) : that.ccAddresses != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (includingStackTraceWhenJobFails != null ? !includingStackTraceWhenJobFails.equals(that.includingStackTraceWhenJobFails) : that.includingStackTraceWhenJobFails != null)
            return false;
        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;
        if (messageTextWhenJobFails != null ? !messageTextWhenJobFails.equals(that.messageTextWhenJobFails) : that.messageTextWhenJobFails != null)
            return false;
        if (resultSendType != null ? !resultSendType.equals(that.resultSendType) : that.resultSendType != null)
            return false;
        if (skipEmptyReports != null ? !skipEmptyReports.equals(that.skipEmptyReports) : that.skipEmptyReports != null)
            return false;
        if (skipNotificationWhenJobFails != null ? !skipNotificationWhenJobFails.equals(that.skipNotificationWhenJobFails) : that.skipNotificationWhenJobFails != null)
            return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (toAddresses != null ? !toAddresses.equals(that.toAddresses) : that.toAddresses != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bccAddresses != null ? bccAddresses.hashCode() : 0;
        result = 31 * result + (ccAddresses != null ? ccAddresses.hashCode() : 0);
        result = 31 * result + (toAddresses != null ? toAddresses.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (includingStackTraceWhenJobFails != null ? includingStackTraceWhenJobFails.hashCode() : 0);
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (resultSendType != null ? resultSendType.hashCode() : 0);
        result = 31 * result + (skipEmptyReports != null ? skipEmptyReports.hashCode() : 0);
        result = 31 * result + (skipNotificationWhenJobFails != null ? skipNotificationWhenJobFails.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (messageTextWhenJobFails != null ? messageTextWhenJobFails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MailNotification{" +
                "bccAddresses=" + bccAddresses +
                ", ccAddresses=" + ccAddresses +
                ", toAddresses=" + toAddresses +
                ", version=" + version +
                ", id=" + id +
                ", includingStackTraceWhenJobFails=" + includingStackTraceWhenJobFails +
                ", messageText='" + messageText + '\'' +
                ", resultSendType='" + resultSendType + '\'' +
                ", skipEmptyReports=" + skipEmptyReports +
                ", skipNotificationWhenJobFails=" + skipNotificationWhenJobFails +
                ", subject='" + subject + '\'' +
                ", messageTextWhenJobFails='" + messageTextWhenJobFails + '\'' +
                '}';
    }
}
