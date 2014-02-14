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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.OutputFormatsListWrapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public class Job {

    private Long id;
    private Long version;
    private String username;
    private String label;
    private String description;
    private String creationDate;
    private String baseOutputFilename;
    private String outputLocale;


    @XmlElement(name = "repositoryDestination")
    private RepositoryDestination repositoryDestination;

    @XmlElement(name = "mailNotification")
    private MailNotification mailNotification;

    @XmlElement(name = "source")
    private JobSource source;

    @XmlElement(name = "alert")
    private JobAlert alert;

    @XmlElement(name = "outputFormats")
    private OutputFormatsListWrapper outputFormats;

    @XmlElements({
            @XmlElement(name = "simpleTrigger", type = SimpleTrigger.class),
            @XmlElement(name = "calendarTrigger", type = CalendarTrigger.class)})
    private JobTrigger trigger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBaseOutputFilename() {
        return baseOutputFilename;
    }

    public void setBaseOutputFilename(String baseOutputFilename) {
        this.baseOutputFilename = baseOutputFilename;
    }

    public String getOutputLocale() {
        return outputLocale;
    }

    public void setOutputLocale(String outputLocale) {
        this.outputLocale = outputLocale;
    }

    public RepositoryDestination getRepositoryDestination() {
        return repositoryDestination;
    }

    public void setRepositoryDestination(RepositoryDestination repositoryDestination) {
        this.repositoryDestination = repositoryDestination;
    }

    public MailNotification getMailNotification() {
        return mailNotification;
    }

    public void setMailNotification(MailNotification mailNotification) {
        this.mailNotification = mailNotification;
    }

    public JobSource getSource() {
        return source;
    }

    public void setSource(JobSource source) {
        this.source = source;
    }

    public JobAlert getAlert() {
        return alert;
    }

    public void setAlert(JobAlert alert) {
        this.alert = alert;
    }

    public JobTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(JobTrigger trigger) {
        this.trigger = trigger;
    }

    public OutputFormatsListWrapper getOutputFormats() {
        return outputFormats;
    }

    public void setOutputFormats(OutputFormatsListWrapper outputFormats) {
        this.outputFormats = outputFormats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (alert != null ? !alert.equals(job.alert) : job.alert != null) return false;
        if (baseOutputFilename != null ? !baseOutputFilename.equals(job.baseOutputFilename) : job.baseOutputFilename != null)
            return false;
        if (creationDate != null ? !creationDate.equals(job.creationDate) : job.creationDate != null) return false;
        if (description != null ? !description.equals(job.description) : job.description != null) return false;
        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (label != null ? !label.equals(job.label) : job.label != null) return false;
        if (mailNotification != null ? !mailNotification.equals(job.mailNotification) : job.mailNotification != null)
            return false;
        if (outputFormats != null ? !outputFormats.equals(job.outputFormats) : job.outputFormats != null) return false;
        if (outputLocale != null ? !outputLocale.equals(job.outputLocale) : job.outputLocale != null) return false;
        if (repositoryDestination != null ? !repositoryDestination.equals(job.repositoryDestination) : job.repositoryDestination != null)
            return false;
        if (source != null ? !source.equals(job.source) : job.source != null) return false;
        if (trigger != null ? !trigger.equals(job.trigger) : job.trigger != null) return false;
        if (username != null ? !username.equals(job.username) : job.username != null) return false;
        if (version != null ? !version.equals(job.version) : job.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (baseOutputFilename != null ? baseOutputFilename.hashCode() : 0);
        result = 31 * result + (outputLocale != null ? outputLocale.hashCode() : 0);
        result = 31 * result + (repositoryDestination != null ? repositoryDestination.hashCode() : 0);
        result = 31 * result + (mailNotification != null ? mailNotification.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (outputFormats != null ? outputFormats.hashCode() : 0);
        result = 31 * result + (trigger != null ? trigger.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", version=" + version +
                ", username='" + username + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", baseOutputFilename='" + baseOutputFilename + '\'' +
                ", outputLocale='" + outputLocale + '\'' +
                ", repositoryDestination=" + repositoryDestination +
                ", mailNotification=" + mailNotification +
                ", source=" + source +
                ", alert=" + alert +
                ", outputFormats=" + outputFormats +
                ", trigger=" + trigger +
                '}';
    }
}
