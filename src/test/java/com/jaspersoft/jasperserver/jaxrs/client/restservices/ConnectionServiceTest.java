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

package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.connection.FtpConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConnectionServiceTest extends Assert{

    private static JasperserverRestClient client;
    private String connectionUuid;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testCreateConnection() throws Exception {
        FtpConnection ftpConnection = new FtpConnection();
        ftpConnection
                .setFolderPath("/")
                .setUserName("borys.kolesnykov")
                .setPassword("rfkfy_ghbdtn789")
                .setType(FtpConnection.FtpType.ftp)
                .setHost("localhost")
                .setPort(21);

        OperationResult<FtpConnection> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .connectionService()
                .newConnection(FtpConnection.class, ftpConnection);

        assertEquals(result.getResponse().getStatus(), ResponseStatus.CREATED);
        String connectionUuid = result.getResponse().getHeaderString("Location");
        this.connectionUuid = connectionUuid.substring(connectionUuid.lastIndexOf("/") + 1);
        assertNotNull(connectionUuid);
        assertNotNull(result.getEntity());

    }

    @Test(dependsOnMethods = "testCreateConnection")
    public void testGetConnection() throws Exception {
        OperationResult<FtpConnection> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .connectionService()
                .connection(connectionUuid)
                .get(FtpConnection.class);

        assertNotNull(result.getEntity());
    }

    @Test(dependsOnMethods = "testCreateConnection")
    public void testUpdateConnection() throws Exception {
        FtpConnection ftpConnection = new FtpConnection();
        ftpConnection
                .setFolderPath("/Downloads")
                .setUserName("borys.kolesnykov")
                .setPassword("rfkfy_ghbdtn789")
                .setType(FtpConnection.FtpType.ftp)
                .setHost("localhost")
                .setPort(21);

        OperationResult<FtpConnection> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .connectionService()
                .connection(connectionUuid)
                .update(FtpConnection.class, ftpConnection);

        assertEquals(result.getResponse().getStatus(), ResponseStatus.UPDATED);
        assertNotNull(result.getEntity());
    }

    @Test(dependsOnMethods = {"testUpdateConnection", "testGetConnection"})
    public void testDelete() throws Exception {
        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .connectionService()
                .connection(connectionUuid)
                .delete();

        assertEquals(result.getResponse().getStatus(), ResponseStatus.NO_CONTENT);
    }
}
