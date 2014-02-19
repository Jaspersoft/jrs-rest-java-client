package com.jaspersoft.jasperserver.jaxrs.client.restservices.exceptions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionMask;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AccessForbiddenException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailureException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.BadRequestException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
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
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("lalala")
                .get();
    }

    @Test(expectedExceptions = AuthenticationFailureException.class)
    public void testGetResourceByNotAuthorizedUser() {
        client
                .authenticate("jasperadmin", "")
                .usersService()
                .username("lalala")
                .get();
    }

    @Test(expectedExceptions = AccessForbiddenException.class)
    public void testPerformForbiddenAction() {
        /*
        * setting own permissions is forbidden for user
        */
        RepositoryPermission permission = new RepositoryPermission()
                .setRecipient("user:/jasperadmin")
                .setUri("/themes")
                .setMask(PermissionMask.READ_WRITE_DELETE);

        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .createNew(permission);
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void testSendIncorrectRequest() {
        client
                .authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .prepareForRun(ReportOutputFormat.HTML, 1000000)
                .parameter("Cascading_name_single_select", "lalala")
                .run();
    }

}
