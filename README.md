jasperserver-rest-client
========================

1) Maven dependency to add jasperserver-rest-client to your app:

    <dependencies>
        <dependency>
            <groupId>com.jaspersoft</groupId>
            <artifactId>jasperserver-jaxrs-client</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>my.mvn.repo</id>
            <url>https://github.com/boryskolesnykov/jasperserver-rest-client/tree/master/mvn-repo</url>
            <!-- use snapshot version -->
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>


