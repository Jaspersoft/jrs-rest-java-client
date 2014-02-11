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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.List;

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
}
