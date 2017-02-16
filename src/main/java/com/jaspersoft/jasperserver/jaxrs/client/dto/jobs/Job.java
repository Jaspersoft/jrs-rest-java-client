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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.OutputFormatXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.TimestampToStringXmlAdapter;

import java.util.HashSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.util.Set;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "job")
public class Job {

    private Long id;
    private Long version;
    private String username;
    private String label;
    private String description;
    private Timestamp creationDate;
    private String baseOutputFilename;
    private String outputLocale;
    private RepositoryDestination repositoryDestination;
    private MailNotification mailNotification;
    private JobSource source;
    private JobAlert alert;
    private Set<OutputFormat> outputFormats;
    private JobTrigger trigger;
    private String outputTimeZone;

    public Job() {
    }

    public Job(Job other) {
        this.alert = (other.alert != null) ? new JobAlert(other.alert) : null;
        this.baseOutputFilename = other.baseOutputFilename;
        this.description = other.description;
        this.id = other.id;
        this.label = other.label;
        this.mailNotification = (other.mailNotification != null) ? new MailNotification(other.mailNotification) : null;
        if (other.outputFormats != null) {
            this.outputFormats = new HashSet<OutputFormat>();
            for (OutputFormat outputFormat : other.outputFormats) {
                this.outputFormats.add(outputFormat);
            }
        }
        this.outputLocale = other.outputLocale;
        this.outputTimeZone = other.outputTimeZone;
        this.repositoryDestination = (other.repositoryDestination != null) ?
                new RepositoryDestination(other.repositoryDestination) : null;
        this.source = (other.source != null) ? new JobSource(other.source) : null;
        this.username = other.username;
        this.version = other.version;
        this.creationDate = new Timestamp(other.creationDate.getTime());
        this.trigger = other.trigger.deepClone();
    }

    public Long getId() {
        return id;
    }

    public Job setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Job setVersion(Long version) {
        this.version = version;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Job setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Job setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Job setDescription(String description) {
        this.description = description;
        return this;
    }

    @XmlJavaTypeAdapter(TimestampToStringXmlAdapter.class)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Job setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getBaseOutputFilename() {
        return baseOutputFilename;
    }

    public Job setBaseOutputFilename(String baseOutputFilename) {
        this.baseOutputFilename = baseOutputFilename;
        return this;
    }

    public String getOutputLocale() {
        return outputLocale;
    }

    public Job setOutputLocale(String outputLocale) {
        this.outputLocale = outputLocale;
        return this;
    }

    @XmlElement(name = "repositoryDestination")
    public RepositoryDestination getRepositoryDestination() {
        return repositoryDestination;
    }

    public Job setRepositoryDestination(RepositoryDestination repositoryDestination) {
        this.repositoryDestination = repositoryDestination;
        return this;
    }

    @XmlElement(name = "mailNotification")
    public MailNotification getMailNotification() {
        return mailNotification;
    }

    public Job setMailNotification(MailNotification mailNotification) {
        this.mailNotification = mailNotification;
        return this;
    }

    @XmlElement(name = "source")
    public JobSource getSource() {
        return source;
    }

    public Job setSource(JobSource source) {
        this.source = source;
        return this;
    }

    @XmlElement(name = "alert")
    public JobAlert getAlert() {
        return alert;
    }

    public Job setAlert(JobAlert alert) {
        this.alert = alert;
        return this;
    }

    @XmlElements({
            @XmlElement(name = "simpleTrigger", type = SimpleTrigger.class),
            @XmlElement(name = "calendarTrigger", type = CalendarTrigger.class)})
    public JobTrigger getTrigger() {
        return trigger;
    }

    public Job setTrigger(JobTrigger trigger) {
        this.trigger = trigger;
        return this;
    }

    @XmlElement(name = "outputFormats")
    @XmlJavaTypeAdapter(OutputFormatXmlAdapter.class)
    public Set<OutputFormat> getOutputFormats() {
        return outputFormats;
    }

    public Job setOutputFormats(Set<OutputFormat> outputFormats) {
        this.outputFormats = outputFormats;
        return this;
    }

    public String getOutputTimeZone() {
        return outputTimeZone;
    }

    public Job setOutputTimeZone(String outputTimeZone) {
        this.outputTimeZone = outputTimeZone;
        return this;
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
