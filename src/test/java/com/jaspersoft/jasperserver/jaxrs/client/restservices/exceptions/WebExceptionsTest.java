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

package com.jaspersoft.jasperserver.jaxrs.client.restservices.exceptions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionMask;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebExceptionsTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void testGetNonexistentResource() {
        client
                .authenticate("superuser", "superuser")
                .usersService()
                .username("lalala")
                .get();
    }

    @Test(expectedExceptions = AuthenticationFailedException.class)
    public void testGetResourceByNotAuthorizedUser() {
        client
                .authenticate("superuser", "")
                .usersService()
                .username("lalala")
                .get();
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void testPerformForbiddenAction() {
        /*
        * setting own permissions is forbidden for user
        */
        RepositoryPermission permission = new RepositoryPermission()
                .setRecipient("user:/organization_1/jasperadmin")
                .setUri("/themes")
                .setMask(PermissionMask.READ_WRITE_DELETE);

        client
                .authenticate("superuser", "superuser")
                .permissionsService()
                .createNew(permission);
    }

    //@Test(expectedExceptions = ReportExportException.class)
    @Test(expectedExceptions = BadRequestException.class)
    public void testSendIncorrectRequest() {
        client
                .authenticate("jasperadmin|organization_1", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .prepareForRun(ReportOutputFormat.HTML, 1000000)
                .parameter("Cascading_name_single_select", "lalala")
                .run();
    }

}
