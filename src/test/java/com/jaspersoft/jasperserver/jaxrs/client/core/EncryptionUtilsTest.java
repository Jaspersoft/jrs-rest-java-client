//package com.jaspersoft.jasperserver.jaxrs.client.core;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.codehaus.jettison.json.JSONObject;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import javax.crypto.Cipher;
//import javax.ws.rs.core.Response;
//import java.math.BigInteger;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.spec.RSAPublicKeySpec;
//import java.util.Map;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNull;
//import static org.testng.Assert.assertTrue;
//
///**
// * Unit tests for {@link EncryptionUtils}
// */
//@PrepareForTest({
//        EncryptionUtils.class,
//        JSONObject.class,
//        LogFactory.class,
//        Cipher.class,
//        KeyFactory.class,
//        Log.class
//})
//public class EncryptionUtilsTest extends PowerMockTestCase {
//
//    @Mock
//    private Log logMock;
//
//    @Mock
//    private PublicKey keyMock;
//
//    @Mock
//    private Response responseMock;
//
//    @Mock
//    public KeyFactory keyFactoryMock;
//
//    @Mock
//    public BigInteger publicExponentMock;
//
//    @Mock
//    public BigInteger modulusMock;
//
//    @Mock
//    public RSAPublicKeySpec rsaPublicKeySpecMock;
//
//    @Mock
//    public Cipher cipherMock;
//
//    @Mock
//    public JSONObject jsonObjectMock;
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test(enabled = false)
//    public void should_invoke_error_method_of_Log_class_only_once() throws Exception {
//
//        // Given
//        PowerMockito.mockStatic(LogFactory.class);
//        Mockito.when(LogFactory.getLog(EncryptionUtils.class)).thenReturn(logMock);
//
//        // When
//        try {
//            EncryptionUtils.encryptPassword("pass", "n", "e");
//        } catch (RuntimeException e) {/* NOP */}
//
//        // Then
//        verify(logMock, times(1)).error(anyString()); // был вызван только один раз
//    }
//
//    @Test(expectedExceptions = RuntimeException.class, enabled = false)
//    public void should_invoke_info_method_of_Log_class_only_once() {
//        EncryptionUtils.encryptPassword("pass", "n", "e");
//        PowerMockito.mockStatic(LogFactory.class);
//        PowerMockito.when(LogFactory.getLog(EncryptionUtils.class)).thenReturn(logMock);
//        verify(logMock, times(1)).info(anyString());
//    }
//
//    @Test(enabled = true)
//    public void should_encrypt_password() throws Exception {
//
//        /* Given */
//        KeyFactory keyFactoryMock = PowerMockito.mock(KeyFactory.class);
//        Cipher cipherMock = PowerMockito.mock(Cipher.class);
//
//        PowerMockito.mockStatic(KeyFactory.class);
//        PowerMockito.when(KeyFactory.getInstance("RSA")).thenReturn(keyFactoryMock);
//
//        PowerMockito.mockStatic(Cipher.class);
//        PowerMockito.when(Cipher.getInstance("RSA/NONE/NoPadding", new BouncyCastleProvider())).thenReturn(cipherMock);
//
//        PowerMockito.whenNew(BigInteger.class).withArguments("e", 16).thenReturn(publicExponentMock);
//        PowerMockito.whenNew(BigInteger.class).withArguments("n", 16).thenReturn(modulusMock);
//        PowerMockito.whenNew(RSAPublicKeySpec.class).withArguments(modulusMock, publicExponentMock).thenReturn(rsaPublicKeySpecMock);
//
//        PowerMockito.doReturn(keyMock).when(keyFactoryMock).generatePublic(rsaPublicKeySpecMock);
//        PowerMockito.doNothing().when(cipherMock).init(1, keyMock);
//        PowerMockito.doReturn(new byte[]{0, 23, 23, 113, 2}).when(cipherMock).doFinal(any(byte[].class));
//
//        /* When */
//        String retrieved = EncryptionUtils.encryptPassword("pass", "n", "e");
//
//        /* Then */
//        assertNotNull(retrieved);
//        assertEquals(retrieved, "0017177102");
//
//        PowerMockito.verifyStatic(times(1));
//        Cipher.getInstance("RSA/NONE/NoPadding", new BouncyCastleProvider());
//
//        PowerMockito.verifyStatic(times(1));
//        KeyFactory.getInstance("RSA");
//
//        PowerMockito.verifyNew(BigInteger.class).withArguments("e", 16);
//        PowerMockito.verifyNew(BigInteger.class).withArguments("n", 16);
//        PowerMockito.verifyNew(RSAPublicKeySpec.class).withArguments(modulusMock, publicExponentMock);
//
//        Mockito.verify(keyFactoryMock, times(1)).generatePublic(rsaPublicKeySpecMock);
//        Mockito.verify(cipherMock, times(1)).init(1, keyMock);
//    }
//
//    @Test
//    public void should_parse_encryption_params() throws Exception {
//
//        /* Given */
//        Mockito.doReturn("{number:1}").when(responseMock).readEntity(String.class);
//        PowerMockito.whenNew(JSONObject.class).withArguments("{number:1}").thenReturn(jsonObjectMock);
//        PowerMockito.doReturn("n_").doReturn("e_").when(jsonObjectMock).getString(anyString());
//
//        /* When */
//        Map<String, String> retrieved = EncryptionUtils.parseEncryptionParams(responseMock);
//
//        /* Then */
//        PowerMockito.verifyNew(JSONObject.class).withArguments("{number:1}");
//        Mockito.verify(jsonObjectMock, times(2)).getString(anyString());
//
//        assertTrue(retrieved.size() == 2);
//        assertEquals(retrieved.get("e"), "e_");
//    }
//
//    @Test
//    public void should_create_instance() {
//        EncryptionUtils utils = new EncryptionUtils();
//        assertNotNull(utils);
//    }
//
//    @Test
//    public void should_return_null_map() {
//        PowerMockito.when(responseMock.readEntity(String.class)).thenReturn("a");
//        Map<String, String> retrieved = EncryptionUtils.parseEncryptionParams(responseMock);
//        assertNull(retrieved);
//    }
//
//    @AfterMethod
//    public void after() {
//        Mockito.reset(logMock, keyMock, jsonObjectMock, publicExponentMock, keyFactoryMock, cipherMock,
//                rsaPublicKeySpecMock, modulusMock, responseMock);
//    }
//}