jasperserver-rest-client
========================

###Jasperserver client instantiation:
```
    //RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
    RestClientConfiguration configuration = RestClientConfiguration("http://localhost:8080/jasperserver/rest_v2");
    JasperserverRestClient client = new JasperserverRestClient(configuration);
```
###Client for authority services samples:

####New user creation:
```
        ClientUser user = new ClientUser()
                .setUsername("john.doe")
                .setPassword("12345678")
                .setEmailAddress("john.doe@email.net")
                .setEnabled(true)
                .setExternallyDefined(false)
                .setFullName("John Doe");

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .createOrUpdate(user);
```
####Granting new user with admin role:
```
        ClientRole role = client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename("ROLE_ADMINISTRATOR")
                .get()
                .getEntity();

        Set<ClientRole> roles = new HashSet<ClientRole>();
        roles.add(role);
        user.setRoleSet(roles);

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .createOrUpdate(user);
```
Firstly we requested an admin role entity and then updated existing user with a new role.

####Addition of some user' attributes:
```
        ClientUserAttribute attribute = new ClientUserAttribute()
                        .setName("someAttribute")
                        .setValue("hello");

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .attribute(attribute.getName())
            .createOrUpdate(attribute);
```
####Creating permission for user to do something with some resource:
```
        RepositoryPermission permission = new RepositoryPermission()
            .setRecipient("user:/john.doe")
            .setUri("/")
            .setMask(PermissionMask.READ_ONLY);

        client
            .authenticate("jasperadmin", "jasperadmin")
            .permissionsService()
            .create(permission);
```
####Deleting of user' attributes:
```
        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .attribute("someAttribute")
            .delete();
```
####Deleting of permissions:
```
        client
            .authenticate("jasperadmin", "jasperadmin")
            .permissionsService()
            .resource("/")
            .permissionRecipient(PermissionRecipient.USER, "john.doe")
            .delete();
```
####Deleting of users:
```
        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .delete();
```

###Maven dependency to add jasperserver-rest-client to your app:
```
    <dependencies>
        <dependency>
            <groupId>com.jaspersoft</groupId>
            <artifactId>jasperserver-jaxrs-client</artifactId>
            <version>0.9.1</version>
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
```

