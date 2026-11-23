package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

import com.jaspersoft.jasperserver.dto.connection.metadata.TableMetadata;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ContextServiceTest {

    public static final String SOME_UU_ID = "someUuId";
    public static final String SOME_MIME_TYPE = "someMimeType";
    public static final String METADATA_MIME_TYPE = "someMetadataMimeType";

    @Mock
    private SessionStorage sessionStorageMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        ContextService contextService = new ContextService(sessionStorageMock);
        SessionStorage retrieved = contextService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        new ContextService(null);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_uuid() throws Exception {
        ContextService contextService = new ContextService(sessionStorageMock);

        SingleContextAdapter retrieved = contextService.context(Object.class, SOME_MIME_TYPE, SOME_UU_ID);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), Object.class);
        assertEquals(getInternalState(retrieved, "contextContentMimeType"), SOME_MIME_TYPE);
        assertEquals(getInternalState(retrieved, "uuId"), SOME_UU_ID);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType_uuid() throws Exception {
        ContextService contextService = new ContextService(sessionStorageMock);

        SingleContextAdapter retrieved = contextService.context(ClientCustomDataSource.class, SOME_MIME_TYPE, SOME_UU_ID);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientCustomDataSource.class);
        assertEquals(getInternalState(retrieved, "contextContentMimeType"), SOME_MIME_TYPE);
        assertEquals(getInternalState(retrieved, "uuId"), SOME_UU_ID);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType() throws Exception {
        ContextService contextService = new ContextService(sessionStorageMock);

        SingleContextAdapter retrieved = contextService.context(ClientCustomDataSource.class, SOME_MIME_TYPE);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientCustomDataSource.class);
        assertEquals(getInternalState(retrieved, "contextContentMimeType"), SOME_MIME_TYPE);
        assertNull(getInternalState(retrieved, "uuId"));
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_connection_metadata_class_mimeType_uuid() throws Exception {
        ContextService contextService = new ContextService(sessionStorageMock);

        SingleContextAdapter retrieved = contextService.context(SOME_UU_ID, TableMetadata.class, METADATA_MIME_TYPE);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "uuId"), SOME_UU_ID);
        assertEquals(getInternalState(retrieved, "metadataClass"), TableMetadata.class);
        assertEquals(getInternalState(retrieved, "metadataMimeType"), METADATA_MIME_TYPE);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType_metadata_class_mimeType() throws Exception {
        ContextService contextService = new ContextService(sessionStorageMock);

        SingleContextAdapter retrieved = contextService.context(
                ClientDomain.class,
                ContextMediaTypes.DOMAIN_JSON,
                ResourceGroupElement.class,
                ContextMediaTypes.DOMAIN_METADATA_JSON);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientDomain.class);
        assertEquals(getInternalState(retrieved, "contextContentMimeType"), ContextMediaTypes.DOMAIN_JSON);
        assertEquals(getInternalState(retrieved, "metadataClass"), ResourceGroupElement.class);
        assertEquals(getInternalState(retrieved, "metadataMimeType"), ContextMediaTypes.DOMAIN_METADATA_JSON);
    }
}
