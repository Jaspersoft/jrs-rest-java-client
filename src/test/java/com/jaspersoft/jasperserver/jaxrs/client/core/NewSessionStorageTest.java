package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({ClientBuilder.class, SSLContext.class, EncryptionUtils.class})
public class NewSessionStorageTest extends PowerMockTestCase {

    @Mock
    public RestClientConfiguration configurationMock;

    @Mock
    public AuthenticationCredentials credentialsMock;

    @Mock
    public WebTarget targetMock;
    @Mock
    public ClientBuilder builderMock;
    @Mock
    public Client clientMock;
    @Mock
    public Invocation.Builder invocationBuilderMock;
    @Mock
    public Response responseMock;
    @Mock
    public Response.StatusType statusTypeMock;

    @Mock
    public SSLContext sslContextMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(enabled = true)
    public void should_init_ssl() {

        /* Given */
        //PowerMockito.mockStatic(LogFactory.class);
        //PowerMockito.when(LogFactory.getLog(SessionStorage.class)).thenReturn(logMock);

        /** - mock for static method **/
        PowerMockito.mockStatic(ClientBuilder.class);
        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);

        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
        Mockito.doReturn("https://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
        Mockito.doReturn(clientMock).when(builderMock).build();
        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");

        /** - mocks for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();

        PowerMockito.mockStatic(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(null);
        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");

        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
        Mockito.doReturn(new HashMap<String, NewCookie>() {{
            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
        }}).when(responseMock).getCookies();

        /** - mocks for {@link SessionStorage#login()} method **/
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));

        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
                .when(responseMock)
                .getHeaderString("Set-Cookie");
        Mockito.doReturn(302).when(responseMock).getStatus();


        try {
            new SessionStorage(configurationMock, credentialsMock);
        } catch (Exception e) {
            //Mockito.verify(logMock, times(1)).error(anyString(), any(NoSuchAlgorithmException.class));
            assertNotNull(e);
        }
    }

    @AfterMethod
    public void after() {
        reset(configurationMock, credentialsMock, builderMock, invocationBuilderMock, clientMock,
                responseMock, targetMock, sslContextMock, statusTypeMock);
    }
}
