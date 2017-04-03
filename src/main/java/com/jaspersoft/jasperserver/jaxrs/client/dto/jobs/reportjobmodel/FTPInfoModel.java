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
 * @deprecated (use server adapter).
 */
public class FTPInfoModel extends FtpInfo {
    /**
     * Creates an empty job source.
     */
    public FTPInfoModel(FTPInfoModel other) {
        super(other);
    }

    /**
     * Specifies whether the login user name for the FTP server
     *
     * @param userName the login user name
     */
    public FTPInfoModel setUserName(String userName) {
        super.setUserName(userName);
        return this;
    }

    /**
     * Specifies whether the login password for the FTP server
     *
     * @param password the login password
     */
    public FTPInfoModel setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    /**
     * Specifies whether the path of the folder under which job output
     * resources would be created.
     *
     * @param folderPath the folder path
     */
    public FTPInfoModel setFolderPath(String folderPath) {
        super.setFolderPath(folderPath);
        return this;
    }

    /**
     * Returns the server name for the ftp site.
     *
     * @return the server name
     */
    public FTPInfoModel setServerName(String serverName) {
        super.setServerName(serverName);
        return this;
    }

    /**
     * Specifies FTP type: TYPE_FTP / TYPE_FTPS
     *
     * @param type the ftp type
     */
    public FTPInfoModel setType(FtpType type) {
        super.setType(type);
        return this;
    }

    /**
     * Specifies the port number of the ftp site
     *
     * @param port the port number
     */
    public FTPInfoModel setPort(int port) {
        super.setPort(port);
        return this;
    }

    /**
     * Specifies the protocol of the ftp site
     *
     * @param protocol the protocol
     */
    public FTPInfoModel setProtocol(String protocol) {
        super.setProtocol(protocol);
        return this;
    }

    /**
     * Specifies the security mode for FTPS (Implicit/ Explicit)
     * If isImplicit is true, the default port is set inFolder 990
     *
     * @param implicit
     */
    public FTPInfoModel setImplicit(boolean implicit) {
        super.setImplicit(implicit);
        return this;
    }

    /**
     * specifies pbsz value: 0 inFolder (2^32)-1 decimal integer.
     *
     * @param pbsz Protection Buffer Size.
     */
    public FTPInfoModel setPbsz(long pbsz) {
        super.setPbsz(pbsz);
        return this;
    }

    /**
     * specific PROT command.
     * <ul>
     * <li>C - Clear</li>
     * <li>S - Safe(SSL protocol only)</li>
     * <li>E - Confidential(SSL protocol only)</li>
     * <li>P - Private</li>
     * </ul>
     *
     * @param prot Data Channel Protection Level
     */
    public FTPInfoModel setProt(String prot) {
        super.setProt(prot);
        return this;
    }

    /**
     * Sets the set additional properties for FTP info
     *
     * @param propertiesMap extra properties for FTP info
     */
    public FTPInfoModel setPropertiesMap(Map<String, String> propertiesMap) {
        super.setPropertiesMap(propertiesMap);
        return this;
    }
}
