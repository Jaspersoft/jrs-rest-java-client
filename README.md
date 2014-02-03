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
There are two approaches to run a report - in synchronous and asynchronous modes.
To run report in synchronous mode you can use the code below:
```java
OperationResult<InputStream> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .reportingService()
        .report("/reports/samples/Cascading_multi_select_report")
        .prepareForRun(ReportOutputFormat.HTML, 1)
        .parameter("Cascading_name_single_select", "A & U Stalker Telecommunications, Inc")
        .run();
InputStream report = result.getEntity();
```
In this mode you don't need to work in one session. In the above code we specified report URI, format in which we want to get a report and some report parameters. As we a result we got `InputStream` instance. In synchronous mode as a response you get a report itself while in asynchronous you get just a descriptor with report ID which you can use to download report afer it will be ready.

In order to run a report in asynchronous mode, you need firstly build `ReportExecutionRequest` instance and specify all the parameters needed to launch a report. The response from the server is the `ReportExecutionDescriptor` instance which contains the request ID needed to track the execution until completion and others report parameters. Here's the code to run a report:
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
You can search for reports that are running on the server, including
report jobs triggered by the scheduler.
To search for running reports, use the search arguments from `ReportAndJobSearchParameter` enumeration.
```java
OperationResult<ReportExecutionListWrapper> operationResult =
        session
                .reportingService()
                .runningReportsAndJobs()
                .parameter(ReportAndJobSearchParameter.REPORT_URI, "/reports/samples/AllAccounts")
                .find();

ReportExecutionListWrapper entity = operationResult1.getEntity();
```
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
Only administrative users may access the REST services for administration.

###Users service
It provides methods that allow you to list, view, create, modify, and delete user accounts, including setting role membership.
Because the user ID is used in the URL, this service can operate only on users whose ID is less than 100 characters long and does not contain spaces or special symbols. As with resource IDs, the user ID is permanent and cannot be modified for the life of the user account.
####Searching for Users
You can search for users by name or by role. If no search is specified, service returns all users.
```java
OperationResult<UsersListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .allUsers()
                .param(UsersParameter.REQUIRED_ROLE, "ROLE_USER")
                .get();

UsersListWrapper usersListWrapper = operationResult.getEntity();
```
####Viewing a User
Method `username()` with a user ID (username) retrieves a single descriptor containing the full list of user properties and roles.
```java
OperationResult<ClientUser> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .get();

ClientUser user = operationResult.getEntity();
```
The full user descriptor includes detailed information about the user account, including any roles.
####Creating a User
To create a user account, put all required information in a user descriptor `ClientUser`, and include it in a request to the users service (`createOrUpdate()` method), with the intended user ID (username) specified in the `username()` method. To create a user, the user ID in the `username()` method must be unique on the server. If the user ID already exists, that user account will be modified. The descriptor sent in the request should contain all the properties you want to set on the new user, except for the username that is specified in the `username()` method. To set roles on the user, specify them as a list of roles.
```java
//Creating a user
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
    
//Granting new user with admin role    
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
####Modifying User Properties
To modify the properties of a user account, put all desired information in a user descriptor (`ClientUser`), and include it in a request to the users service (`createOrUpdate()` method), with the existing user ID (username) specified in the `username()` method. To modify a user, the user ID must already exist on the server. If the user ID doesn’t exist, a user account will be created. To add a role to the user, specify the entire list of roles with the desired role added. To remove a role from a user, specify the entire list of roles without the desired role removed.
```java
ClientUser user = new ClientUser()
        .setUsername("john.doe")
        .setPassword("12345678")
        .setEmailAddress("john.doe@email.net")
        .setEnabled(true)
        .setExternallyDefined(false)
        .setFullName("Bob");                    //field to be updated

client
    .authenticate("jasperadmin", "jasperadmin")
    .usersService()
    .username(user.getUsername())
    .createOrUpdate(user);
```
####Deleting a User
To delete a user, call the `delete()` method and specify the user ID in the `username()` method.
```java
client
    .authenticate("jasperadmin", "jasperadmin")
    .usersService()
    .username(user.getUsername())
    .delete();
```

Attributes service
--------------------
Attributes, also called profile attributes, are name-value pairs associated with a user. Certain advanced features such as Domain security and OLAP access grants use profile attributes in addition to roles to grant certain permissions. Unlike roles, attributes are not pre-defined, and thus any attribute name can be assigned any value at any time.
Attributes service provides methods for reading, writing, and deleting attributes on any given user account. All attribute operations apply to a single specific user; there are no operations for reading or searching attributes from multiple users.
Because the user ID is used in the URL, this service can operate only on users whose ID is less than 100 characters long and does not contain spaces or special symbols. In addition, both attribute names and attribute values being written with this service are limited to 255 characters and may not be empty (null) or contain only whitespace characters.
####Viewing User Attributes
The code below retrieves the list of attributes, if any, defined for the user.
```java
OperationResult<UserAttributesListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attributes()
                .get();

UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();
```
The list of attributes includes the name and value of each attribute. Each attribute may only have one value, however that value may contain a comma-separated list that is interpreted by the server as being multi-valued.

An alternative syntax exists to read a single attribute by specifying its name in the `attribute()` method:
```java
OperationResult<ClientUserAttribute> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attribute("testAttribute")
                .get();

ClientUserAttribute attribute = operationResult.getEntity();
```
The response is a single attribute name-value pair.
####Setting User Attributes
The `createOrUpdate()` method of the attributes service adds or replaces attributes on the specified user. The list of attributes defines the name and value of each attribute. Each attribute may only have one value, however, that value may contain a comma separated list that is interpreted by the server as being multi-valued.
There are two syntaxes, the following one is for adding or replacing all attributes
```java
List<ClientUserAttribute> userAttributes = new ArrayList<ClientUserAttribute>();
userAttributes.add(new ClientUserAttribute()
        .setName("attr1")
        .setValue("val1"));
userAttributes.add(new ClientUserAttribute()
        .setName("attr2")
        .setValue("val2"));

UserAttributesListWrapper listWrapper = new UserAttributesListWrapper(userAttributes);
OperationResult<UserAttributesListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attributes()
                .createOrUpdate(listWrapper);

Response response = operationResult.getResponse();
```
The second syntax of the attributes service is for adding or replacing individual attributes.
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
####Deleting User Attributes
The `delete()` method of the attributes service removes attributes from the specified user. When attributes are
removed, both the name and the value of the attribute are removed, not only the value.
There are two syntaxes, the following one is for deleting multiple attributes or all attributes at once.
```java
OperationResult<UserAttributesListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attributes()
                .delete();

Response response = operationResult.getResponse();
```
The second syntax deletes a single attribute named in the `username()` from the specified user.
```java
OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attribute(clientUserAttribute.getName())
                .createOrUpdate(clientUserAttribute);

Response response = operationResult.getResponse();
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

