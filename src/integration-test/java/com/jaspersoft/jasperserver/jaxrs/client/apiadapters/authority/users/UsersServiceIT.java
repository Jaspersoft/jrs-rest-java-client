package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT extends RestClientTestUtil {


    @BeforeClass
    public void before() {
        initClient();
        initSession();
    }

    @Test
    public void shouldReturnAllUsers() {

         //When
        List<ClientUser> users = session
                .usersService()
                .allUsers()
                .get()
                .getEntity()
                .getUserList();

         //Then
        Assert.assertTrue(users.size() > 3);
    }

    @Test
    public void should_return_user_by_name() {

        OperationResult<ClientUser> operationResult =
                session
                        .usersService()
                        .username("superuser")
                        .get();

        ClientUser user = operationResult.getEntity();

        assertNotNull(user);
        assertNotNull(user.getUsername());
    }



    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}