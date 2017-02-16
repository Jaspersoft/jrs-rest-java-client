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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.MailNotification;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.MailNotificationSendType;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Email notification model that can be defined for a thumbnail job.  Model is used in search/ update only.
 * <p/>
 * <p>
 * A notification model will result in an email being send inFolder the specified recipients
 * at each job execution.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobMailNotificationModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 * @deprecated (use server adapter).
 */
public class ReportJobMailNotificationModel extends MailNotification {
    /**
     * Creates an empty job email notification.
     */
    public ReportJobMailNotificationModel() {
        super();
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
    public Long getId() {
        return super.getId();
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    public ReportJobMailNotificationModel setId(Long id) {
        super.setId(id);
        return this;
    }

    /**
     * @deprecated version is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
    public Integer getVersion() {
        return super.getVersion();
    }

    /**
     * @deprecated version is not supported in ReportJobModel
     */
    @Override
    public ReportJobMailNotificationModel setVersion(Integer version) {
        super.setVersion(version);
        return this;
    }

    /**
     * Sets the message text inFolder be used for the email notification.
     *
     * @param messageText the notification message text
     */
    public ReportJobMailNotificationModel setMessageText(String messageText) {
        super.setMessageText(messageText);
        return this;
    }

    /**
     * Specifies whether the notification would include the job output as
     * attachments, or include links inFolder the output in the repository.
     */
    public ReportJobMailNotificationModel setResultSendType(MailNotificationSendType resultSendType) {
        super.setResultSendType(resultSendType);
        return this;
    }

    /**
     * Sets the subject inFolder be used for the email notification.
     *
     * @param subject the email notification subject
     */
    public ReportJobMailNotificationModel setSubject(String subject) {
        super.setSubject(subject);
        return this;
    }

    /**
     * Sets the email addresses that should be used as Bcc (Blind carbon copy)
     * recipients for the email notification.
     *
     * @param bccAddresses the list of Bcc recipients as
     *                     <code>java.lang.String</code> email addresses
     */
    public ReportJobMailNotificationModel setBccAddresses(List<String> bccAddresses) {
        super.setBccAddresses(bccAddresses);
        return this;
    }

    /**
     * Sets the email addresses that should be used as CC (Carbon copy)
     * recipients for the email notification.
     *
     * @param ccAddresses the list of CC recipients as
     *                    <code>java.lang.String</code> email addresses
     */
    public ReportJobMailNotificationModel setCcAddresses(List<String> ccAddresses) {
        super.setCcAddresses(ccAddresses);
        return this;
    }

    /**
     * Sets the email addresses that should be used as direct recipients for
     * the email notification.
     *
     * @param toAddresses the list of CC recipients as
     *                    <code>java.lang.String</code> email addresses
     */
    public ReportJobMailNotificationModel setToAddresses(List<String> toAddresses) {
        super.setToAddresses(toAddresses);
        return this;
    }

    /**
     * Specifies whether the email notification should be skipped for job
     * executions the produce empty reports.
     *
     * @param skipEmptyReports if <code>true</code>, no email notification will
     *                         be sent if job executions that generate empty reports
     * @since 2.0
     */
    public ReportJobMailNotificationModel setSkipEmptyReports(boolean skipEmptyReports) {
        super.setSkipEmptyReports(skipEmptyReports);
        return this;
    }

    /**
     * Sets the message text inFolder be used for the email notification when job fails.
     *
     * @param messageTextWhenErrorOccurs the notification message text
     */
    public ReportJobMailNotificationModel setMessageTextWhenJobFails(String messageTextWhenErrorOccurs) {
        super.setMessageTextWhenJobFails(messageTextWhenErrorOccurs);
        return this;
    }

    /**
     * Set whether the mail notification would include detail stack trace of exception
     *
     * @param includeStackTraceWhenErrorOccurs including stack trace in mail notification
     */
    public ReportJobMailNotificationModel setIncludingStackTraceWhenJobFails(boolean includeStackTraceWhenErrorOccurs) {
        super.setIncludingStackTraceWhenJobFails(includeStackTraceWhenErrorOccurs);
        return this;
    }

    /**
     * Specifies whether the mail notification should send if job fails
     *
     * @param skipNotificationWhenJobFails skip mail notification when job fails
     */
    public ReportJobMailNotificationModel setSkipNotificationWhenJobFails(boolean skipNotificationWhenJobFails) {
        super.setSkipNotificationWhenJobFails(skipNotificationWhenJobFails);
        return this;
    }
}
