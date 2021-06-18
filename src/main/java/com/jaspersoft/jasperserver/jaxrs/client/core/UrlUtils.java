/*
 * Copyright © 2014-2018. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatyana Matveyeva
 * @version $Id$
 */
public class UrlUtils {

    private UrlUtils() {
        //To prevent class from instantiation
        throw new AssertionError();
    }

    /**
     * Encode provided string (curly braces)
     * @param param string to encode
     * @return encoded string
     */
    public static String encode(String param) {
            return param.replaceAll("\\}", "%7D").replaceAll("\\{", "%7B");

    }

    /**
     * Encode provided strings (curly braces)
     * @param params list of strings to encode
     * @return encoded list of strings
     */
    public static List<String> encode(List<String> params) {
        List<String> result = new ArrayList<>();
        for (String param: params) {
            result.add(param.replaceAll("\\}", "%7D").replaceAll("\\{", "%7B"));
        }
        return result;
    }
}
