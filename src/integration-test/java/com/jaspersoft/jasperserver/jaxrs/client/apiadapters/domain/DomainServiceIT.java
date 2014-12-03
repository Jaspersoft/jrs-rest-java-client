package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.domain.DomainMetaData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_return_not_null_entity_when_data_is_exist() {
        DomainMetaData entity = session
                .domainService()
                .domainMetadata("/organizations/organization_1/Domains/supermartDomain")
                .retrieve()
                .entity();

        assertNotNull(entity);
    }

    @AfterMethod
    public void after() {
        session.logout();
    }
}
