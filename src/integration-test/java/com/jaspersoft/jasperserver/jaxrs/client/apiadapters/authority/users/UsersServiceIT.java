package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT extends RestClientTestUtil {

    private ClientUser user;
    private ClientUser user1;


    @BeforeClass
    public void before() {
        initClient();
        initSession();
        user = new ClientUser()
                .setUsername("test_user")
                .setPassword("test_password")
                .setEmailAddress("john.doe@email.net")
                .setEnabled(true)
                .setExternallyDefined(false)
                .setFullName("John Doe");
        user1 = new ClientUser()
                .setUsername("test_user_1")
                .setPassword("12345678")
                .setEmailAddress("john.doe@email.net")
                .setEnabled(true)
                .setExternallyDefined(false)
                .setFullName("John Doe");
    }

    @Test
    public void should_create_new_users() {

        OperationResult<ClientUser> operationResult = session
                .usersService()
                .user(user)
                .createOrUpdate(user);

        ClientUser entity = operationResult.getEntity();

        assertNotNull(entity);
        assertEquals(Response.Status.CREATED.getStatusCode(), operationResult.getResponse().getStatus());

    }


    @Test(dependsOnMethods = "should_create_new_users")
    public void should_return_user_by_name() {

        ClientUser clientUser = new ClientUser().setUsername(user.getUsername());

        OperationResult<ClientUser> operationResult =
                session
                        .usersService()
                        .user(clientUser)
                        .get();

        ClientUser retrivedUser = operationResult.getEntity();

        assertNotNull(retrivedUser);
        assertNotNull(retrivedUser.getUsername());
        assertEquals(user.getUsername(), retrivedUser.getUsername());
    }

    @Test(dependsOnMethods = "should_return_user_by_name")
    public void should_update_user() {

        ClientUser newUser = new ClientUser(user).setFullName("another_full_name");

        OperationResult<ClientUser> operationResult = session
                        .usersService()
                        .user(user)
                        .createOrUpdate(newUser);

        ClientUser retrivedUser = operationResult.getEntity();

        assertNotNull(retrivedUser);
        assertNotNull(retrivedUser.getUsername());
        assertEquals(user.getUsername(), retrivedUser.getUsername());
        assertEquals(newUser.getFullName(), retrivedUser.getFullName());
        assertEquals(Response.Status.OK.getStatusCode(), operationResult.getResponse().getStatus());
    }

    @Test(dependsOnMethods = "should_update_user")
    public void should_delete_user() {
        OperationResult<ClientUser> operationResult =
                session
                        .usersService()
                        .user(user)
                        .delete();

        assertNull(operationResult.getEntity());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), operationResult.getResponse().getStatus());
    }


    @Test(dependsOnMethods = "should_delete_user")
    public void should_create_new_users_in_organization() {

        OperationResult<ClientUser> operationResult = session
                .usersService()
                .forOrganization("organization_1")
                .user(user)
                .createOrUpdate(user);

        ClientUser entity = operationResult.getEntity();

        assertNotNull(entity);
        assertEquals(Response.Status.CREATED.getStatusCode(), operationResult.getResponse().getStatus());

    }


    @Test(dependsOnMethods = "should_create_new_users_in_organization")
    public void should_return_user_by_name_in_organization() {

        ClientUser clientUser = new ClientUser().setUsername(user.getUsername());

        OperationResult<ClientUser> operationResult =
                session
                        .usersService()
                        .forOrganization("organization_1")
                        .user(clientUser)
                        .get();

        ClientUser retrivedUser = operationResult.getEntity();

        assertNotNull(retrivedUser);
        assertNotNull(retrivedUser.getUsername());
        assertEquals(user.getUsername(), retrivedUser.getUsername());
        assertEquals("organization_1", retrivedUser.getTenantId());
    }

    @Test(dependsOnMethods = "should_return_user_by_name_in_organization")
    public void should_update_user_in_organization() {

        ClientUser newUser = new ClientUser(user).setFullName("another_full_name");

        OperationResult<ClientUser> operationResult = session
                        .usersService()
                .forOrganization("organization_1")
                .user(user)
                        .createOrUpdate(newUser);

        ClientUser retrivedUser = operationResult.getEntity();

        assertNotNull(retrivedUser);
        assertNotNull(retrivedUser.getUsername());
        assertEquals(user.getUsername(), retrivedUser.getUsername());
        assertEquals(newUser.getFullName(), retrivedUser.getFullName());
        assertEquals(Response.Status.OK.getStatusCode(), operationResult.getResponse().getStatus());
    }

    @Test(dependsOnMethods = "should_update_user_in_organization")
    public void should_delete_user_in_organization() {
        OperationResult<ClientUser> operationResult =
                session
                        .usersService()
                        .forOrganization("organization_1")
                        .user(user)
                        .delete();

        assertNull(operationResult.getEntity());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), operationResult.getResponse().getStatus());
    }

    @Test(dependsOnMethods = "should_delete_user_in_organization")
    public void should_return_list_of_users() {
        OperationResult<ClientUser> operationResult = session
                .usersService()
                .user(user)
                .createOrUpdate(user);
        OperationResult<ClientUser> operationResult1 = session
                .usersService()
                .user(user1)
                .createOrUpdate(user1);
        OperationResult<UsersListWrapper> listOperationResult = session
                        .usersService()
                        .allUsers()
                        .get();

        assertNotNull(operationResult.getEntity());
        assertNotNull(operationResult1.getEntity());
        assertNotNull(listOperationResult.getEntity());
        assertTrue(listOperationResult.getEntity().getUserList().size() >= 2);
    }


    @Test(dependsOnMethods = "should_return_list_of_users")
    public void should_return_list_of_users_by_role() {

        OperationResult<UsersListWrapper> operationResult = session
                .usersService()
                .allUsers()
                .param(UsersParameter.REQUIRED_ROLE, "ROLE_USER")
                .get();

        UsersListWrapper usersListWrapper = operationResult.getEntity();

        assertNotNull(operationResult.getEntity());
        assertTrue(usersListWrapper.getUserList().size() >= 2);

        OperationResult<ClientUser> operationResult1= session
                .usersService()
                .user(user)
                .delete();
        OperationResult<ClientUser> operationResult2 = session
                .usersService()
                .user(user1)
                .delete();

    }


    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}