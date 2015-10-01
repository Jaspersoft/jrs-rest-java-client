package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.BatchAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import javax.ws.rs.core.MultivaluedMap;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.Assert;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class OrganizationBatchAttributeAdapterTest {

    @Test
    public void should_set_params() {
        SessionStorage sessionStorageMock = Mockito.mock(SessionStorage.class);
        BatchAttributeAdapter adapter = new BatchAttributeAdapter(
                sessionStorageMock,
                "MyOrg", 
                "number_of_employees", 
                "country_code");
        
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>)
                Whitebox.getInternalState(adapter, "params");
        Assert.assertSame(params.get("name").size(), 2);
    }
}