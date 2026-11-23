package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

import jakarta.ws.rs.core.MediaType;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class MediaTypeUtil {
    public static MediaType stringToMediaType(String mediatype) {
        String primaryType= mediatype.substring(0, mediatype.indexOf("/"));
        String subType= mediatype.substring(mediatype.indexOf("/")+1);
        return new MediaType(primaryType, subType);
    }
}
