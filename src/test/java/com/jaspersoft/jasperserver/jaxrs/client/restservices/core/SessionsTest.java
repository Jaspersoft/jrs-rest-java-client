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

package com.jaspersoft.jasperserver.jaxrs.client.restservices.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SessionsTest extends Assert {

    private static Log log = LogFactory.getLog(SessionsTest.class);

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void sessionsIdMustBeDifferentForDifferentUsers(){
        Session jasperadminSession = client.authenticate("jasperadmin|organization_1", "jasperadmin");
        Session joeuserSession = client.authenticate("joeuser|organization_1", "joeuser");

        jasperadminSession.resourcesService().resource("/reports/samples/StandardChartsEyeCandyReport").details();
        joeuserSession.resourcesService().resource("/reports/samples/StandardChartsEyeCandyReport").details();

        String jasperadminSessionId = jasperadminSession.getStorage().getSessionId();
        String joeuserSessionId = joeuserSession.getStorage().getSessionId();

        assertNotEquals(jasperadminSessionId, joeuserSessionId);
    }

    @Test
    public void sessionsIdMustBeDifferentForDifferentAuthentications() {
        Session jasperadminSession1 = client.authenticate("jasperadmin|organization_1", "jasperadmin");
        Session jasperadminSession2 = client.authenticate("jasperadmin|organization_1", "jasperadmin");

        jasperadminSession1.resourcesService().resource("/reports/samples/StandardChartsEyeCandyReport").details();
        jasperadminSession2.resourcesService().resource("/reports/samples/StandardChartsEyeCandyReport").details();

        String jasperadminSession1Id = jasperadminSession1.getStorage().getSessionId();
        String jasperadminSession2Id = jasperadminSession2.getStorage().getSessionId();

        assertNotEquals(jasperadminSession1Id, jasperadminSession2Id);
    }

    @Test
    public void sessionIdMustBeSameWithinAuthentication() throws Exception {
        Session jasperadminSession = client.authenticate("jasperadmin|organization_1", "jasperadmin");

        jasperadminSession.resourcesService().resource("/reports/samples/StandardChartsEyeCandyReport").details();
        String sessionId1 = jasperadminSession.getStorage().getSessionId();
        log.info("Session1: id = " + sessionId1);
        jasperadminSession.resourcesService().resource("/reports/samples/StandardChartsAegeanReport").details();
        String sessionId2 = jasperadminSession.getStorage().getSessionId();
        log.info("Session2: id = " + sessionId2);

        assertEquals(sessionId1, sessionId2);
    }
}
