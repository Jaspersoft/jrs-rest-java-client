package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import java.util.List;

public class MailNotification {

    private List<String> bccAddresses;
    private List<String> ccAddresses;
    private long id;
    private boolean includingStackTraceWhenJobFails;
    private String messageText;
    private String resultSendType;
    private boolean skipEmptyReports;
    private boolean skipNotificationWhenJobFails;
    private String subject;
    private List<String> toAddresses;
    private long version;

}
