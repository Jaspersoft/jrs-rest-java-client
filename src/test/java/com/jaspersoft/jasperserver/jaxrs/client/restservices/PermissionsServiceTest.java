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

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionMask;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionRecipient;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionResourceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PermissionsServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public void setUp() {

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);

        try {
            client
                    .authenticate("superuser", "superuser")
                    .permissionsService()
                    .resource("/public/Samples/Data_Sources")
                    .permissionRecipient(PermissionRecipient.USER, "organization_1/joeuser")
                    .delete();

            client
                    .authenticate("superuser", "superuser")
                    .permissionsService()
                    .resource("/public")
                    .permissionRecipient(PermissionRecipient.USER, "organization_1/joeuser")
                    .delete();

            client
                    .authenticate("superuser", "superuser")
                    .permissionsService()
                    .resource("/public/audit")
                    .delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public void tearDown() {
        setUp();
    }

    @Test
    public void testGetPermissionForResourceBatch() {
        OperationResult<RepositoryPermissionListWrapper> operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public/Samples/Data_Sources")
                        .param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "true")
                        .get();

        RepositoryPermissionListWrapper permissions = operationResult.getEntity();
        assertNotEquals(permissions, null);
    }

    @Test(enabled = true)
    public void testAddPermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/public/audit", "user:/organization_1/joeuser", 30));
        //permissionList.add(new RepositoryPermission("/public/audit", "role:/ROLE_ADMINISTRATOR", 30));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .createNew(permissionListWrapper);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionBatch"}, enabled = true)
    public void testUpdatePermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/public/audit", "user:/organization_1/joeuser", 1));
        //permissionList.add(new RepositoryPermission("/public/audit", "role:/ROLE_ADMINISTRATOR", 1));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public/audit")
                        .createOrUpdate(permissionListWrapper);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionBatch", enabled = true)
    public void testDeletePermissionBatch() {
        OperationResult operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public/audit")
                        .delete();

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 204);
    }

    @Test(enabled = false)
    public void testGetPermissionForResourceWithPermissionRecipientSingle() {
        OperationResult<RepositoryPermission> operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public/Samples/Data_Sources")
                        .param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "true")
                        .permissionRecipient(PermissionRecipient.ROLE, "ROLE_ADMINISTRATOR")
                        .get();

        RepositoryPermission permission = operationResult.getEntity();
        assertNotEquals(permission, null);
    }

    @Test
    public void testAddPermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/public")
                .setRecipient("user:/organization_1/joeuser")
                .setMask(PermissionMask.READ_WRITE_DELETE);

        OperationResult operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .createNew(permission);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionSingle"})
    public void testUpdatePermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/public")
                .setRecipient("user:/organization_1/joeuser")
                .setMask(1);

        OperationResult<RepositoryPermission> operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public")
                        .permissionRecipient(PermissionRecipient.USER, "organization_1/joeuser")
                        .createOrUpdate(permission);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionSingle")
    public void testDeletePermissionSingle() {
        OperationResult operationResult =
                client
                        .authenticate("superuser", "superuser")
                        .permissionsService()
                        .resource("/public")
                        .permissionRecipient(PermissionRecipient.USER, "organization_1/joeuser")
                        .delete();

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

}
