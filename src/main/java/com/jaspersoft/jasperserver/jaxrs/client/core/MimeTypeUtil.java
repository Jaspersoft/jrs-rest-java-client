/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core;

public class MimeTypeUtil {

    private static final String JSON_SUFFIX = "json";
    private static final String XML_SUFFIX = "xml";

    public static String toCorrectContentMime(RestClientConfiguration configuration, String srcMime) {
        if (srcMime.endsWith("{mime}")) {
            return srcMime.replace("{mime}", configuration.getContentMimeType() == MimeType.JSON ? JSON_SUFFIX : XML_SUFFIX);
        }
        return srcMime;
    }

    public static String toCorrectAcceptMime(RestClientConfiguration configuration, String srcMime) {
        if (srcMime.endsWith("{mime}")) {
            return srcMime.replace("{mime}", configuration.getAcceptMimeType() == MimeType.JSON ? JSON_SUFFIX : XML_SUFFIX);
        }
        return srcMime;
    }
}
