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
 * Contains attributes related to the generation of repository resources
 * for report job output files.
 * Model is used in search/ update only.
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobRepositoryDestinationModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 */
public class ReportJobRepositoryDestinationModel extends RepositoryDestination {

    /*private boolean isFolderURIModified = false;
    private boolean isSequentialFilenamesModified = false;
    private boolean isOverwriteFilesModified = false;
    private boolean isOutputDescriptionModified = false;
    private boolean isTimestampPatternModified = false;
    private boolean isSaveToRepositoryModified = false;
    private boolean isDefaultReportOutputFolderURIModified = false;
    private boolean isUsingDefaultReportOutputFolderURIModified = false;
    private boolean isOutputFTPInfoModified = false;
    private boolean isOutputLocalFolderModified = false;*/


    /**
	 * Creates an empty object.
	 */
	public ReportJobRepositoryDestinationModel() {
	}

	/**
	 * Returns the repository URI/path of the folder under which job output
	 * resources are to be be created.
	 *
	 * <p>
	 * The job owner should have write permission on the output folder.
	 * </p>
	 *
	 * @param folder the URI/path of the repository output folder
	 * @see ReportJobModel#setBaseOutputFilename(String)
	 */
	public void setFolderURI(String folder) {
//        isFolderURIModified = true;
		super.setFolderURI(folder);
	}

	/**
	 * Specifies whether a timestamp is to be added to the names of the job
	 * output resources.
	 *
	 * <p>
	 * This is usually required when a job occurs severa time and the output
	 * from each execution needs to be kept in the repository.
	 * </p>
	 *
	 * @param sequentialFilenames <code>true</code> if the job output resources
	 * names should include a timestamp
	 */
	public void setSequentialFilenames(boolean sequentialFilenames) {
//		isSequentialFilenamesModified = true;
        super.setSequentialFilenames(sequentialFilenames);
	}

	/**
	 * Specifies whether the scheduler should overwrite files in the repository
	 * when saving job output resources.
	 *
	 * @param overwriteFiles
	 * @see #isOverwriteFiles()
	 */
	public void setOverwriteFiles(boolean overwriteFiles) {
//		isOverwriteFilesModified = true;
        super.setOverwriteFiles(overwriteFiles);
	}

	/**
	 * Sets the description that should be used for job output resources.
	 *
	 * @param outputDescription the job output resources description
	 * @since 3.0
	 */
	public void setOutputDescription(String outputDescription) {
//		isOutputDescriptionModified = true;
        super.setOutputDescription(outputDescription);
	}

	/**
	 * Sets a date pattern to be used for the timestamp included in job output
	 * resources names.
	 *
	 * <p>
	 * The pattern should be a valid pattern as defined by
	 * <code>java.text.SimpleDateFormat</code> and can only contain underscores,
	 * dots and dashes as token separators.
	 * </p>
	 *
	 * @param timestampPattern
	 * @since 3.0
	 * @see #setSequentialFilenames(boolean)
	 */
	public void setTimestampPattern(String timestampPattern) {
//		isTimestampPatternModified = true;
        super.setTimestampPattern(timestampPattern);
	}

    /**
	 * Specifies whether the scheduler should write files to the repository
	 *
	 * @param saveToRepository
	 * @see #isSaveToRepository()
	 */
	public void setSaveToRepository(boolean saveToRepository) {
//        isSaveToRepositoryModified = true;
		super.setSaveToRepository(saveToRepository);
	}

     /**
     * Sets the default scheduled report output folder URI of the job owner
     *
     * @param  defaultReportOutputFolderURI default scheduled report output folder URI of the job owner
     * @see #getDefaultReportOutputFolderURI()
     * @since 4.7
     */
	public void setDefaultReportOutputFolderURI(String defaultReportOutputFolderURI) {
//        isDefaultReportOutputFolderURIModified = true;
        super.setDefaultReportOutputFolderURI(defaultReportOutputFolderURI);
    }

	/**
	 * Specifies whether the scheduler should write files to default report output folder URI of the job owner
	 *
	 * @param usingDefaultReportOutputFolderURI
	 * @see #isUsingDefaultReportOutputFolderURI()
     * @since 4.7
	 */
	public void setUsingDefaultReportOutputFolderURI(boolean usingDefaultReportOutputFolderURI) {
//        isUsingDefaultReportOutputFolderURIModified = true;
		super.setUsingDefaultReportOutputFolderURI(usingDefaultReportOutputFolderURI);
	}

	/**
	 * Returns the output local path of the folder under which job output
	 * resources are to be be created.
	 *
	 * @param outputLocalFolder the path of the local output folder
	 */
    public void setOutputLocalFolder(String outputLocalFolder) {
//        isOutputLocalFolderModified = true;
        super.setOutputLocalFolder(outputLocalFolder);
    }

    /**
	 * Returns the output FTP location information which job output
	 * resources are to be be created.
	 *
	 * @return the output FTP Information
	 * @see #setOutputFTPInfoModel(FTPInfoModel)
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
	 * @see #setOutputFTPInfoModel(FTPInfoModel)
	 */
	public FTPInfoModel getOutputFTPInfoModel() {
        FtpInfo model = super.getOutputFTPInfo();
        if (model == null) return null;
        if (model instanceof FTPInfoModel) return (FTPInfoModel) model;
        throw new JSClientException("Please use FTPInfoModel instead of FTPInfo in ReportJobRepositoryDestinationModel class.");
	}

	/**
	 * Returns the output FTP location information which job output
	 * resources are to be be created.
	 *
	 * @param ftpInfo FTP information of the output folder
     * @deprecated use #setOutputFTPInfo(FTPInfoModel) instead
	 */
	public void setOutputFTPInfo(FtpInfo ftpInfo) {
        if (ftpInfo == null) setOutputFTPInfoModel(null);
		else if (ftpInfo instanceof FTPInfoModel) setOutputFTPInfoModel((FTPInfoModel)ftpInfo);
        else throw new JSClientException("Please use FTPInfoModel instead of FTPInfo in ReportJobRepositoryDestinationModel class.");
	}

    /**
	 * Returns the output FTP location information which job output
	 * resources are to be be created.
	 *
	 * @param outputFTPInfo FTP information of the output folder
	 */
    public void setOutputFTPInfoModel(FTPInfoModel outputFTPInfo) {
//        isOutputFTPInfoModified = true;
        super.setOutputFTPInfo(outputFTPInfo);
    }

//    /**
//     * returns whether FolderURI has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isFolderURIModified() { return isFolderURIModified; }
//
//    /**
//     * returns whether SequentialFilenames has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isSequentialFilenamesModified() { return isSequentialFilenamesModified; }
//
//    /**
//     * returns whether OutputDescription has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOutputDescriptionsModified() { return isOutputDescriptionModified; }
//
//    /**
//     * returns whether TimestampPattern has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isTimestampPatternModified() { return isTimestampPatternModified; }
//
//    /**
//     * returns whether OverwriteFiles has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOverwriteFilesModified() { return isOverwriteFilesModified; }
//
//    /**
//     * returns whether SaveToRepository has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isSaveToRepositoryModified() { return isSaveToRepositoryModified; }
//
//    /**
//     * returns whether DefaultReportOutputFolderURI has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isDefaultReportOutputFolderURIModified() { return isDefaultReportOutputFolderURIModified; }
//
//    /**
//     * returns whether UsingDefaultReportOutputFolderURI has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isUsingDefaultReportOutputFolderURIModified() { return isUsingDefaultReportOutputFolderURIModified; }
//
//    /**
//     * returns whether OutputLocalFolder has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOutputLocalFolderModified() { return isOutputLocalFolderModified; }
//
//    /**
//     * returns whether OutputFTPInfo has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOutputFTPInfoModified() { return isOutputFTPInfoModified; }
}
