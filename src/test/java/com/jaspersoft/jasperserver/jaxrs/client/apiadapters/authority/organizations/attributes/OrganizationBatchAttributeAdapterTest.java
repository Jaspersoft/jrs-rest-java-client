//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes;
//
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import org.mockito.Mock;
//import org.mockito.internal.util.reflection.Whitebox;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import javax.ws.rs.core.MultivaluedMap;
//
//import static org.mockito.Mockito.reset;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//@SuppressWarnings("unchecked")
//public class OrganizationBatchAttributeAdapterTest {
//
//    @Mock
//    private SessionStorage sessionStorageMock;
//
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test
//    public void should_set_params() {
//        OrganizationBatchAttributeAdapter adapter = new OrganizationBatchAttributeAdapter(sessionStorageMock,
//                "MyOrg", "number_of_employees", "country_code");
//        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>)
//                Whitebox.getInternalState(adapter, "params");
//        Assert.assertSame(params.get("name").size(), 2);
//
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(sessionStorageMock);
//    }
//}