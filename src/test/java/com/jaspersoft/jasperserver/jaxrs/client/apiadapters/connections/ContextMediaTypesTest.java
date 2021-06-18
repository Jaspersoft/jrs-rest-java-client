package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes}
 */
public class ContextMediaTypesTest {

    @Test
    public void should_return_proper_value() {
        assertEquals(ContextMediaTypes.FTP_JSON, "application/connections.ftp+json");
        assertEquals(ContextMediaTypes.FTP_XML, "application/connections.ftp+xml");
        assertEquals(ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON, "application/connections.lfs+json");
        assertEquals(ContextMediaTypes.LOCAL_FILE_SYSTEM_XML, "application/connections.lfs+xml");
        assertEquals(ContextMediaTypes.CUSTOM_DATA_SOURCE_JSON, "application/repository.customDataSource+json");
        assertEquals(ContextMediaTypes.CUSTOM_DATA_SOURCE_XML, "application/repository.customDataSource+xml");
        assertEquals(ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_JSON, "application/repository.jndiJdbcDataSource+json");
        assertEquals(ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_XML, "application/repository.jndiJdbcDataSource+xml");
        assertEquals(ContextMediaTypes.JDBC_DATA_SOURCE_JSON, "application/repository.jdbcDataSource+json");
        assertEquals(ContextMediaTypes.JDBC_DATA_SOURCE_XML, "application/repository.jdbcDataSource+xml");
        assertEquals(ContextMediaTypes.DOMAIN_DATA_SOURCE_JSON, "application/repository.semanticLayerDataSource+json");
        assertEquals(ContextMediaTypes.DOMAIN_DATA_SOURCE_XML, "application/repository.semanticLayerDataSource+xml");
        assertEquals(ContextMediaTypes.TXT_FILE_JSON, "application/connections.txtFile+json");
        assertEquals(ContextMediaTypes.TXT_FILE_XML, "application/connections.txtFile+xml");
        assertEquals(ContextMediaTypes.XLS_FILE_JSON, "application/connections.xlsFile+json");
        assertEquals(ContextMediaTypes.XLS_FILE_XML, "application/connections.xlsFile+xml");
    }

    @Test
    public void constructor_of_enum_should_be_private() {
        Constructor<?>[] constructors = ContextMediaTypes.class.getDeclaredConstructors();
        assertFalse(constructors[0].isAccessible());
    }

    @Test
    public void magic() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<ContextMediaTypes> constructor = ContextMediaTypes.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ContextMediaTypes instance = constructor.newInstance();

        assertNotNull(instance);
    }
}