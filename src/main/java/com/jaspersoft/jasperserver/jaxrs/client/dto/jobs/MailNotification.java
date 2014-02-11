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
}
