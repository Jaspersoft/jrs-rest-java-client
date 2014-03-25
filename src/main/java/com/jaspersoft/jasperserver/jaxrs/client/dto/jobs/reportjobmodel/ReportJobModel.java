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
 * Definition model of a report execution job. Model is used in search/ update only.
 *
 * <p>
 * A report job definition specifies wich report to execute and when,
 * what output to generate and where to send the output.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
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

    /*private boolean isCreationDateModified = false;
    private boolean isSourceModified = false;
    private boolean isTriggerModified = false;
    private boolean isMailNotificationModified = false;
    private boolean isAlertModified = false;
    private boolean isContentRespositoryDestinationModified = false;
    private boolean isDescriptionModified = false;
    private boolean isLabelModified = false;
    private boolean isBaseOutputFileNameModified = false;
    private boolean isOutputFormatsModified = false;
    private boolean isUsernameModified = false;
    private boolean isOutputLocaleModified = false;
    private boolean isRuntimeInformationModified = false;*/

//    private ReportJobStateModel runtimeInformation = null;

    @Override
    @XmlElement(name = "sourceModel")
	public ReportJobSourceModel getSource() {
        JobSource model = super.getSource();
        if (model == null) return null;
        if (model instanceof ReportJobSourceModel) return (ReportJobSourceModel) model;
        throw new JSClientException("Please use ReportJobSourceModel instead of JobSource in ReportJobModel class.");
	}

    @Override
	public void setSource(JobSource source) {
//       isSourceModified = true;
       super.setSource(source);
	}

    @Override
    @XmlElements({
            @XmlElement(name = "simpleTriggerModel", type = ReportJobSimpleTriggerModel.class),
            @XmlElement(name = "calendarTriggerModel", type = ReportJobCalendarTriggerModel.class)})
	public JobTrigger getTrigger() {
		JobTrigger model = super.getTrigger();
        if (model == null) return null;
        if ((model instanceof ReportJobSimpleTriggerModel) || (model instanceof ReportJobCalendarTriggerModel)) return model;
        else throw new JSClientException("Please useReportJobTriggerModel instead of JobTrigger in ReportJobModel class.");
	}

    @Override
	public void setTrigger(JobTrigger trigger) {
//        isTriggerModified = true;
        if (trigger == null) super.setTrigger(null);
		else if ((trigger instanceof ReportJobSimpleTriggerModel) || (trigger instanceof ReportJobCalendarTriggerModel))
            super.setTrigger(trigger);
        else {
            if ((trigger instanceof SimpleTrigger))
                throw new JSClientException("Please use ReportJobSimpleTriggerModel instead of ReportJobSimpleTrigger in ReportJobModel class.");
            else
                throw new JSClientException("Please use ReportJobCalendarTriggerModel instead of ReportJobCalendarTrigger in ReportJobModel class.");
        }
	}

    @Override
    @XmlElement(name = "alertModel")
    public ReportJobAlertModel getAlert() {
        JobAlert model = super.getAlert();
        if (model == null) return null;
        if (model instanceof ReportJobAlertModel) return (ReportJobAlertModel) model;
        throw new JSClientException("Please use ReportJobAlertModel instead of JobAlert in ReportJobModel class.");
    }

    @Override
	public void setAlert(JobAlert alert) {
		super.setAlert(alert);
//        isAlertModified = true;
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
	public void setMailNotification(MailNotification mailNotification) {
		super.setMailNotification(mailNotification);
//        isMailNotificationModified = true;
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
    public void setRepositoryDestination(RepositoryDestination contentRepositoryDestination) {
        super.setRepositoryDestination(contentRepositoryDestination);
//        isContentRespositoryDestinationModified = true;
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
    public void setId(Long id) {
        super.setId(id);
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
    public void setVersion(Long version) {
        super.setVersion(version);
    }

    /**
     * Sets a description for the job
     *
     * @param description the job description
     */
    public void setDescription(String description) {
//        isDescriptionModified = true;
        super.setDescription(description);
    }

    /**
     * Sets creation date for the job
     *
     * @param creationDate the job creation date
     * @since 4.7
     */
    public void setCreationDate(Timestamp creationDate) {
//        isCreationDateModified = true;
        super.setCreationDate(creationDate);
    }

    /**
     * Sets a mandatory short description for the report job.
     *
     * @param label the job label
     */
    public void setLabel(String label) {
//        isLabelModified = true;
        super.setLabel(label);
    }

    /**
     * Sets the base filename to be used for the report job output files.
     *
     * @param baseOutputFilename the job output base filename
     * @see #getBaseOutputFilename()
     */
    public void setBaseOutputFilename(String baseOutputFilename) {
//        isBaseOutputFileNameModified = true;
        super.setBaseOutputFilename(baseOutputFilename);
    }

    /**
     * Sets the list of output formats that will be generated by the job.
     *
     * @param outputFormats the set of output formats as
     * <code>java.lang.Byte</code> keys
     */
    public void setOutputFormatsSet(Set<OutputFormat> outputFormats) {
//        isOutputFormatsModified = true;
        super.setOutputFormats(outputFormats);
    }

    /**
     * Sets the owner of this job.
     *
     * <p>
     * This method should not be called by code that schedules jobs as the job
     * owner is automatically set when the job is saved, overwriting any existing
     * value.
     * </p>
     *
     * @param username the job owner
     */
    public void setUsername(String username) {
//        isUsernameModified = true;
        super.setUsername(username);
    }

    /**
     * Sets a locale to be used to execute the report.
     *
     * <p>
     * The report output will be localized according to the provided locale.
     * </p>
     *
     * @param outputLocale the locale code as in <code>java.util.Locale.toString()</code>
     */
    public void setOutputLocale(String outputLocale) {
//        isOutputLocaleModified = true;
        super.setOutputLocale(outputLocale);
    }

//    /**
//     * returns whether CreationDate has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isCreationDateModified() { return isCreationDateModified; }
//
//    /**
//     * returns whether source has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isSourceModified() { return isSourceModified; }
//
//    /**
//     * returns whether trigger has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isTriggerModified() { return isTriggerModified; }
//    /**
//     * returns whether mail notification has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isMailNotificationModified() { return isMailNotificationModified; }
//    /**
//     * returns whether alert has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isAlertModified() { return isAlertModified; }
//    /**
//     * returns whether ContentRespositoryDestination has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isContentRespositoryDestinationModified() { return isContentRespositoryDestinationModified; }
//    /**
//     * returns whether description has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isDescriptionModified() { return isDescriptionModified; }
//    /**
//     * returns whether label has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isLabelModified() { return isLabelModified; }
//    /**
//     * returns whether base output file name has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isBaseOutputFileNameModified() { return isBaseOutputFileNameModified; }
//    /**
//     * returns whether output formats has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOutputFormatsModified() { return isOutputFormatsModified; }
//    /**
//     * returns whether the user name has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isUsernameModified() { return isUsernameModified; }
//    /**
//     * returns whether output locale has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOutputLocaleModified() { return isOutputLocaleModified; }
//    /**
//     * returns whether runtime information has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isRuntimeInformationModified() { return isRuntimeInformationModified; }

}
