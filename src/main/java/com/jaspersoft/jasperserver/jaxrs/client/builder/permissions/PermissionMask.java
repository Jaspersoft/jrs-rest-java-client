package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;


public final class PermissionMask {

    public static final int NO_ACCESS = 0;
    public static final int ADMINISTER = 1;
    public static final int READ_ONLY = 2;
    public static final int READ_WRITE = 6;
    public static final int READ_DELETE = 18;
    public static final int READ_WRITE_DELETE = 30;
    public static final int EXECUTE = 32;

    private PermissionMask(){}
}
