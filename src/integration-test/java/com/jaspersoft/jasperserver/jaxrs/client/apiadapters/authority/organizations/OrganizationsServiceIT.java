package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Tetiana Iefimenko
 */
public class OrganizationsServiceIT {


    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_1);
        config.setLogHttp(true);
        client = new JasperserverRestClient(config);

        session = client.authenticate("superuser", "superuser");
    }

    @Test(enabled = false)
    public void should_return_tenant_attributes() {
//        OperationResult<TenantAttributesListWrapper> result3 = session
//                .organizationsService()
//                .organization("organization_1")
//                .attributes()
//                .get();
    }

}
