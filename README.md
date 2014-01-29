jasperserver-rest-client
========================

1) Jasperserver client instantiation:

    //RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
    RestClientConfiguration configuration = RestClientConfiguration("http://localhost:8080/jasperserver/rest_v2");
    JasperserverRestClient client = new JasperserverRestClient(configuration);

2) Client for authority services samples:

     2.1) New user creation:

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

    2.2) Granting new user with admin role:

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

        Firstly we requested an admin role entity and then updated existing user with a new role.

    2.3) Addition of some user' attributes:

        ClientUserAttribute attribute = new ClientUserAttribute()
                        .setName("someAttribute")
                        .setValue("hello");

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .attribute(attribute.getName())
            .createOrUpdate(attribute);

    2.4) Creating permission for user to do something with some resource:

        RepositoryPermission permission = new RepositoryPermission()
            .setRecipient("user:/john.doe")
            .setUri("/")
            .setMask(PermissionMask.READ_ONLY);

        client
            .authenticate("jasperadmin", "jasperadmin")
            .permissionsService()
            .create(permission);

    2.5) Deleting of user' attributes:

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .attribute("someAttribute")
            .delete();

    2.6) Deleting of permissions:

        client
            .authenticate("jasperadmin", "jasperadmin")
            .permissionsService()
            .resource("/")
            .permissionRecipient(PermissionRecipient.USER, "john.doe")
            .delete();

    2.7) Deleting of users:

        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .delete();


3) Maven dependency to add jasperserver-rest-client to your app:

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


