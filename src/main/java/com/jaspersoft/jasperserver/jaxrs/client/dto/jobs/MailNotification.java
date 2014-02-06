package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "mailNotification")
public class MailNotification {

    private static final int VERSION_NEW = -1;

    @XmlElementWrapper(name = "bccAddresses")
    @XmlElement(name = "address")
    private List<String> bccAddresses;

    @XmlElementWrapper(name = "ccAddresses")
    @XmlElement(name = "address")
    private List<String> ccAddresses;

    @XmlElementWrapper(name = "toAddresses")
    @XmlElement(name = "address")
    private List<String> toAddresses;

    private int version = VERSION_NEW;
    private long id;
    private boolean includingStackTraceWhenJobFails;
    private String messageText;
    private Byte resultSendType;
    private boolean skipEmptyReports;
    private boolean skipNotificationWhenJobFails;
    private String subject;



}
