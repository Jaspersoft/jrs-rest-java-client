package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersUtils.toPathSegment;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersUtils}
 *
 **
 * @deprecated should be deleted after deleted deprecated {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersUtils
 */
@PrepareForTest(ReportParametersUtils.class)
public class ReportParametersUtilsTest extends PowerMockTestCase {

    @Test
    public void should_return_new_instance_of_ReportParameters_constructed_with_proper_parameters() {

        /* Given */
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>() {{
            add("key1", "value1_1");
            add("key1", "value1_2");
            add("key2", "value2_1");
            add("key2", "value2_2");
        }};

        /* When */
        ReportParameters retrieved = ReportParametersUtils.toReportParameters(params);
        List<ReportParameter> parameters = retrieved.getReportParameters();

        /* Then */
        assertNotNull(retrieved);
        assertFalse(parameters.isEmpty());
        assertTrue(parameters.size() == 2);
    }

    @Test
    public void should_convert_list_of_IC_Ids_into_proper_path_segment() {

        /* Given */
        List<String> inputControlsIds = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};

        /* When */
        String retrieved = toPathSegment(inputControlsIds);

        /* Then */
        assertNotNull(retrieved);
        assertEquals(retrieved, "a;b;c;");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void should_throw_an_exception_when_inputControlsIds_is_null() {
        toPathSegment(null);
    }

    @Test
    public void should_invoke_static_method_of_ReportParametersUtils_class() {

        /* Given */
        PowerMockito.mockStatic(ReportParametersUtils.class);

        List<String> inputControlsIds = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};

        ReportParametersUtils.toPathSegment(inputControlsIds);

        verifyStatic(times(1));
        ReportParametersUtils.toPathSegment(inputControlsIds);
    }

    @Test
    public void should_create_instance_of_class() {
        assertNotNull(new ReportParametersUtils());
    }
}