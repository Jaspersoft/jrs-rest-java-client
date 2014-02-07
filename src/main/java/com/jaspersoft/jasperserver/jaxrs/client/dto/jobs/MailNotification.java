package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.AddressListWrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mailNotification")
public class MailNotification {

    private static final int VERSION_NEW = -1;

    @XmlElement(name = "bccAddresses")
    private AddressListWrapper bccAddresses;

    @XmlElement(name = "ccAddresses")
    private AddressListWrapper ccAddresses;

    @XmlElement(name = "toAddresses")
    private AddressListWrapper toAddresses;

    private int version = VERSION_NEW;
    private long id;
    private boolean includingStackTraceWhenJobFails;
    private String messageText;
    private String resultSendType;
    private boolean skipEmptyReports;
    private boolean skipNotificationWhenJobFails;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIncludingStackTraceWhenJobFails() {
        return includingStackTraceWhenJobFails;
    }

    public void setIncludingStackTraceWhenJobFails(boolean includingStackTraceWhenJobFails) {
        this.includingStackTraceWhenJobFails = includingStackTraceWhenJobFails;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isSkipEmptyReports() {
        return skipEmptyReports;
    }

    public void setSkipEmptyReports(boolean skipEmptyReports) {
        this.skipEmptyReports = skipEmptyReports;
    }

    public boolean isSkipNotificationWhenJobFails() {
        return skipNotificationWhenJobFails;
    }

    public void setSkipNotificationWhenJobFails(boolean skipNotificationWhenJobFails) {
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
}
