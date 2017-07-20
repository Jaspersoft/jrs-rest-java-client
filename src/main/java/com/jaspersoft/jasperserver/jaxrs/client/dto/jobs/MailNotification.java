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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "mailNotification")
public class MailNotification {


    private List<String> bccAddresses;
    private List<String> ccAddresses;
    private List<String> toAddresses;
    private Integer version;
    private Long id;
    private Boolean includingStackTraceWhenJobFails;
    private String messageText;
    private MailNotificationSendType resultSendType;
    private Boolean skipEmptyReports;
    private Boolean skipNotificationWhenJobFails;
    private String subject;
    private String messageTextWhenJobFails;

    public MailNotification() {
    }

    public MailNotification(MailNotification other) {
        this.bccAddresses = (other.bccAddresses != null) ? new LinkedList<String>(other.bccAddresses) : null;
        this.ccAddresses = (other.ccAddresses != null) ? new LinkedList<String>(other.ccAddresses) : null;
        this.toAddresses = (other.toAddresses != null) ? new LinkedList<String>(other.toAddresses) : null;
        this.id = other.id;
        this.includingStackTraceWhenJobFails = other.includingStackTraceWhenJobFails;
        this.messageText = other.messageText;
        this.messageTextWhenJobFails = other.messageTextWhenJobFails;
        this.resultSendType = other.resultSendType;
        this.skipEmptyReports = other.skipEmptyReports;
        this.skipNotificationWhenJobFails = other.skipNotificationWhenJobFails;
        this.subject = other.subject;
        this.version = other.version;
    }

    @XmlJavaTypeAdapter(AddressesXmlAdapter.class)
    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    public MailNotification setCcAddresses(List<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
        return this;
    }

    @XmlJavaTypeAdapter(AddressesXmlAdapter.class)
    public List<String> getToAddresses() {
        return toAddresses;
    }

    public MailNotification setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
        return this;
    }

    @XmlJavaTypeAdapter(AddressesXmlAdapter.class)
    public List<String> getBccAddresses() {
        return bccAddresses;
    }

    public MailNotification setBccAddresses(List<String> bccAddresses) {
        this.bccAddresses = bccAddresses;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public MailNotification setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Long getId() {
        return id;
    }

    public MailNotification setId(Long id) {
        this.id = id;
        return this;
    }

    public Boolean isIncludingStackTraceWhenJobFails() {
        return includingStackTraceWhenJobFails;
    }

    public MailNotification setIncludingStackTraceWhenJobFails(Boolean includingStackTraceWhenJobFails) {
        this.includingStackTraceWhenJobFails = includingStackTraceWhenJobFails;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public MailNotification setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public Boolean isSkipEmptyReports() {
        return skipEmptyReports;
    }

    public MailNotification setSkipEmptyReports(Boolean skipEmptyReports) {
        this.skipEmptyReports = skipEmptyReports;
        return this;
    }

    public Boolean isSkipNotificationWhenJobFails() {
        return skipNotificationWhenJobFails;
    }

    public MailNotification setSkipNotificationWhenJobFails(Boolean skipNotificationWhenJobFails) {
        this.skipNotificationWhenJobFails = skipNotificationWhenJobFails;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailNotification setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessageTextWhenJobFails() {
        return messageTextWhenJobFails;
    }

    public MailNotification setMessageTextWhenJobFails(String messageTextWhenJobFails) {
        this.messageTextWhenJobFails = messageTextWhenJobFails;
        return this;
    }

    public MailNotificationSendType getResultSendType() {
        return resultSendType;
    }

    public MailNotification setResultSendType(MailNotificationSendType resultSendType) {
        this.resultSendType = resultSendType;
        return this;
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
