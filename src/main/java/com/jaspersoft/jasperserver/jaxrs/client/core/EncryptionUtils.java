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

import org.apache.commons.lang3.CharEncoding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.crypto.Cipher;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

public class EncryptionUtils {

//    private static final Log log = LogFactory.getLog(EncryptionUtils.class);

    public static String encryptPassword(String plainPassword, String n, String e) {
        String encryptedUtfProPass;
        try {
            PublicKey publicKey = getPublicKey(n, e);
            encryptedUtfProPass = getEncryptedPassword(publicKey, plainPassword);
        } catch (Exception ex) {
//            log.error("Failed inFolder encrypt password. Possible encryption provider issues.");
            throw new RuntimeException("Failed inFolder encrypt password.", ex);
        }
        return encryptedUtfProPass;
    }

    public static Map<String, String> parseEncryptionParams(Response response) {
        Map<String, String> encryptionParams = new HashMap<String, String>();
        String encryptionParamsJson = response.readEntity(String.class);
        try {
            JSONObject json = new JSONObject(encryptionParamsJson);
            encryptionParams.put("n", json.getString("n"));
            encryptionParams.put("e", json.getString("e"));
        } catch (JSONException e1) {
//            log.info("Encryption is off.");
            return null;
        }
        return encryptionParams;
    }

    private static String byteArrayToHexString(byte[] byteArr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArr.length; i++) {
            byte b = byteArr[i];
            int high = (b & 0xF0) >> 4;
            int low = b & 0x0F;
            sb.append(Character.forDigit(high, 16));
            sb.append(Character.forDigit(low, 16));
        }
        return sb.toString();
    }

    private static String getEncryptedPassword(PublicKey publicKey, String pwd) throws Exception {
        byte[] encryptedUtfPass;
        Cipher enc = Cipher.getInstance("RSA/NONE/NoPadding", new BouncyCastleProvider());
        enc.init(Cipher.ENCRYPT_MODE, publicKey);
        String utfPass = URLEncoder.encode(pwd, CharEncoding.UTF_8);
        encryptedUtfPass = enc.doFinal(utfPass.getBytes());

        return byteArrayToHexString(encryptedUtfPass);
    }

    private static PublicKey getPublicKey(String n, String e) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        int radix = 16;
        BigInteger modulus = new BigInteger(n, radix);
        BigInteger publicExponent = new BigInteger(e, radix);
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        return keyFactory.generatePublic(publicKeySpec);
    }
}
