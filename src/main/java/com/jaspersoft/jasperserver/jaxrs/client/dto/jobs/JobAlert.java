package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.AddressListWrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportJobAlert")
public class JobAlert {

    private Long id;
    private Integer version;
    private String recipient;
    private String jobState;
    private String messageText;
    private String messageTextWhenJobFails;
    private String subject;
    private Boolean includingStackTrace;
    private Boolean includingReportJobInfo;

    @XmlElement(name = "toAddresses")
    private AddressListWrapper toAddresses;


    public AddressListWrapper getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(AddressListWrapper toAddresses) {
        this.toAddresses = toAddresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getJobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTextWhenJobFails() {
        return messageTextWhenJobFails;
    }

    public void setMessageTextWhenJobFails(String messageTextWhenJobFails) {
        this.messageTextWhenJobFails = messageTextWhenJobFails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean isIncludingStackTrace() {
        return includingStackTrace;
    }

    public void setIncludingStackTrace(Boolean includingStackTrace) {
        this.includingStackTrace = includingStackTrace;
    }

    public Boolean isIncludingReportJobInfo() {
        return includingReportJobInfo;
    }

    public void setIncludingReportJobInfo(Boolean includingReportJobInfo) {
        this.includingReportJobInfo = includingReportJobInfo;
    }

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
