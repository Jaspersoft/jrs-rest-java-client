package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.license;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.Map;

/**
 * Client for the server's <code>/rest_v2/licenseFeatures</code> service, which reports
 * which license features are enabled or disabled. Responses are maps of feature name to boolean.
 *
 * @author Ambalal Nama
 */
public class LicenseService extends AbstractAdapter {

    public static final String SERVICE_URI = "licenseFeatures";

    public LicenseService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    /**
     * Retrieves all enabled license features.
     * The server responds with 404 if the license is not accepted.
     */
    @SuppressWarnings("rawtypes")
    public OperationResult<Map> allFeatures() {
        return JerseyRequest.buildRequest(sessionStorage, Map.class, new String[]{SERVICE_URI}).get();
    }

    /**
     * Retrieves the state of a single license feature, e.g. "ef", "aud", "wl".
     * The server responds with 404 if the feature is unknown.
     *
     * @param featureName license feature name
     */
    @SuppressWarnings("rawtypes")
    public OperationResult<Map> feature(String featureName) {
        if (featureName == null || featureName.isEmpty()) {
            throw new IllegalArgumentException("featureName is null or empty");
        }
        return JerseyRequest.buildRequest(sessionStorage, Map.class, new String[]{SERVICE_URI, featureName}).get();
    }
}