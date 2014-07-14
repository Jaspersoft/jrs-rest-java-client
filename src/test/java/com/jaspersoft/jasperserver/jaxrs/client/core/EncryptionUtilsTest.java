package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.security.PublicKey;

import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Unit tests for {@link EncryptionUtils}
 */
@PrepareForTest({EncryptionUtils.class, LogFactory.class})
public class EncryptionUtilsTest extends PowerMockTestCase {

    @Mock
    private Log logMock;

    @Mock
    private PublicKey keyMock;

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
        Assert.assertEquals(retrieved, "encryptedPassword");
    }

    @Test
    public void should_invoke_error_method_of_Log_class_only_once() {

        // TODO: need to fix compatibility with method above. Works only in separate mode.

        // Given
        PowerMockito.mockStatic(LogFactory.class);
        PowerMockito.when(LogFactory.getLog(EncryptionUtils.class)).thenReturn(logMock);

        // When
        try {
            EncryptionUtils.encryptPassword("pass", "n", "e");
        } catch (RuntimeException e) {/* NOP */}

        // Than
        Mockito.verify(logMock).error(Matchers.anyString());
    }

    @AfterMethod
    public void after() {
        Mockito.reset(logMock, keyMock);
    }
}