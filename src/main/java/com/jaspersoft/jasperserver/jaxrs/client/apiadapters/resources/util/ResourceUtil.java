package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import jakarta.xml.bind.DatatypeConverter;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ResourceUtil {
    public static String toBase64EncodedContent(InputStream content) {
        String emptyString = "";
        BufferedReader buf = new BufferedReader(new InputStreamReader(content));

        StringBuilder sb = new StringBuilder();
        String line = new String();
        while(line != null){
            sb.append(line).append("\n");
            try {
                line = buf.readLine();
            } catch (IOException e) {
                return emptyString;
            }
        }

        return toBase64EncodedContent(sb.toString());
    }

    public static String toBase64EncodedContent(String string) {
        try {
            return  DatatypeConverter.printBase64Binary(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
