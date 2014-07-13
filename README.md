Rest Client for JasperReports Server [![Build Status](https://travis-ci.org/Krasnyanskiy/jrs-rest-java-client.svg?branch=develop)](https://travis-ci.org/Krasnyanskiy/jrs-rest-java-client) [![Coverage Status](https://coveralls.io/repos/Krasnyanskiy/jrs-rest-java-client/badge.png?branch=develop)](https://coveralls.io/r/Krasnyanskiy/jrs-rest-java-client?branch=develop)
=========================================

With this library you can easily write Java applications which can interact with one or more JasperReports servers simultaneously in a very simple way. Library provides very friendly API for user, it minimizes possibility of building wrong requests.

Table of Contents
------------------
1. [Introduction](#introduction).
2. [Configuration](#configuration).
  * [Loading configuration from file](#loading-configuration-from-file).
  * [Creation of manual configuration](#creation-of-manual-configuration).
  * [HTTPS configuration](#https-configuration).
  * [Client instantiation](#client-instantiation).
3. [Authentication](#authentication).
  * [Invalidating session](#invalidating-session).
4. [Report services](#report-services).
  * [Running a report](#running-a-report).
  * [Requesting report execution status](#requesting-report-execution-status).
  * [Requesting report execution details](#requesting-report-execution-details).
  * [Requesting Report Output](#requesting-report-output).
  * [Download file attachments for report output](#download-file-attachments-for-report-output).
  * [Exporting a Report Asynchronously](#exporting-a-report-asynchronously).
  * [Polling Export Execution](#polling-export-execution).
  * [Finding Running Reports and Jobs](#finding-running-reports-and-jobs).
  * [Stopping Running Reports and Jobs](#stopping-running-reports-and-jobs).
5. [Report Parameters services](#report-parameters-services).
  * [Listing Report Parameters Structure](#listing-report-parameters-structure).
  * [Listing Report Parameter Values](#listing-report-parameter-values).
  * [Setting Report Parameter Values](#setting-report-parameter-values).
6. [Administration services](#administration-services).
  1. [Organizations service](#organizations-service).
    * [Searching for Organizations](#searching-for-organizations).
    * [Viewing an Organization](#viewing-an-organization).
    * [Creating an Organization](#creating-an-organization).
    * [Modifying Organization Properties](#modifying-organization-properties).
    * [Deleting an Organization](#deleting-an-organization).
  2. [Users service](#users-service).
    * [Searching for Users](#searching-for-users).
    * [Viewing a User](#viewing-a-user).
    * [Creating a User](#creating-a-user).
    * [Modifying User Properties](#modifying-user-properties).
    * [Deleting a User](#deleting-a-user).
  3. [Attributes service](#attributes-service).
    * [Viewing User Attributes](#viewing-user-attributes).
    * [Setting User Attributes](#setting-user-attributes).
    * [Deleting User Attributes](#deleting-user-attributes).
  4. [The Roles Service](#the-roles-service).
    * [Searching for Roles](#searching-for-roles).
    * [Viewing a Role](#viewing-a-role).
    * [Creating a Role](#creating-a-role).
    * [Modifying a Role](#modifying-a-role).
    * [Setting Role Membership](#setting-role-membership).
    * [Deleting a Role](#deleting-a-role).
7. [Repository Services](#repository-services).
  1. [Resources Service](#resources-service).
    * [Searching the Repository](#searching-the-repository).
    * [Viewing Resource Details](#viewing-resource-details).
    * [Downloading File Resources](#downloading-file-resources).
    * [Creating a Resource](#creating-a-resource).
    * [Modifying a Resource](#modifying-a-resource).
    * [Copying a Resource](#copying-a-resource).
    * [Moving a Resource](#moving-a-resource).
    * [Uploading File Resources](#uploading-file-resources).
    * [Deleting Resources](#deleting-resources).
  2. [The Permissions Service](#the-permissions-service).
    * [Viewing Multiple Permissions](#viewing-multiple-permissions).
    * [Viewing a Single Permission](#viewing-a-single-permission).
    * [Setting Multiple Permissions](#setting-multiple-permissions).
    * [Setting a Single Permission](#setting-a-single-permission).
    * [Deleting Permissions in Bulk](#deleting-permissions-in-bulk).
    * [Deleting a Single Permission](#deleting-a-single-permission).
8. [Jobs service](#jobs-service).
  * [Listing Report Jobs](#listing-report-jobs).
  * [Viewing a Job Definition](#viewing-a-job-definition).
  * [Extended Job Search](#extended-job-search).
  * [Scheduling a Report](#scheduling-a-report).
  * [Viewing Job Status](#viewing-job-status).
  * [Editing a Job Definition](#editing-a-job-definition).
  * [Updating Jobs in Bulk](#updating-jobs-in-bulk).
  * [Pausing Jobs](#pausing-jobs).
  * [Resuming Jobs](#resuming-jobs).
  * [Restarting Failed Jobs](#restarting-failed-jobs).
9. [Calendars service](#calendars-service).
  * [Listing All Registered Calendar Names](#listing-all-registered-calendar-names).
  * [Viewing an Exclusion Calendar](#viewing-an-exclusion-calendar).
  * [Adding or Updating an Exclusion Calendar](#adding-or-updating-an-exclusion-calendar).
  * [Deleting an Exclusion Calendar](#deleting-an-exclusion-calendar).
10. [Import/Export](#importexport).
  1. [Export service](#export-service).
    * [Checking the Export State](#checking-the-export-state).
    * [Fetching the Export Output](#fetching-the-export-output).
  2. [Import service](#import-service).
    * [Checking the Import State](#checking-the-import-state).
11. [REST Server Information](#rest-server-information).
12. [Exception handling](#exception-handling).
13. [Asynchronous API](#asynchronous-api).
14. [Getting serialized content from response](#getting-serialized-content-from-response).
15. [Switching between JSON and XML](#switching-between-json-and-xml).
16. [Possible issues](#possible-issues).
17. [Maven dependency to add jasperserver-rest-client to your app](#maven-dependency-to-add-jasperserver-rest-client-to-your-app).
18. [License](#license).

Introduction
-------------
With this library you can easily write Java applications which can interact with one or more JasperReports servers simultaneously in a very simple way. Library provides very friendly API for user, it minimizes possibility of building wrong requests. To use library in your maven-based application you need just to specify dependency and repository which are given below or download jar file manually from
```
http://jaspersoft.artifactoryonline.com/jaspersoft/repo/com/jaspersoft/jrs-rest-java-client/{version}/jrs-rest-java-client-{version}.jar
```

Configuration
-------------
To start working with the library you should firstly configure one ore more instances of `JasperserverRestClient`.
To do this you should create instance of `RestClientConfiguration`. It can be done in two ways:
####Loading configuration from file:
```java
RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
```
File should contain only URL which is entry point to your server's REST services and it is needed to URL  corresponds to this pattern `{protocol}://{host}:{port}/{contextPath}`.
####Creation of manual configuration
```java
RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:8080/jasperserver");
```
####HTTPS configuration
<strong>To use HTTPS you need:</strong>
1. Configure your server to support HTTPS
2. Download [InstallCert](http://miteff.com/files/InstallCert-bin.zip) util and follow  [InstallCert-Guide](http://www.mkyong.com/webservices/jax-ws/suncertpathbuilderexception-unable-to-find-valid-certification-path-to-requested-target/) instructions.
3. Set HTTPS as your protocol in server URL, e.g. `https://localhost:8443/jasperserver`
4. Configure trusted certificates if needed

```java
RestClientConfiguration configuration = new RestClientConfiguration("https://localhost:8443/jasperserver");
X509TrustManager x509TrustManager = ...
TrustManager[] trustManagers = new TrustManager[1];
trustManagers[0] = x509TrustManager;
configuration.setTrustManagers(trustManagers);
```
####Client instantiation:
Here everything is easy, you need just to pass `configuration` to `JasperserverRestClient` constructor.
```java
JasperserverRestClient client = new JasperserverRestClient(configuration);
```

Authentication
---------------
This library automatically encrypts your password before send it if encryption is on, so to authenticate you need just specify login and password (not encrypted) in `authenticate()` method.
```java
Session session = client.authenticate("jasperadmin", "jasperadmin");
//authentication with multitenancy enabled
Session session = client.authenticate("jasperadmin|organization_1", "jasperadmin");
```
####Invalidating session
Not to store session on server you can invalidate it with `logout()` method.
```java
session.logout();
```


Report services
===============
After you've configured the client you can easily use any of available services. For reporting service there is one feature that should be noted - when you are running a report all subsequent operations must be executed in the same session. Here's the code:
```java
Session session = client.authenticate("jasperadmin", "password");
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
###Report Parameters services:
The reports service includes methods for reading and setting report parameters.
####Listing Report Parameters Structure
The following code returns a description of the structure of the report parameters for a given report.
```java
ReportInputControlsListWrapper inputControls =
        client.authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .reportParameters()
                .get()
                .getEntity();
```
The response contains the structure of the report parameters for the report. It contains the information needed by your application to display the report parameters to your users and allow them to make a selection. In particular, this includes any cascading structure as a set of dependencies between report parameters. Each report parameter also has a type that indicates how the user should be allowed to make a choice: bool, singleSelect, singleSelectRadio, multiSelectCheckbox, multiSelect, singleValue, singleValueText, singleValueNumber, singleValueDate, singleValueDatetime, singleValueTime.
The structure includes a set of validation rules for each report parameter. These rules indicate what type of validation your client should perform on report parameter values it receives from your users, and if the validation fails, the message to display. Depending on the type of the report parameter, the following validations are possible:
* mandatoryValidationRule – This input is required and your client should ensure the user enters a value.
* dateTimeFormatValidation – This input must have a data time format and your client should ensure the user enters a valid date and time.

####Listing Report Parameter Values
The following code returns a description of the possible values of all report parameters for the report. Among these choices, it shows which ones are selected.
```java
InputControlStateListWrapper inputControlsValues =
        client.authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .reportParameters()
                .values()
                .get()
                .getEntity();
```
The response contains the structure of the report parameters for the report.
If a selection-type report parameter has a null value, it is given as `~NULL~`. If no selection is made, its value is given as `~NOTHING~`.
####Setting Report Parameter Values
The following code updates the state of current report parameter values, so they are set for the next run of the report.
```java
InputControlStateListWrapper inputControlsValues =
        client.authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .reportParameters()
                .values()
                .parameter("Cascading_name_single_select", "A & U Stalker Telecommunications, Inc")
                .update()
                .getEntity();
```
####QueryExecutor Service
In addition to running reports, JasperReports Server exposes queries that you can run through the QueryExecutor service.
For now the only resource that supports queries is a Domain.

The following code executes query and retrieves a result of execution as QueryResult entity.
```java
QueryResult queryResult = session.queryExecutorService()
        .query(queryFromXmlFile, "/organizations/organization_1/Domains/Simple_Domain")
        .execute()
        .getEntity();
```
Administration services:
========================
Only administrative users may access the REST services for administration.

###Organizations service
It provides methods that allow you to list, view, create, modify, and delete organizations (also known as tenants). Because the organization ID is used in the URL, this service can operate only on organizations whose ID is less than 100 characters long and does not contain spaces or special symbols. As with resource IDs, the organization ID is permanent and cannot be modified for the life of the organization.
####Searching for Organizations
The service searches for organizations by ID, alias, or display name. If no search is specified, it returns a list of all organizations. Searches and listings start from but do not include the
logged-in user’s organization or the specified base.
```java
OperationResult<OrganizationsListWrapper> result = session
        .organizationsService()
        .organizations()
        .parameter(OrganizationParameter.INCLUDE_PARENTS, "true")
        .get();
```
####Viewing an Organization
The `organization()` method with an organization ID retrieves a single descriptor containing the list of properties for the organization. When you specify an organization, use its unique ID, not its path.
```java
OperationResult<Organization> result = session
        .organizationsService()
        .organization("myOrg1")
        .get();
```
####Creating an Organization
To create an organization, put all information in an organization descriptor, and include it in a request to the `rest_v2/organizations` service, with no ID specified. The organization is created in the organization specified by the `parentId` value of the descriptor.
```java
Organization organization = new Organization();
organization.setAlias("myOrg1");

OperationResult<Organization> result = session
        .organizationsService()
        .organizations()
        .create(organization);
```
The descriptor sent in the request should contain all the properties you want to set on the new organization. Specify the `parentId` value to set the parent of the organization, not the `tenantUri` or `tenantFolderUri` properties.
However, all properties have defaults or can be determined based on the alias value. The minimal descriptor necessary to create an organization is simply the alias property. In this case, the organization is created as child of the logged-in user’s home organization.
####Modifying Organization Properties
To modify the properties of an organization, use the `update` method and specify the organization ID in the URL. The request must include an organization descriptor with the values you want to change. You cannot change the ID of an organization, only its name (used for display) and its alias (used for logging in).
```java
Organization organization = new Organization();
organization.setAlias("lalalaOrg");

OperationResult<Organization> result = session
        .organizationsService()
        .organization("myOrg1")
        .update(organization);
```
####Deleting an Organization
To delete an organization, use the `delete()` method and specify the organization ID in the `organization()` method. When deleting an organization, all of its resources in the repository, all of its sub-organizations, all of its users, and all of its roles are permanently deleted.
```java
OperationResult result = session
        .organizationsService()
        .organization("myOrg1")
        .delete();
```


######Each of administration services can work both with enabled and disabled multitenancy mode.
```java
//with multitenancy
client
        .authenticate("jasperadmin|organization_1", "jasperadmin")
        .usersService()
        .organization("myOrg1")
        ....
//without multitenancy
client
        .authenticate("jasperadmin", "jasperadmin")
        .usersService()
        ...
```

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

###Attributes service
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
###The Roles Service
It provides similar methods that allow you to list, view, create, modify, and delete roles. The new service provides improved search functionality, including user-based role searches. Because the role ID is used in the URL, this service can operate only on roles whose ID is less than 100 characters long and does not contain spaces or special symbols. Unlike resource IDs, the role ID is the role name and can be modified.
####Searching for Roles
The `allRoles()` method searches for and lists role definitions. It has options to search for roles by
name or by user (`param()` method) that belong to the role. If no search is specified, it returns all roles.
```java
OperationResult<RolesListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .allRoles()
                .param(RolesParameter.USER, "jasperadmin")
                .get();

RolesListWrapper rolesListWrapper = operationResult.getEntity();
```
####Viewing a Role
The `rolename()` method with a role ID retrieves a single role descriptor containing the role properties.
```java
OperationResult<ClientRole> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename("ROLE_ADMINISTRATOR")
                .get();

ClientRole role = operationResult.getEntity();
```
####Creating a Role
To create a role, send the request via `createOrUpdate()` method to the roles service with the intended role ID (name) specified in the URL. Roles do not have any properties to specify other than the role ID, but the request must include a descriptor that
can be empty.
```java
ClientRole role = new ClientRole()
        .setName("ROLE_HELLO");

OperationResult<ClientRole> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename(role.getName())
                .createOrUpdate(role);

Response response = operationResult.getResponse();
```
####Modifying a Role
To change the name of a role, send a request via `createOrUpdate()` to the roles service and specify the new name in the role descriptor. The only property of a role that you can modify is the role’s name. After the update, all members of the role are members of the new role name, and all permissions associated with the old role name are updated to the new role name.
```java
ClientRole roleHello = new ClientRole()
        .setName("ROLE_HELLO_HELLO");

OperationResult<ClientRole> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename("ROLE_HELLO")
                .createOrUpdate(roleHello);

Response response = operationResult.getResponse();
```
####Setting Role Membership
To assign role membership to a user, set the roles property on the user account with the PUT method of the rest_
v2/users service. For details, see section [creating a user](https://github.com/boryskolesnykov/jasperserver-rest-client/edit/master/README.md#creating-a-user).
####Deleting a Role
To delete a role, send the DELETE method and specify the role ID (name) in the URL.
When this method is successful, the role is permanently deleted.
```java
OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename("ROLE_HELLO")
                .delete();
Response response = operationResult.getResponse();
```
Repository Services
=====================
###Resources Service
This new service provides greater performance and more consistent handling of resource descriptors for all repository resource types. The service has two formats, one takes search parameters to find resources, the other takes a repository URI to access resource descriptors and file contents.
####Searching the Repository
The resources service, when `resources()` method used without specifying any repository URI, is used to search the repository. The various parameters let you refine the search and specify how you receive search results.
```java
OperationResult<ClientResourceListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resources()
        .search();
ClientResourceListWrapper resourceListWrapper = result.getEntity();
//OR
OperationResult<ClientResourceListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resources()
        .parameter(ResourceSearchParameter.FOLDER_URI, "/reports/samples")
        .parameter(ResourceSearchParameter.LIMIT, "5")
        .search();
ClientResourceListWrapper resourceListWrapper = result.getEntity();
```
The response of a search is a set of shortened descriptors showing only the common attributes of each resource. One additional attribute specifies the type of the resource. This allows you to quickly receive a list of resources for display or further processing.
####Viewing Resource Details
Use the `resource()` method and a resource URI with `details()` method to request the resource's complete descriptor.
```java
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/properties/GlobalPropertiesList")
        .details();
```
####Downloading File Resources
There are two operations on file resources:
* Viewing the file resource details to determine the file format
* Downloading the binary file contents

To view the file resource details, specify the URL of the file in `resource()` method and use the code form [Viewing Resource Details](https://github.com/boryskolesnykov/jasperserver-rest-client/edit/master/README.md#viewing-resource-details) section.
To download file binary content, specify the URL of the file in `resource()` method and use the code below
```java
OperationResult<InputStream> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/themes/default/buttons.css")
        .downloadBinary();

InputStream inputStream = result.getEntity();
```
To get file MIME type yo can get `Content-Type` header from the `Response` instance.
####Creating a Resource
The `createNew()` and `createOrUpdate()` methods offer alternative ways to create resources. Both take a resource descriptor but each handles the URL differently. With the `createNew()` method, specify a folder in the URL, and the new resource ID is created automatically from the label attribute in its descriptor. With the `createOrUpdate()` method, specify a unique new resource ID as part of the URL in `resource()` method.
```java
ClientFolder folder = new ClientFolder();
folder
        .setUri("/reports/testFolder")
        .setLabel("Test Folder")
        .setDescription("Test folder description")
        .setPermissionMask(0)
        .setCreationDate("2014-01-24 16:27:47")
        .setUpdateDate("2014-01-24 16:27:47")
        .setVersion(0);

OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource(folder.getUri())
        .createOrUpdate(folder);
//OR
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource(folder.getUri())
        .createNew(folder);
```
####Modifying a Resource
Use the `createOrUpdate()` method above to overwrite an entire resource. Specify the path of the target resource in the `resource()` method and specify resource of the same type. Use `parameter(ResourceServiceParameter.OVERWRITE, "true")` to replace a resource of a different type. The resource descriptor must completely describe the updated resource, not use individual fields. The descriptor must also use only references for nested resources, not other resources expanded inline. You can update the local resources using the hidden folder _file.
The `patchResource()` method updates individual descriptor fields on the target resource. It also accept expressions that modify the descriptor in the Spring Expression Language. This expression language lets you easily modify the structure and values of descriptors.
```java
PatchDescriptor patchDescriptor = new PatchDescriptor();
patchDescriptor.setVersion(0);
patchDescriptor.field("label", "Patch Label");

OperationResult<ClientFolder> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/reports/testFolder")
        .patchResource(ClientFolder.class, patchDescriptor);
```
Note that you must explicitly set the type of resource to update because of server issue.
####Copying a Resource
To copy a resource, specify in `copyFrom()` method its URI and in `resource()` method URI of destination location.
```java
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/reports")
        .copyFrom("/datasources/testFolder");
```
####Moving a Resource
To move a resource, specify in `moveFrom()` method its URI and in `resource()` method URI of destination location.
```java
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/datasources")
        .moveFrom("/reports/testFolder");
```
####Uploading File Resources
To upload file you must specify the MIME type that corresponds with the desired file type, you can take it from `ClientFile.FileType` enumeration.
```java
OperationResult<ClientFile> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/reports/testFolder")
        .uploadFile(imageFile, ClientFile.FileType.img, "fileName", "fileDescription");
```
####Deleting Resources
You can delete resources in two ways, one for single resources and one for multiple resources. To delete multiple resources at once, specify multiple URIs with the `ResourceSearchParameter.RESOURCE_URI` parameter.
```java
//multiple
OperationResult result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resources()
        .parameter(ResourceSearchParameter.RESOURCE_URI, "/some/resource/uri/1")
        .parameter(ResourceSearchParameter.RESOURCE_URI, "/some/resource/uri/2")
        .delete();
//OR
//single
OperationResult result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/reports/testFolder")
        .delete();
```
###The Permissions Service
In the permissions service, the syntax is expanded so that you can specify the resource, the recipient (user name or role name) and the permission value within the URL. This makes it simpler to set permissions because you don’t need to send a resource descriptor to describe the permissions. In order to set, modify, or delete permissions, you must use credentials or login with a user that has “administer” permissions on the target resource.
Because a permission can apply to either a user or a role, the permissions service uses the concept of a “recipient”. A recipient specifies whether the permission applies to a user or a role, and gives the ID of the user or role.
There are two qualities of a permission:
* The assigned permission is one that is set explicitly for a given resource and a given user or role. Not all permissions are assigned, in which case the permission is inherited from the parent folder.
* The effective permission is the permission that is being enforced, whether it is assigned or inherited.

####Viewing Multiple Permissions

```java
OperationResult<RepositoryPermissionListWrapper> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .resource("/datasources")
                .get();
```
####Viewing a Single Permission
Specify the recipient in the URL to see a specific assigned permission.
```java
OperationResult<RepositoryPermission> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .resource("/datasources")
                .permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER")
                .get();

RepositoryPermission permission = operationResult.getEntity();
```
####Setting Multiple Permissions
The `createNew()` method assigns any number of permissions to any number of resources specified in the body of the request. All permissions must be newly assigned, and the request will fail if a recipient already has an assigned (not inherited) permission. Use the `createOrUpdate()` method to update assigned permissions. The `createOrUpdate()` method modifies exiting permissions (already assigned).
```java
List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
permissionList.add(new RepositoryPermission("/themes", "user:/joeuser", 30));

RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .createNew(permissionListWrapper);

Response response = operationResult.getResponse();
```
####Setting a Single Permission
The `createNew()` method accepts a single permission descriptor.
```java
RepositoryPermission permission = new RepositoryPermission();
permission
        .setUri("/")
        .setRecipient("user:/joeuser")
        .setMask(PermissionMask.READ_WRITE_DELETE);

OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .createNew(permission);

Response response = operationResult.getResponse();
```
####Deleting Permissions in Bulk
The `delete()` method removes all assigned permissions from the designated resource. After returning successfully, all effective permissions for the resource are inherited.
```java
OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .resource("/themes")
                .delete();

Response response = operationResult.getResponse();
```
####Deleting a Single Permission
Specify a recipient in the `permissionRecipient()` method and call the `delete()` method to remove only that permission.
```java
OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .resource("/")
                .permissionRecipient(PermissionRecipient.USER, "joeuser")
                .delete();

Response response = operationResult.getResponse();
```
Jobs service
==================
The jobs service provides the interface to schedule reports and manage scheduled reports (also called jobs). In addition, this service provides an API to scheduler features that were introduced in JasperReports Server 4.7, such as bulk updates, pausing jobs, FTP output and exclusion calendars.
####Listing Report Jobs
Use the following method to list all jobs managed by the scheduler.
```java
OperationResult<JobSummaryListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .get();

JobSummaryListWrapper jobSummaryListWrapper = result.getEntity();
```
The jobs are described in the `JobSummary` element.
####Viewing a Job Definition
The following piece of code with a specific job ID specified in `job()` method retrieves the detailed information about that scheduled job.
```java
OperationResult<Job> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .job(8600)
        .get();

Job job = result.getEntity();
```
This code returns a job element that gives the output, scheduling, and parameter details, if any, for the job.
####Extended Job Search
The `search()` method is used for more advanced job searches. Some field of the jobsummary descriptor can be used directly as parameters, and fields of the job descriptor can also be used as search criteria. You can also control the pagination and sorting order of the reply.
```java
Job criteria = new Job);
criteria.setLabel("updatedLabel");
criteria.setAlert(new JobAlert());

OperationResult<JobSummaryListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.SEARCH_LABEL, "hello")
        .search(criteria);

JobSummaryListWrapper jobSummaryListWrapper = result.getEntity();
```
The `criteria` parameter lets you specify a search on fields in the job descriptor, such as output formats. Some fields may be specified in both the search parameter and in a dedicated parameter, for example label. In that case, the search specified in the parameter takes precedence.   For example, you can search for all jobs that specify output format of PDF. The criteria to specify this
field is:
```java
List<String> outputFormats = new ArrayList<String>();
outputFormats.add("PDF");
OutputFormatsListWrapper wrapper = new OutputFormatsListWrapper(outputFormats);
JobExtension criteria = new JobExtension();
criteria.setOutputFormats(wrapper);
```
Currently the code is a little bit littered, in futere versions it will be eliminated.
####Scheduling a Report
To schedule a report, create its job descriptor similar to the one returned by the `job(id).get();` method, and use the `scheduleReport()` method of the jobs service. Specify the report being scheduled inside the job descriptor. You do not need to specify any job IDs in the descriptor, because the server will assign them.
```java
job.setLabel("NewScheduledReport");
job.setDescription("blablabla");
JobSource source = job.getSource();
source.setReportUnitURI("/reports/samples/Employees");

OperationResult<Job> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .scheduleReport(job);

job = result.getEntity();
```
The body contains the job descriptor of the newly created job. It is similar to the one that was sent but now contains the jobID for the new job.
####Viewing Job Status
The following method returns the current runtime state of a job:
```java
OperationResult<JobState> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .job(8600)
        .state();

JobState jobState = result.getEntity();
```
Response contains the `JobState` status descriptor.
####Editing a Job Definition
To modify an existing job definition, use the `job(id).get()` method to read its job descriptor, modify the descriptor as required, and use the `update()` method of the jobs service. The `update()` method replaces the definition of the job with the given job ID.
```java
String label = "updatedLabel";
Long jobId = job.getId();
job.setLabel(label);

OperationResult<Job> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .job(jobId)
        .update(job);

Job job = result.getEntity();
```
####Updating Jobs in Bulk
To update several jobs at once you should specify jobs IDs as parameters, and send a descriptor with filled fields to update.
```java
Job jobDescriptor = new Job();
jobDescriptor.setDescription("Bulk update description");

OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .parameter(JobsParameter.JOB_ID, "8601")
        .update(jobDescriptor);
```
The code above will update the `description` field of jobs with IDs `8600` and `8601`.
####Pausing Jobs
The following method pauses currently scheduled job execution. Pausing keeps the job schedule and all other details but prevents the job from running. It does not delete the job.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .pause();
```
####Resuming Jobs
Use the following method to resume any or all paused jobs in the scheduler. Resuming a job means that any defined trigger in the schedule that occurs after the time it is resumed will cause the report to run again. Missed schedule triggers that occur before the job is resumed are never run.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .resume();
```
####Restarting Failed Jobs
Use the following method to rerun failed jobs in the scheduler. For each job to be restarted, the scheduler creates an immediate single-run copy of job, to replace the one that failed. Therefore, all jobs listed in the request body will run once immediately after issuing this command. The single-run copies have a misfire policy set so that they do not trigger any further failures (`MISFIRE_ INSTRUCTION_IGNORE_MISFIRE_POLICY`). If the single-run copies fail themselves, no further attempts are made automatically.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .restart();
```
###Calendars service
The scheduler allows a job to be defined with a list of excluded days or times when you do not want the job to run. For example, if you have a report scheduled to run every business day, you want to exclude holidays that change every year. The list for excluded days and times is defined as a calendar, and there are various ways to define the calendar.  The scheduler stores any number of exclusion calendars that you can reference by name. When scheduling a report, reference the name of the calendar to exclude, and the scheduler automatically calculates the correct days to trigger the report. The scheduler also allows you to update an exclusion calendar and update all of the report jobs that used it. Therefore, you can update the calendar of excluded holidays every year and not need to modify any report jobs.
####Listing All Registered Calendar Names
The following method returns the list of all calendar names that were added to the scheduler.
```java
OperationResult<CalendarNameListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .calendars();  //OR .calendars(CalendarType.HOLIDAY); //to specify the desired calendar type

CalendarNameListWrapper calendarNameListWrapper = result.getEntity();
```
####Viewing an Exclusion Calendar
The following method takes the name of an exclusion calendar and returns the definition of the calendar:
```java
OperationResult<Calendar> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .calendar("testCalendar")
        .get();

Calendar jobCalendar = result.getEntity();
```
As a result we have common caledar descriptor `ReportJobCalendar`.
####Adding or Updating an Exclusion Calendar
This method creates a named exclusion calendar that you can use when scheduling reports. If the calendar already exists, you have the option of replacing it and updating all the jobs that used it.
```java
WeeklyCalendar calendar = new WeeklyCalendar();
calendar.setDescription("lalala");
calendar.setTimeZone("GMT+03:00");
calendar.setExcludeDaysFlags(new boolean[]{true, false, false, false, false, true, true});

OperationResult result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .calendar("testCalendar")
        .createOrUpdate(calendar);
```
Unlike common `ReportJobCalendar` which we receive as result of GET operation here we need create the calendar instance of desired type and path it to the `createOrUpdate()` method.
####Deleting an Exclusion Calendar
Use the following method to delete a calendar by name.
```java
OperationResult result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .calendar("testCalendar")
        .delete();
```
Import/Export
=============
###Export service
The export service works asynchronously: first you request the export with the desired options, then you monitor the state of the export, and finally you request the output file. You must be authenticated as the system admin (superuser) for the export services.
```java
OperationResult<StateDto> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .exportService()
                .newTask()
                .user("jasperadmin")
                .role("ROLE_USER")
                .parameter(ExportParameter.EVERYTHING)
                .create();

StateDto stateDto = operationResult.getEntity();
```
####Checking the Export State
After receiving the export ID, you can check the state of the export operation.
```java
OperationResult<StateDto> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .exportService()
                .task(stateDto.getId())
                .state();

StateDto state = operationResult.getEntity();
```
The body of the response contains the current state of the export operation.
####Fetching the Export Output
When the export state is ready, you can download the zip file containing the export catalog.
```java
OperationResult<InputStream> operationResult1 =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .exportService()
                .task(stateDto.getId())
                .fetch();

InputStream inputStream = operationResult1.getEntity();
```
###Import service
Use the following service to upload a catalog as a zip file and import it with the given options. Specify options as arguments from `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter`. Arguments that are omitted are assumed to be false. You must be authenticated as the system admin (superuser) for the import service. Jaspersoft does not recommend uploading files greater than 2 gigabytes.
```java
URL url = ImportService.class.getClassLoader().getResource("testExportArchive.zip");
OperationResult<StateDto> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .importService()
                .newTask()
                .parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, true)
                .create(new File(url.toURI()));

StateDto stateDto = operationResult.getEntity();
```
####Checking the Import State
After receiving the import ID, you can check the state of the import operation.
```java
OperationResult<StateDto> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .importService()
                .task(stateDto.getId())
                .state();

StateDto state = operationResult.getEntity();
```
####DomainMetadata Service

The DomainMetadata Service gives access to the sets and items exposed by a Domain for use in Ad
Hoc reports. Items are database fields exposed by the Domain, after all joins, filters, and calculated fields have
been applied to the database tables selected in the Domain. Sets are groups of items, arranged by the Domain
creator for use by report creators.

A limitation of the DomainMetadata Service only allows it to operate on Domains with a single data
island. A data island is a group of fields that are all related by joins between the database tables in the
Domain. Fields that belong to tables that are not joined in the Domain belong to separate data islands.

The following code retrieves metadata of Domain.
```java
DomainMetaData domainMetaData = session.domainService()
        .domainMetadata("/Foodmart_Sales")
        .retrieve()
        .getEntity();
```
REST Server Information
========================
Use the following service to verify the server information, the same as the `About JasperReports Server` link in the user interface.
```java
OperationResult<ServerInfo> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .serverInfoService()
        .details();

ServerInfo serverInfo = result.getEntity();
```
The server returns a `ServerInfo` instance containing the requested information.
You can access each value separately with the following code:
```java
OperationResult<String> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .serverInfoService()
        .edition();
        //.version();
        //.licenseType();
        //.features();
        //.expiration();
        //.editionName();
        //.dateTimeFormatPattern();
        //.dateFormatPattern();
        //.build();

String edition = result.getEntity();
```

###Exception handling
You can customize exception handling for each endpoint. To do this you need to pass `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler` implementation to `JerseyRequestBuilder.buildRequest()` factory method.

JRS REST client exception handling system is based on `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler` interface. Its `void handleError(Response response)` method is responsible for all error handling logic. You can use existed handlers, define your own handlers or extend existed handlers.

1. Existed handlers:
  * `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultExceptionHandler` - this implementation is suitable for most of the JRS errors, but sometimes you can meet some not standart errors and here such implementations as `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler`, `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.RunReportErrorHandler`, etc. take responsibility.
2. You can create your own handler by implementing `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler`.
3. You can extend `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultExceptionHandler` or any other handler and override its methods `void handleBodyError(Response response)` and/or `void handleStatusCodeError(Response response, String overridingMessage)`.

###Asynchronous API
Each operation which requests server has its asynchronous brother which has same name with `async` prefix, e. g. `get() -> asyncGet()`. Each of these operations take a `com.jaspersoft.jasperserver.jaxrs.client.core.Callback` implementation with `execute()` method implemented. `execute()` takes an `OperationResult` instance as a parameter. The `execute` method is called when the response from server came.
Each of these `async` operations returns `com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution` instance which gives you ability to cancel execution.
Example:
```java
RequestExecution requestExecution = session
        .organizationsService()
        .organizations()
        .parameter(OrganizationParameter.CREATE_DEFAULT_USERS, "false")
        .asyncCreate(new Organization().setAlias("asyncTestOrg"), new Callback<OperationResult<Organization>, Void>() {
            @Override
            public Void execute(OperationResult<Organization> data) {
                System.out.println(data.getEntity());
            }
        });

        requestExecution.cancel();
```

###Getting serialized content from response
If you need to get a plain response body, either JSON, XML, HTML or plain text, you gen get it it with code below:
```java
OperationResult<UsersListWrapper> result = ...
result.getSerializedContent();
```

###Switching between JSON and XML
You can configure a client to make request either with JSON or XML content.
```java
RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver");
configuration.setContentMimeType(MimeType.XML);
configuration.setAcceptMimeType(MimeType.XML);
```
or you can externilize configuration
```
url=http://localhost:4444/jasperserver/
contentMimeType=JSON
acceptMimeType=JSON
#contentMimeType=XML
#acceptMimeType=XML
```
```java
RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("jrs-client-config.properties");
```

###Possible issues
1. <strong>Deploying jrs-rest-client within web app to any Appplication Server, e.g. JBoss, Glassfish, WebSphere etc.</strong>
jrs-rest-client uses the implementation of JAX-RS API of version 2.0 and if your application server does not support this version you will get an error. To solve this problem you need to add to your application a deployment configuration specific for your AS where you need to exclude modules with old JAX-RS API version. Example of such descriptor for JBoss AS you can find below:

```xml
<jboss-deployment-structure>
    <deployment>
        <exclusions>

            <!-- Exclude JAVA EE of JBOSS (javax.ws..) => Add dependency javax.annotation -->
            <module name="javaee.api" />
            <!-- Exclude RestEasy conflict (javax.ws.rs.ext.RunDelegate) -->
            <module name="javax.ws.rs.api"/>
            <module name="org.codehaus.jackson.jackson-core-asl" />
            <module name="org.jboss.resteasy.resteasy-atom-provider" />
            <module name="org.jboss.resteasy.resteasy-cdi" />
            <module name="org.jboss.resteasy.resteasy-crypto" />
            <module name="org.jboss.resteasy.resteasy-jackson-provider" />
            <module name="org.jboss.resteasy.resteasy-jaxb-provider" />
            <module name="org.jboss.resteasy.resteasy-jaxrs" />
            <module name="org.jboss.resteasy.resteasy-jettison-provider" />
            <module name="org.jboss.resteasy.resteasy-jsapi" />
            <module name="org.jboss.resteasy.resteasy-json-p-provider" />
            <module name="org.jboss.resteasy.resteasy-multipart-provider" />
            <module name="org.jboss.resteasy.resteasy-validator-provider-11" />
            <module name="org.jboss.resteasy.resteasy-yaml-provider" />
        </exclusions>
    </deployment>
</jboss-deployment-structure>
```

###Maven dependency to add jasperserver-rest-client to your app:
```xml
    <dependencies>
        <dependency>
            <groupId>com.jaspersoft</groupId>
            <artifactId>jrs-rest-java-client</artifactId>
            <version>5.5.0.2-ALPHA-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>

        <repository>
            <id>jaspersoft-clients-snapshots</id>
            <name>Jaspersoft clients snapshots</name>
            <url>http://jaspersoft.artifactoryonline.com/jaspersoft/jaspersoft-clients-snapshots</url>
        </repository>

    </repositories>
```

License
--------
Copyright &copy; 2005 - 2014 Jaspersoft Corporation. All rights reserved.
http://www.jaspersoft.com.

Unless you have purchased a commercial license agreement from Jaspersoft,
the following license terms apply:

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
