package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/10/2015.
 */
public class UsersServiceIT {


    @Test
    public void shouldReturnProperListOfUsers() {
        RestClientConfiguration cfg = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(cfg);
        Session session = client.authenticate("superuser", "superuser");

        UsersListWrapper entity = session.usersService()
                .allUsers()
                .param(UsersParameter.REQUIRED_ROLE, "ROLE_USER")
                .get()
                .getEntity();

        Assert.assertNotNull(entity);
        List<ClientUser> list = entity.getUserList();
        Assert.assertSame(list.size(), 4);
    }
}