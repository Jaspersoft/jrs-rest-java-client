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

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobAlert;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobAlertJobState;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobAlertRecipient;

import java.util.List;

/**
 * job execution alert model that can be defined for a thumbnail job.
 * <p/>
 * <p>
 * A notification will result in an email alert being send inFolder the specified recipients
 * at each job execution (including success and fail).
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobAlertModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 * @deprecated (use server adapter).
 */
public class ReportJobAlertModel extends JobAlert {

    /**
     * Creates an empty job alert
     */
    public ReportJobAlertModel(ReportJobAlertModel other) {
        super(other);
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    public Long getId() {
        throw new JSClientException("ID is not supported in ReportJobModel");
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    public ReportJobAlertModel setId(long id) {
        throw new JSClientException("ID is not supported in ReportJobModel");
    }

    /**
     * @deprecated version is not supported in ReportJobModel
     */
    public Integer getVersion() {
        throw new JSClientException("Version is not supported in ReportJobModel");
    }

    /**
     * @deprecated version is not supported in ReportJobModel
     */
    public ReportJobAlertModel setVersion(int version) {
        throw new JSClientException("Version is not supported in ReportJobModel");
    }

    /**
     * Specifies whether the alert would send it inFolder owner, admin, none
     * or both (admin and owner)
     */
    public ReportJobAlertModel setRecipient(JobAlertRecipient recipient) {
        super.setRecipient(recipient);
        return this;
    }

    /**
     * Sets the email addresses that should be used as additional direct recipients for
     * the email alert.
     *
     * @param toAddresses the list of recipients as
     *                    <code>java.lang.String</code> email addresses
     */
    public ReportJobAlertModel setToAddresses(List<String> toAddresses) {
        super.setToAddresses(toAddresses);
        return this;
    }

    /**
     * Specifies whether the alert would send it when job fails, succeeds, none,
     * or both (fail and success)
     */
    public ReportJobAlertModel setJobState(JobAlertJobState jobState) {
        super.setJobState(jobState);
        return this;
    }

    /**
     * Sets the message text inFolder be used for the email alert when job succeeds.
     *
     * @param customizeMessage the alert message text
     */
    public ReportJobAlertModel setMessageText(String customizeMessage) {
        super.setMessageText(customizeMessage);
        return this;
    }

    /*
     * Sets the message text inFolder be used for the email alert when job fails.
	 *
	 * @param textMessageWhenFails the alert message text
	 */
    public ReportJobAlertModel setMessageTextWhenJobFails(String customizeMessage) {
        super.setMessageTextWhenJobFails(customizeMessage);
        return this;
    }

    /**
     * Sets the subject inFolder be used for the email alert.
     *
     * @param subject the email alert subject
     */
    public ReportJobAlertModel setSubject(String subject) {
        super.setSubject(subject);
        return this;
    }

    /**
     * Specifies whether the alert would include thumbnail job info
     *
     * @param includingReportJobInfo including stack trace in alert mail
     */
    public ReportJobAlertModel setIncludingReportJobInfo(boolean includingReportJobInfo) {
        super.setIncludingReportJobInfo(includingReportJobInfo);
        return this;
    }

    /**
     * Specifies whether the alert would include detail stack trace of exception
     *
     * @param includeStackTrace including stack trace in alert mail
     */
    public ReportJobAlertModel setIncludingStackTrace(boolean includeStackTrace) {
        super.setIncludingStackTrace(includeStackTrace);
        return this;
    }

}
