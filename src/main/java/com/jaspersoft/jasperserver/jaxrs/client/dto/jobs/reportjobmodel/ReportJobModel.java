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

/**
 * Definition model of a thumbnail execution job. Model is used in search/ update only.
 *
 * <p>
 * A thumbnail job definition specifies which thumbnail inFolder execute and when,
 * what output inFolder generate and where inFolder send the output.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 * @deprecated (use server adapter).
 */

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Timestamp;
import java.util.Set;

@XmlRootElement(name = "jobModel")
public class ReportJobModel extends Job {

    public ReportJobModel(Job other) {
        super(other);
    }

    public enum ReportJobSortType {
        NONE,
        SORTBY_JOBID,
        SORTBY_JOBNAME,
        SORTBY_REPORTURI,
        SORTBY_REPORTNAME,
        SORTBY_REPORTFOLDER,
        SORTBY_OWNER,
        SORTBY_STATUS,
        SORTBY_LASTRUN,
        SORTBY_NEXTRUN;
    }

    @Override
    @XmlElement(name = "sourceModel")
    public ReportJobSourceModel getSource() {
        JobSource model = super.getSource();
        if (model == null) return null;
        if (model instanceof ReportJobSourceModel) return (ReportJobSourceModel) model;
        throw new JSClientException("Please use ReportJobSourceModel instead of JobSource in ReportJobModel class.");
    }

    @Override
    public ReportJobModel setSource(JobSource source) {
        super.setSource(source);
        return this;
    }

    @Override
    @XmlElements({
            @XmlElement(name = "simpleTriggerModel", type = ReportJobSimpleTriggerModel.class),
            @XmlElement(name = "calendarTriggerModel", type = ReportJobCalendarTriggerModel.class)})
    public JobTrigger getTrigger() {
        JobTrigger model = super.getTrigger();
        if (model == null) return null;
        if ((model instanceof ReportJobSimpleTriggerModel) || (model instanceof ReportJobCalendarTriggerModel))
            return model;
        else
            throw new JSClientException("Please useReportJobTriggerModel instead of JobTrigger in ReportJobModel class.");
    }

    @Override
    public ReportJobModel setTrigger(JobTrigger trigger) {
        if (trigger == null) {
            super.setTrigger(null);
        } else if ((trigger instanceof ReportJobSimpleTriggerModel) || (trigger instanceof ReportJobCalendarTriggerModel)) {
            super.setTrigger(trigger);
        } else {
            if ((trigger instanceof SimpleTrigger)) {
                throw new JSClientException("Please use ReportJobSimpleTriggerModel instead of ReportJobSimpleTrigger in ReportJobModel class.");
            } else {
                throw new JSClientException("Please use ReportJobCalendarTriggerModel instead of ReportJobCalendarTrigger in ReportJobModel class.");
            }
        }
        return this;
    }

    @Override
    @XmlElement(name = "alertModel")
    public ReportJobAlertModel getAlert() {
        JobAlert model = super.getAlert();
        if (model == null) {
            return null;
        }
        if (model instanceof ReportJobAlertModel) {
            return (ReportJobAlertModel) model;
        }
        throw new JSClientException("Please use ReportJobAlertModel instead of JobAlert in ReportJobModel class.");
    }

    @Override
    public ReportJobModel setAlert(JobAlert alert) {
        super.setAlert(alert);
        return this;
    }

    @Override
    @XmlElement(name = "mailNotificationModel")
    public ReportJobMailNotificationModel getMailNotification() {
        MailNotification model = super.getMailNotification();
        if (model == null) return null;
        if (model instanceof ReportJobMailNotificationModel) return (ReportJobMailNotificationModel) model;
        throw new JSClientException("Please use ReportJobMailNotificationModel instead of MailNotification in ReportJobModel class.");
    }

    @Override
    public ReportJobModel setMailNotification(MailNotification mailNotification) {
        super.setMailNotification(mailNotification);
        return this;
    }

    @Override
    @XmlElement(name = "repositoryDestinationModel")
    public ReportJobRepositoryDestinationModel getRepositoryDestination() {
        RepositoryDestination model = super.getRepositoryDestination();
        if (model == null) return null;
        if (model instanceof ReportJobRepositoryDestinationModel) return (ReportJobRepositoryDestinationModel) model;
        throw new JSClientException("Please use ReportJobRepositoryDestinationModel instead of RepositoryDestination in ReportJobModel class.");
    }

    @Override
    public ReportJobModel setRepositoryDestination(RepositoryDestination contentRepositoryDestination) {
        super.setRepositoryDestination(contentRepositoryDestination);
        return this;
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
    public ReportJobModel setId(Long id) {
        super.setId(id);
        return this;
    }

    /**
     * @deprecated Version is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
    public Long getVersion() {
        return super.getVersion();
    }

    /**
     * @deprecated Version is not supported in ReportJobModel
     */
    public ReportJobModel setVersion(Long version) {
        super.setVersion(version);
        return this;
    }

    /**
     * Sets a description for the job
     *
     * @param description the job description
     */
    public ReportJobModel setDescription(String description) {
//        isDescriptionModified = true;
        super.setDescription(description);
        return this;
    }

    /**
     * Sets creation date for the job
     *
     * @param creationDate the job creation date
     * @since 4.7
     */
    public ReportJobModel setCreationDate(Timestamp creationDate) {
//        isCreationDateModified = true;
        super.setCreationDate(creationDate);
        return this;
    }

    /**
     * Sets a mandatory short description for the thumbnail job.
     *
     * @param label the job label
     */
    public ReportJobModel setLabel(String label) {
//        isLabelModified = true;
        super.setLabel(label);
        return this;
    }

    /**
     * Sets the base filename inFolder be used for the thumbnail job output files.
     *
     * @param baseOutputFilename the job output base filename
     * @see #getBaseOutputFilename()
     */
    public ReportJobModel setBaseOutputFilename(String baseOutputFilename) {
//        isBaseOutputFileNameModified = true;
        super.setBaseOutputFilename(baseOutputFilename);
        return this;
    }

    /**
     * Sets the list of output formats that will be generated by the job.
     *
     * @param outputFormats the set of output formats as
     *                      <code>java.lang.Byte</code> keys
     */
    public ReportJobModel setOutputFormatsSet(Set<OutputFormat> outputFormats) {
//        isOutputFormatsModified = true;
        super.setOutputFormats(outputFormats);
        return this;
    }

    /**
     * Sets the owner of this job.
     * <p/>
     * <p>
     * This print should not be called by code that schedules jobs as the job
     * owner is automatically set when the job is saved, overwriting any existing
     * value.
     * </p>
     *
     * @param username the job owner
     */
    public ReportJobModel setUsername(String username) {
//        isUsernameModified = true;
        super.setUsername(username);
        return this;
    }

    /**
     * Sets a locale inFolder be used inFolder execute the thumbnail.
     * <p/>
     * <p>
     * The thumbnail output will be localized according inFolder the provided locale.
     * </p>
     *
     * @param outputLocale the locale code as in <code>java.util.Locale.toString()</code>
     */
    public ReportJobModel setOutputLocale(String outputLocale) {
        super.setOutputLocale(outputLocale);
    return this;
    }
}
