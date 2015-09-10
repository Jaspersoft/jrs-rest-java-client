package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.core;

/**
 * @author Tetiana Iefimenko
 */
public class TMP {
    public static void main(String[] args) {

        String s = "[^?]+\\?([^&]*&)*error=1(&[^&]*)*$";
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?error=2&error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?error=1&error=2".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-pro/login.html?error=1&error=2".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-pro/login.html?error=1&".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-p&ro/login.html?error=1&".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-p&ro/login.html?error=1&&".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-p&ro/login.html?error=1&dfdf?&".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html??&error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?&error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pr&o/login.html?&error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?&error=1".matches(s));
        System.out.println("http://jasperserver.com/jasperserver-p&ro/login.html??&error=1&dfdf?&".matches(s));
        System.out.println();
        System.out.println("http://jasperserver.com/jasperserver-p&ro/login.html??error=1&dfdf?&".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?error=11".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?rtr&gfgfderror=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html?fdhdfh=fbfhb?error=1".matches(s));
        System.out.println("http://localhost:8080/jasperserver-pro/login.html&error=1".matches(s));
    }
}
