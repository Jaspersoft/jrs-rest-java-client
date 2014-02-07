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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "repositoryDestination")
public class RepositoryDestination {

    private String folderURI;
    private long id;
    private String outputDescription;
    private boolean overwriteFiles;
    private boolean sequentialFilenames;
    private long version;
    private String timestampPattern;
    private boolean saveToRepository;
    private String defaultReportOutputFolderURI;
    private boolean usingDefaultReportOutputFolderURI;
    private String outputLocalFolder;
    private FtpInfo outputFTPInfo;

    public String getFolderURI() {
        return folderURI;
    }

    public void setFolderURI(String folderURI) {
        this.folderURI = folderURI;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription;
    }

    public boolean isOverwriteFiles() {
        return overwriteFiles;
    }

    public void setOverwriteFiles(boolean overwriteFiles) {
        this.overwriteFiles = overwriteFiles;
    }

    public boolean isSequentialFilenames() {
        return sequentialFilenames;
    }

    public void setSequentialFilenames(boolean sequentialFilenames) {
        this.sequentialFilenames = sequentialFilenames;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getTimestampPattern() {
        return timestampPattern;
    }

    public void setTimestampPattern(String timestampPattern) {
        this.timestampPattern = timestampPattern;
    }

    public boolean isSaveToRepository() {
        return saveToRepository;
    }

    public void setSaveToRepository(boolean saveToRepository) {
        this.saveToRepository = saveToRepository;
    }

    public String getDefaultReportOutputFolderURI() {
        return defaultReportOutputFolderURI;
    }

    public void setDefaultReportOutputFolderURI(String defaultReportOutputFolderURI) {
        this.defaultReportOutputFolderURI = defaultReportOutputFolderURI;
    }

    public boolean isUsingDefaultReportOutputFolderURI() {
        return usingDefaultReportOutputFolderURI;
    }

    public void setUsingDefaultReportOutputFolderURI(boolean usingDefaultReportOutputFolderURI) {
        this.usingDefaultReportOutputFolderURI = usingDefaultReportOutputFolderURI;
    }

    public String getOutputLocalFolder() {
        return outputLocalFolder;
    }

    public void setOutputLocalFolder(String outputLocalFolder) {
        this.outputLocalFolder = outputLocalFolder;
    }

    public FtpInfo getOutputFTPInfo() {
        return outputFTPInfo;
    }

    public void setOutputFTPInfo(FtpInfo outputFTPInfo) {
        this.outputFTPInfo = outputFTPInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoryDestination that = (RepositoryDestination) o;

        if (id != that.id) return false;
        if (overwriteFiles != that.overwriteFiles) return false;
        if (saveToRepository != that.saveToRepository) return false;
        if (sequentialFilenames != that.sequentialFilenames) return false;
        if (usingDefaultReportOutputFolderURI != that.usingDefaultReportOutputFolderURI) return false;
        if (version != that.version) return false;
        if (defaultReportOutputFolderURI != null ? !defaultReportOutputFolderURI.equals(that.defaultReportOutputFolderURI) : that.defaultReportOutputFolderURI != null)
            return false;
        if (folderURI != null ? !folderURI.equals(that.folderURI) : that.folderURI != null) return false;
        if (outputDescription != null ? !outputDescription.equals(that.outputDescription) : that.outputDescription != null)
            return false;
        if (outputFTPInfo != null ? !outputFTPInfo.equals(that.outputFTPInfo) : that.outputFTPInfo != null)
            return false;
        if (outputLocalFolder != null ? !outputLocalFolder.equals(that.outputLocalFolder) : that.outputLocalFolder != null)
            return false;
        if (timestampPattern != null ? !timestampPattern.equals(that.timestampPattern) : that.timestampPattern != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = folderURI != null ? folderURI.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (outputDescription != null ? outputDescription.hashCode() : 0);
        result = 31 * result + (overwriteFiles ? 1 : 0);
        result = 31 * result + (sequentialFilenames ? 1 : 0);
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + (timestampPattern != null ? timestampPattern.hashCode() : 0);
        result = 31 * result + (saveToRepository ? 1 : 0);
        result = 31 * result + (defaultReportOutputFolderURI != null ? defaultReportOutputFolderURI.hashCode() : 0);
        result = 31 * result + (usingDefaultReportOutputFolderURI ? 1 : 0);
        result = 31 * result + (outputLocalFolder != null ? outputLocalFolder.hashCode() : 0);
        result = 31 * result + (outputFTPInfo != null ? outputFTPInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RepositoryDestination{" +
                "folderUri='" + folderURI + '\'' +
                ", id=" + id +
                ", outputDescription='" + outputDescription + '\'' +
                ", overwriteFiles=" + overwriteFiles +
                ", sequentialFilenames=" + sequentialFilenames +
                ", version=" + version +
                ", timestampPattern='" + timestampPattern + '\'' +
                ", saveToRepository=" + saveToRepository +
                ", defaultReportOutputFolderURI='" + defaultReportOutputFolderURI + '\'' +
                ", usingDefaultReportOutputFolderURI=" + usingDefaultReportOutputFolderURI +
                ", outputLocalFolder='" + outputLocalFolder + '\'' +
                ", outputFTPInfo=" + outputFTPInfo +
                '}';
    }
}
