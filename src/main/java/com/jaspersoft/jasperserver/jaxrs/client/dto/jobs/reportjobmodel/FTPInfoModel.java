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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.FtpInfo;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.FtpType;

import java.util.Map;

/**
 * Holder for FTP information
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: FTPInfoModel.java 38348 2013-09-30 04:57:18Z carbiv $
 * @since 1.0
 */
public class FTPInfoModel extends FtpInfo {

//    private boolean isUserNameModified = false;
//    private boolean isPasswordModified = false;
//    private boolean isFolderPathModified = false;
//    private boolean isServerNameModified = false;
//    private boolean isPropertiesMapModified = false;

	/**
	 * Creates an empty job source.
	 */
	public FTPInfoModel() {
	}

    /**
	 * Specifies whether the login user name for the FTP server
	 *
	 * @param  userName the login user name
	 */
    public void setUserName(String userName) {
//        isUserNameModified = true;
        super.setUserName(userName);
    }

     /**
	 * Specifies whether the login password for the FTP server
	 *
	 * @param password the login password
	 */
    public void setPassword(String password) {
//        isPasswordModified = true;
        super.setPassword(password);
    }

    /**
	 * Specifies whether the path of the folder under which job output
	 * resources would be created.
	 *
	 * @param folderPath the folder path
	 */
    public void setFolderPath(String folderPath) {
//        isFolderPathModified = true;
        super.setFolderPath(folderPath);
    }

    /**
	 * Returns the server name for the ftp site.
	 *
	 * @return the server name
	 */
    public void setServerName(String serverName) {
//        isServerNameModified = true;
        super.setServerName(serverName);
    }

    /**
     * Specifies FTP type: TYPE_FTP / TYPE_FTPS
     *
     * @param type the ftp type
     */
    public void setType(FtpType type) {
//        isPropertiesMapModified = true;
        super.setType(type);
    }

    /**
     * Specifies the port number of the ftp site
     *
     * @param port the port number
     */
    public void setPort(int port) {
//        isPropertiesMapModified = true;
        super.setPort(port);
    }

    /**
     * Specifies the protocol of the ftp site
     *
     * @param protocol the protocol
     */
    public void setProtocol(String protocol) {
//        isPropertiesMapModified = true;
        super.setProtocol(protocol);
    }

    /**
     * Specifies the security mode for FTPS (Implicit/ Explicit)
     * If isImplicit is true, the default port is set to 990
     *
     * @param implicit
     */
    public void setImplicit(boolean implicit) {
//        isPropertiesMapModified = true;
        super.setImplicit(implicit);
    }

    /**
     * specifies pbsz value: 0 to (2^32)-1 decimal integer.
     * @param pbsz Protection Buffer Size.
     */
    public void setPbsz(long pbsz) {
//        isPropertiesMapModified = true;
        super.setPbsz(pbsz);
    }

    /**
     * specific PROT command.
     * <ul>
     * <li>C - Clear</li>
     * <li>S - Safe(SSL protocol only)</li>
     * <li>E - Confidential(SSL protocol only)</li>
     * <li>P - Private</li>
     * </ul>
     * @param prot Data Channel Protection Level
     */
    public void setProt(String prot) {
//        isPropertiesMapModified = true;
        super.setProt(prot);
    }

    /**
     * Sets the set additional properties for FTP info
     *
     * @param propertiesMap extra properties for FTP info
     */
    public void setPropertiesMap(Map<String, String>  propertiesMap) {
//        isPropertiesMapModified = true;
        super.setPropertiesMap(propertiesMap);
    }

//    /**
//     * returns whether UserName has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isUserNameModified() { return isUserNameModified; }
//
//    /**
//     * returns whether Password has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isPasswordModified() { return isPasswordModified; }
//
//    /**
//     * returns whether folder path has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isFolderPathModified() { return isFolderPathModified; }
//
//    /**
//     * returns whether server name has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isServerNameModified() { return isServerNameModified; }
//
//    /**
//     * returns whether Properties Map has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isPropertiesMapModified() { return isPropertiesMapModified; }
}
