package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ConnectionMediaType;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.core.enums.ConnectionMediaType}
 */
public class ConnectionMediaTypeTest {

    @Test
    public void should_return_proper_value() {
        assertEquals(ConnectionMediaType.FTP_JSON, "application/connections.ftp+json");
        assertEquals(ConnectionMediaType.FTP_XML, "application/connections.ftp+xml");
        assertEquals(ConnectionMediaType.LOCAL_FILE_SYSTEM_JSON, "application/connections.lfs+json");
        assertEquals(ConnectionMediaType.LOCAL_FILE_SYSTEM_XML, "application/connections.lfs+xml");
        assertEquals(ConnectionMediaType.TXT_FILE_JSON, "application/connections.txtFile+json");
        assertEquals(ConnectionMediaType.TXT_FILE_XML, "application/connections.txtFile+xml");
        assertEquals(ConnectionMediaType.XLS_FILE_JSON, "application/connections.xlsFile+json");
        assertEquals(ConnectionMediaType.XLS_FILE_XML, "application/connections.xlsFile+xml");
    }

    @Test
    public void constructor_of_enum_should_be_private() {
        Constructor<?>[] constructors = ConnectionMediaType.class.getDeclaredConstructors();
        assertFalse(constructors[0].isAccessible());
    }

    @Test
    public void magic() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<ConnectionMediaType> constructor = ConnectionMediaType.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ConnectionMediaType instance = constructor.newInstance();

        assertNotNull(instance);
    }
}