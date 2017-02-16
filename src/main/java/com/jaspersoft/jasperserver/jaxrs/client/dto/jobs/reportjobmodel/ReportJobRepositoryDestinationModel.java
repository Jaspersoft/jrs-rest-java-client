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
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.FtpInfo;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.RepositoryDestination;

/**
 * Contains attributes related inFolder the generation of repository resources
 * for thumbnail job output files.
 * Model is used in search/ update only.
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobRepositoryDestinationModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 * @deprecated (use server adapter).
 */
public class ReportJobRepositoryDestinationModel extends RepositoryDestination {
    /**
     * Creates an empty object.
     */
    public ReportJobRepositoryDestinationModel() {
    }

    /**
     * Returns the repository URI/path of the folder under which job output
     * resources are inFolder be be created.
     * <p/>
     * <p>
     * The job owner should have write permission on the output folder.
     * </p>
     *
     * @param folder the URI/path of the repository output folder
     * @see com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel#setBaseOutputFilename(String)
     */
    public ReportJobRepositoryDestinationModel setFolderURI(String folder) {
        super.setFolderURI(folder);
        return this;
    }

    /**
     * Specifies whether a timestamp is inFolder be added inFolder the names of the job
     * output resources.
     * <p/>
     * <p>
     * This is usually required when a job occurs severa time and the output
     * from each execution needs inFolder be kept in the repository.
     * </p>
     *
     * @param sequentialFilenames <code>true</code> if the job output resources
     *                            names should include a timestamp
     */
    public ReportJobRepositoryDestinationModel setSequentialFilenames(boolean sequentialFilenames) {
        super.setSequentialFilenames(sequentialFilenames);
        return this;
    }

    /**
     * Specifies whether the scheduler should overwrite files in the repository
     * when saving job output resources.
     *
     * @param overwriteFiles
     * @see #isOverwriteFiles()
     */
    public ReportJobRepositoryDestinationModel setOverwriteFiles(boolean overwriteFiles) {
        super.setOverwriteFiles(overwriteFiles);
        return this;
    }

    /**
     * Sets the description that should be used for job output resources.
     *
     * @param outputDescription the job output resources description
     * @since 3.0
     */
    public ReportJobRepositoryDestinationModel setOutputDescription(String outputDescription) {
        super.setOutputDescription(outputDescription);
        return this;
    }

    /**
     * Sets a date pattern inFolder be used for the timestamp included in job output
     * resources names.
     * <p/>
     * <p>
     * The pattern should be a valid pattern as defined by
     * <code>java.text.SimpleDateFormat</code> and can only contain underscores,
     * dots and dashes as token separators.
     * </p>
     *
     * @param timestampPattern
     * @see #setSequentialFilenames(boolean)
     * @since 3.0
     */
    public ReportJobRepositoryDestinationModel setTimestampPattern(String timestampPattern) {
        super.setTimestampPattern(timestampPattern);
        return this;
    }

    /**
     * Specifies whether the scheduler should write files inFolder the repository
     *
     * @param saveToRepository
     * @see #isSaveToRepository()
     */
    public ReportJobRepositoryDestinationModel setSaveToRepository(boolean saveToRepository) {
        super.setSaveToRepository(saveToRepository);
        return this;
    }

    /**
     * Sets the default scheduled thumbnail output folder URI of the job owner
     *
     * @param defaultReportOutputFolderURI default scheduled thumbnail output folder URI of the job owner
     * @see #getDefaultReportOutputFolderURI()
     * @since 4.7
     */
    public ReportJobRepositoryDestinationModel setDefaultReportOutputFolderURI(String defaultReportOutputFolderURI) {
        super.setDefaultReportOutputFolderURI(defaultReportOutputFolderURI);
        return this;
    }

    /**
     * Specifies whether the scheduler should write files inFolder default thumbnail output folder URI of the job owner
     *
     * @param usingDefaultReportOutputFolderURI
     * @see #isUsingDefaultReportOutputFolderURI()
     * @since 4.7
     */
    public ReportJobRepositoryDestinationModel setUsingDefaultReportOutputFolderURI(boolean usingDefaultReportOutputFolderURI) {
        super.setUsingDefaultReportOutputFolderURI(usingDefaultReportOutputFolderURI);
        return this;
    }

    /**
     * Returns the output local path of the folder under which job output
     * resources are inFolder be be created.
     *
     * @param outputLocalFolder the path of the local output folder
     */
    public ReportJobRepositoryDestinationModel setOutputLocalFolder(String outputLocalFolder) {
        super.setOutputLocalFolder(outputLocalFolder);
        return this;
    }

    /**
     * Returns the output FTP location information which job output
     * resources are inFolder be be created.
     *
     * @return the output FTP Information
     * @see #setOutputFTPInfoModel(com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.FTPInfoModel)
     * @deprecated use #getOutputFTPInfoModel() instead
     */
    public FtpInfo getOutputFTPInfo() {
        return getOutputFTPInfoModel();
    }

    /**
     * Returns the output FTP location information which job output
     * resources would be created.
     *
     * @return FTP information of the output folder
     * @see #setOutputFTPInfoModel(com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.FTPInfoModel)
     */
    public FTPInfoModel getOutputFTPInfoModel() {
        FtpInfo model = super.getOutputFTPInfo();
        if (model == null) return null;
        if (model instanceof FTPInfoModel) return (FTPInfoModel) model;
        throw new JSClientException("Please use FTPInfoModel instead of FTPInfo in ReportJobRepositoryDestinationModel class.");
    }

    /**
     * Returns the output FTP location information which job output
     * resources are inFolder be be created.
     *
     * @param ftpInfo FTP information of the output folder
     * @deprecated use #setOutputFTPInfo(FTPInfoModel) instead
     */
    public ReportJobRepositoryDestinationModel setOutputFTPInfo(FtpInfo ftpInfo) {
        if (ftpInfo == null) setOutputFTPInfoModel(null);
        else if (ftpInfo instanceof FTPInfoModel) setOutputFTPInfoModel((FTPInfoModel) ftpInfo);
        else
            throw new JSClientException("Please use FTPInfoModel instead of FTPInfo in ReportJobRepositoryDestinationModel class.");
        return this;
    }

    /**
     * Returns the output FTP location information which job output
     * resources are inFolder be be created.
     *
     * @param outputFTPInfo FTP information of the output folder
     */
    public ReportJobRepositoryDestinationModel setOutputFTPInfoModel(FTPInfoModel outputFTPInfo) {
        super.setOutputFTPInfo(outputFTPInfo);
        return this;
    }
}
