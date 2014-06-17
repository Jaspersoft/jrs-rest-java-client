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

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ServerInfoServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testGetServerInfoDetails() throws Exception {
        OperationResult<ServerInfo> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .details();

        ServerInfo serverInfo = result.getEntity();
        assertNotNull(serverInfo);
    }

    @Test
    public void testGetEdition() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .edition();

        String edition = result.getEntity();
        assertEquals(edition, "PRO");
    }

    @Test
    public void testGetVersion() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .version();

        String version = result.getEntity();
        assertEquals(version, "5.6.0");
    }

    @Test(enabled = false)
    public void testGetBuild() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .build();

        String build = result.getEntity();
        assertEquals(build, "20140522_1943");
    }

    @Test
    public void testGetFeatures() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .features();

        String features = result.getEntity();
        assertEquals(features, "Fusion AHD EXP DB ANA AUD MT ");
    }

    @Test
    public void testGetEditionName() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .editionName();

        String editionName = result.getEntity();
        assertEquals(editionName, "Enterprise");
    }

    @Test
    public void testGetLicenceType() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .licenseType();

        String licenceType = result.getEntity();
        assertEquals(licenceType, "Commercial");
    }

    @Test
    public void testGetExpiration() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .expiration();

        String expiration = result.getEntity();
        assertNull(expiration);
    }

    @Test
    public void testGetDateFormatPattern() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .dateFormatPattern();

        String dateFormatPattern = result.getEntity();
        assertEquals(dateFormatPattern, "yyyy-MM-dd");
    }

    @Test
    public void testGetDateTimeFormatPattern() throws Exception {
        OperationResult<String> result = client
                .authenticate("superuser", "superuser")
                .serverInfoService()
                .dateTimeFormatPattern();

        String dateTimeFormatPattern = result.getEntity();
        assertEquals(dateTimeFormatPattern, "yyyy-MM-dd HH:mm:ss");
    }

}
