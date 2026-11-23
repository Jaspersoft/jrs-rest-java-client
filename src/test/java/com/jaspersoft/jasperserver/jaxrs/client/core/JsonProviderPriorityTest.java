package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.importexport.ExportTask;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.DateTimeSettings;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Guards against Jersey 3 picking its own Jackson writer instead of CustomRepresentationTypeProvider.
 * Uses an in-process JDK HTTP server, so no JasperReports Server is needed.
 */
public class JsonProviderPriorityTest {

    private HttpServer server;
    private final AtomicReference<String> requestBody = new AtomicReference<>();
    private volatile String responseJson = "{}";

    @BeforeMethod
    public void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            requestBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            byte[] out = responseJson.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, out.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(out);
            }
        });
        server.start();
    }

    @AfterMethod(alwaysRun = true)
    public void stopServer() {
        server.stop(0);
    }

    private WebTarget rootTarget() {
        RestClientConfiguration cfg =
                new RestClientConfiguration("http://127.0.0.1:" + server.getAddress().getPort());
        return new SessionStorage(cfg, new AuthenticationCredentials("user", "pass"),
                Locale.US, TimeZone.getDefault()).getRootTarget();
    }

    @Test
    public void exportTask_isWrittenWithPluralServerNames() {
        ExportTask task = new ExportTask()
                .setUris(Collections.singletonList("/public"))
                .setRoles(Collections.singletonList("ROLE_USER"))
                .setUsers(Collections.singletonList("joeuser"));

        rootTarget().path("rest_v2/export").request().post(Entity.json(task)).close();

        String json = requestBody.get();
        assertTrue(json.contains("\"uris\""), json);
        assertTrue(json.contains("\"roles\""), json);
        assertTrue(json.contains("\"users\""), json);
        assertFalse(json.contains("\"uri\""), json);
        assertFalse(json.contains("\"role\""), json);
    }

    @Test
    public void clientUser_isWrittenWithRolesArray() {
        ClientRole role = new ClientRole().setName("ROLE_ADMINISTRATOR");
        ClientUser user = new ClientUser().setUsername("joeuser")
                .setRoleSet(new java.util.HashSet<>(Collections.singletonList(role)));

        rootTarget().path("rest_v2/users/joeuser").request().put(Entity.json(user)).close();

        String json = requestBody.get();
        assertTrue(json.contains("\"roles\""), json);
        assertFalse(json.contains("\"role\""), json);
    }

    @Test
    public void errorDescriptor_isReadFromPluralServerNames() {
        responseJson = "{\"errorCode\":\"some.error\",\"message\":\"m\",\"parameters\":[\"p1\",\"p2\"]}";

        ErrorDescriptor error = rootTarget().path("rest_v2/anything").request().get(ErrorDescriptor.class);

        assertNotNull(error.getParameters(), "parameters not read from JSON");
        assertEquals(Arrays.asList(error.getParameters()), Arrays.asList("p1", "p2"));
    }

    /**
     * The server sends fields the DTO does not declare (e.g. timepicker.millisecTimeFormat / timeSuffix
     * from /rest_v2/settings/dateTimeSettings). They must be skipped, not break the whole read.
     */
    @Test
    public void dateTimeSettings_withUnknownFields_isRead() {
        responseJson = "{\"timepicker\":{\"timeFormat\":\"HH:mm:ss\",\"millisecTimeFormat\":\"HH:mm:ss.l\","
                + "\"timeSuffix\":\"\",\"unknownArray\":[\"a\",\"b\"],\"unknownObject\":{\"x\":{\"y\":1}},"
                + "\"amNames\":[\"a.m.\",\"AM\",\"\"]},"
                + "\"datepicker\":{\"dateFormat\":\"dd-mm-yy\",\"unknownScalar\":7,"
                + "\"monthNamesShort\":[\"Ene\",\"Feb\"],\"dayNamesMin\":[\"Do\",\"Lu\"]}}";

        DateTimeSettings settings = rootTarget().path("rest_v2/settings/dateTimeSettings").request()
                .get(DateTimeSettings.class);

        assertNotNull(settings.getDatepicker(), "datepicker not read");
        assertEquals(settings.getDatepicker().getMonthNamesShort(), Arrays.asList("Ene", "Feb"));
        assertEquals(settings.getDatepicker().getDayNamesMin(), Arrays.asList("Do", "Lu"));
        assertNotNull(settings.getTimepicker(), "timepicker not read");
        assertEquals(settings.getTimepicker().getTimeFormat(), "HH:mm:ss");
        assertEquals(settings.getTimepicker().getAmNames(), Arrays.asList("a.m.", "AM", ""));
    }
}
