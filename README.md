jrs-rest-client-java
========================

Introduction
-------------
With this library you can easily write Java applications which can interact with one or more JasperReports servers simultaneously in a very simple way. Library provides very friendly API for user, it minimizes possibility of building wrong requests. To use library in your maven-based application you need just to specify dependency and repository which are given below or download jar file manually from 
```
https://github.com/boryskolesnykov/jasperserver-rest-client/blob/master/mvn-repo/com/jaspersoft/jasperserver-jaxrs-client/{version}/jasperserver-jaxrs-client-{version}.jar
```

Configuration
-------------
To start working with the library you should firstly configure one ore more instances of `JasperserverRestClient`.
To do this you should create instance of `RestClientConfiguration`. It can be done in two ways:
####Loading configuration from file: 
```java
RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
```
File should contain only URL which is entry point to your server's REST services and it is needed to URL  corresponds to this pattern `{protocol}://{host}:{port}/{contextPath}/rest_v2`.
####Creation of manual configuration
```java
RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:8080/jasperserver/rest_v2");
```
####Client instantiation:
Here everything is easy, you need just to pass `configuration` to `JasperserverRestClient` constructor.
```java
JasperserverRestClient client = new JasperserverRestClient(configuration);
```

Reporting services
------------------
After you've configured the client you can easily use any of available services. For reporting service there is one feature that should be noted - when you are running a report all subsequent operations must be executed in the same session. Here's the code:
```java
Session session = client.authenticate("jasperadmin", "jasperadmin");
```
We've authenticated as `jasperadmin` user an got a session for this user, all subsequent operations must be done through this session instance.
####Running a report:
In order to run a report, you need firstly build `ReportExecutionRequest` instance and specify all the parameters needed to launch a report. The response from the server is the `ReportExecutionDescriptor` instance which contains the request ID needed to track the execution until completion and others report parameters. Here's the code to run a report:
```java
//instantiating request and specifying report parameters
ReportExecutionRequest request = new ReportExecutionRequest();
request.setReportUnitUri("/reports/samples/StandardChartsReport");
request
        .setAsync(true)                         //this means that report will be run on server asynchronously
        .setOutputFormat("html");               //report can be requested in different formats e.g. html, pdf, etc.

OperationResult<ReportExecutionDescriptor> operationResult =        
        session                                 //pay attention to this, all requests are in the same session!!!
                .reportingService()
                .newReportExecutionRequest(request);

reportExecutionDescriptor = operationResult.getEntity();
```
In the above code we've created `ReportExecutionRequest` instance and sent it to JR server through the `newReportExecutionRequest` method. As a response we've got `OperationResult` instance which contains HTTP response wrapper and instance of `ReportExecutionDescriptor` which we can get with `operationResult.getEntity()`.
####Requesting report execution status:
After you've got `ReportExecutionDescriptor` you can request for the report execution status:
```java
OperationResult<ReportExecutionStatusEntity> operationResult =
        session                                 //pay attention to this, all requests are in the same session!!!
                .reportingService()
                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                .status();

ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
```
In the above code we've just specified request ID and got its status as a `ReportExecutionStatusEntity` instance.
####Requesting report execution details:
Once the report is ready, your client must determine the names of the files to download by requesting the
reportExecution descriptor again.
```java
OperationResult<ReportExecutionDescriptor> operationResult =
        session                                 //pay attention to this, all requests are in the same session!!!
                .reportingService()
                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                .executionDetails();

ReportExecutionDescriptor descriptor = operationResult.getEntity();
```
####Requesting Report Output
After requesting a report execution and waiting synchronously or asynchronously for it to finish, you are ready to download the report output. Every export format of the report has an ID that is used to retrieve it. For example, the HTML export has the ID html. To download the main report output, specify this export ID in the `export` method. For example, to download the main HTML of the report execution response above, use the following code:
```java
OperationResult<InputStream> operationResult =
        session                                 //pay attention to this, all requests are in the same session!!!
                .reportingService()
                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                .export("html")
                .outputResource();

InputStream file = operationResult.getEntity();
```
As a response you'll get an `InputStream` instance.
####Download file attachments for report output:
To download file attachments for HTML output, use the following code. You must download all attachments to display the HMTL content properly.
```java
ExportDescriptor htmlExportDescriptor = ... //retrieving htmlExportDescriptor from reportExecutionDescriptor

for(AttachmentDescriptor attDescriptor : htmlExportDescriptor.getAttachments()){
    OperationResult<InputStream> operationResult =
            session                             //pay attention to this, all requests are in the same session!!!
                    .reportingService()
                    .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                    .export(htmlExportDescriptor.getId())
                    .attachment(attDescriptor.getFileName());
    
    InputStream file = operationResult.getEntity();
    //doing something with file
}
```
####Exporting a Report Asynchronously
After running a report and downloading its content in a given format, you can request the same report in other formats. As with exporting report formats through the user interface, the report does not run again because the export process is independent of the report.
```java
ExportExecutionOptions exportExecutionOptions = new ExportExecutionOptions()
        .setOutputFormat("pdf")
        .setPages("3");

OperationResult<ExportExecutionDescriptor> operationResult =
        session
                .reportingService()
                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                .runExport(exportExecutionOptions);

ExportExecutionDescriptor statusEntity = operationResult.getEntity();
```
####Polling Export Execution
As with the execution of the main report, you can also poll the execution of the export process.
For example, to get the status of the HTML export in the previous example, use the following code:
```java
OperationResult<ReportExecutionStatusEntity> operationResult =
        session                                 //pay attention to this, all requests are in the same session!!!
                .reportingService()
                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                .export("html")
                .status();

ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
```
####Finding Running Reports and Jobs
//TODO: impl
####Stopping Running Reports and Jobs
To stop a report that is running and cancel its output, use the code below:
```java
OperationResult<ReportExecutionStatusEntity> operationResult1 =
        session
                .reportingService()
                .reportExecutionRequest(executionDescriptor.getRequestId())
                .cancelExecution();

ReportExecutionStatusEntity statusEntity = operationResult1.getEntity();
```

Administration services:
------------------------
####New user creation:
```java
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
```java
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
```java
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
```java
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
```java
        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .attribute("someAttribute")
            .delete();
```
####Deleting of permissions:
```java
        client
            .authenticate("jasperadmin", "jasperadmin")
            .permissionsService()
            .resource("/")
            .permissionRecipient(PermissionRecipient.USER, "john.doe")
            .delete();
```
####Deleting of users:
```java
        client
            .authenticate("jasperadmin", "jasperadmin")
            .usersService()
            .username(user.getUsername())
            .delete();
```

###Maven dependency to add jasperserver-rest-client to your app:
```xml
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

