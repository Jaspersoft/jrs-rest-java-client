package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.security.PublicKey;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.EncryptionUtils}
 */
@PrepareForTest({EncryptionUtils.class, LogFactory.class, Log.class})
public class EncryptionUtilsTest extends PowerMockTestCase {

    @Mock
    private Log logMock;

    @Mock
    private PublicKey keyMock;

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_invoke_all_private_static_methods_only_once() throws Exception {

        // Given
        PowerMock.mockStaticPartial(EncryptionUtils.class, "getEncryptedPassword", "getPublicKey");

        Method[] keys = MemberMatcher.methods(EncryptionUtils.class, "getPublicKey");
        Method[] passwords = MemberMatcher.methods(EncryptionUtils.class, "getEncryptedPassword");

        PowerMock.expectPrivate(EncryptionUtils.class, keys[0], "n", "e").andReturn(keyMock);
        PowerMock.expectPrivate(EncryptionUtils.class, passwords[0], keyMock, "pwd").andReturn("encryptedPassword");

        PowerMock.replay(EncryptionUtils.class);

        // When
        String retrieved = EncryptionUtils.encryptPassword("pwd", "n", "e");

        // Than
        PowerMock.verify(EncryptionUtils.class);
        Assert.assertNotNull(retrieved);
        assertEquals(retrieved, "encryptedPassword");
    }

//    @Test
//    public void should_invoke_error_method_of_Log_class_only_once() throws Exception {
//
//        // Given
//        PowerMockito.mockStatic(LogFactory.class);
//        PowerMockito.when(LogFactory.getLog(EncryptionUtils.class)).thenReturn(logMock);
//
//        // When
//        try {
//            EncryptionUtils.encryptPassword("pass", "n", "e");
//        } catch (RuntimeException e) {/* NOP */}
//
//        // Than
//        Mockito.verify(logMock, times(1)).error("Failed to encrypt password. Possible encryption provider issues."); // был вызван только один раз
//    }

//    @Test
//    public void should_invoke_info_method_of_Log_class_only_once() {
//
//        PowerMockito.mockStatic(LogFactory.class);
//        PowerMockito.when(LogFactory.getLog(EncryptionUtils.class)).thenReturn(logMock);
//        PowerMockito.when(responseMock.readEntity(String.class)).thenReturn("a");
//
//        try {
//            EncryptionUtils.parseEncryptionParams(responseMock);
//        } catch (Exception e) {
//            // NOP
//        }
//
//        verify(logMock, times(1)).info(anyString());
//
//    }

    @AfterMethod
    public void after() {
        Mockito.reset(logMock, keyMock, responseMock);
    }
}