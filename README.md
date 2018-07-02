Rest Client for JasperReports Server [![Build Status](https://travis-ci.org/Jaspersoft/jrs-rest-java-client.svg?branch=master)](https://travis-ci.org/Jaspersoft/jrs-rest-java-client) [![Coverage Status](https://coveralls.io/repos/Jaspersoft/jrs-rest-java-client/badge.png?branch=master)](https://coveralls.io/r/Jaspersoft/jrs-rest-java-client?branch=master)
With this library you can easily write Java applications which can interact with one or more JasperReports servers simultaneously in a very simple way. Library provides very friendly API for user, it minimizes possibility of building wrong requests.

Table of Contents
------------------
* [Introduction](#introduction).
* [Configuration](#configuration).
  * [Loading configuration from file](#loading-configuration-from-file).
  * [Creation of manual configuration](#creation-of-manual-configuration).
  * [HTTPS configuration](#https-configuration).
  * [X-HTTP-Method override](#x-http-method-override).
  * [Switching of authentication type](#switching-authentication-type).
  * [Exception handling](#exception-handling).
  * [Logging](#logging).
  * [Switching between JSON and XML](#switching-between-json-and-xml).
  * [Client instantiation](#client-instantiation).
* [Authentication](#authentication).
  * [Anonymous session](#anonymous-session).
  * [Invalidating session](#invalidating-session).
* [Report services](#report-services).
  * [Running a report](#running-a-report).
  * [Requesting report execution status](#requesting-report-execution-status).
  * [Requesting report execution details](#requesting-report-execution-details).
  * [Requesting Report Output](#requesting-report-output).
  * [Download file attachments for report output](#download-file-attachments-for-report-output).
  * [Updating report execution parameters](#updating-report-execution-parameters).
  * [Running new export](#running-new-export).
  * [Polling Export Execution](#polling-export-execution).
  * [Finding Running Reports and Jobs](#finding-running-reports-and-jobs).
  * [Stopping Running Reports](#stopping-running-reports).
  * [Report options service](#report-options-service).
    * [Listing report options](#listing-report-options).
    * [Creating report options](#creating-report-options).
    * [Updating report options](#updating-report-options).
    * [Deleting report options](#deleting-report-options).
* [Input controls service](#input-controls-service).
  * [Listing input controls structure](#listing-input-controls-structure).
  * [Reordering input controls structure](#reordering-input-controls-structure).
  * [Listing input controls values](#listing-input-controls-values).
  * [Setting input controls values](#setting-input-controls-values).
* [Administration services](#administration-services).
  * [Organizations service](#organizations-service).
    * [Searching for Organizations](#searching-for-organizations).
    * [Viewing an Organization](#viewing-an-organization).
    * [Creating an Organization](#creating-an-organization).
    * [Modifying Organization Properties](#modifying-organization-properties).
    * [Deleting an Organization](#deleting-an-organization).
  * [Users service](#users-service).
    * [Searching for Users](#searching-for-users).
    * [Viewing a User](#viewing-a-user).
    * [Creating a User](#creating-a-user).
    * [Modifying User Properties](#modifying-user-properties).
    * [Deleting a User](#deleting-a-user).
  * [Attributes service](#attributes-service).
    * [Viewing User Attributes](#viewing-user-attributes).
    * [Setting User Attributes](#setting-user-attributes).
    * [Deleting User Attributes](#deleting-user-attributes).
    * [Viewing Organization Attributes](#viewing-organization-attributes).
    * [Setting Organization Attributes](#setting-organization-attributes).
    * [Deleting Organization Attributes](#deleting-organization-attributes).
    * [Viewing Server Attributes](#viewing-server-attributes).
    * [Setting Server Attributes](#setting-server-attributes).
    * [Deleting Server Attributes](#deleting-server-attributes).
    * [Getting attributes permissions](#getting-attributes-permissions).
    * [Searching attributes ](#searching-attributes).
  * [The Roles Service](#the-roles-service).
    * [Searching for Roles](#searching-for-roles).
    * [Viewing a Role](#viewing-a-role).
    * [Creating a Role](#creating-a-role).
    * [Modifying a Role](#modifying-a-role).
    * [Setting Role Membership](#setting-role-membership).
    * [Deleting a Role](#deleting-a-role).
  * [The Settings Service](#settings-service).
    * [Getting server specific settings](#getting-server-specific-settings).
* [Repository Services](#repository-services).
  * [Resources Service](#resources-service).
    * [Searching the Repository](#searching-the-repository).
    * [Viewing Resource Details](#viewing-resource-details).
    * [Downloading File Resources](#downloading-file-resources).
    * [Creating a Resource](#creating-a-resource).
    * [Modifying a Resource](#modifying-a-resource).
    * [Copying a Resource](#copying-a-resource).
    * [Moving a Resource](#moving-a-resource).
    * [Uploading SemanticLayerDataSource](#uploading-semanticlayerdatasource).   
    * [Uploading MondrianConnection](#uploading-mondrianconnection).
    * [Uploading SecureMondrianConnection](#uploading-securemondrianconnection).
    * [Uploading ReportUnit](#uploading-reportunit).
    * [Uploading File Resources](#uploading-file-resources).
    * [Deleting Resources](#deleting-resources).
  * [The Permissions Service](#the-permissions-service).
    * [Viewing Multiple Permissions](#viewing-multiple-permissions).
    * [Viewing a Single Permission](#viewing-a-single-permission).
    * [Setting Multiple Permissions](#setting-multiple-permissions).
    * [Setting a Single Permission](#setting-a-single-permission).
    * [Deleting Permissions in Bulk](#deleting-permissions-in-bulk).
    * [Deleting a Single Permission](#deleting-a-single-permission).
* [Jobs service](#jobs-service).
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
 * [Calendars service](#calendars-service).
  * [Listing All Registered Calendar Names](#listing-all-registered-calendar-names).
  * [Viewing an Exclusion Calendar](#viewing-an-exclusion-calendar).
  * [Adding or Updating an Exclusion Calendar](#adding-or-updating-an-exclusion-calendar).
  * [Deleting an Exclusion Calendar](#deleting-an-exclusion-calendar).
* [Import/Export](#importexport).
  * [Export service](#export-service).
    * [Strat export](#strat-export).
    * [Check the export state](#check-the-export-state).
    * [Fetching the export output](#fetching-the-export-output).
    * [Cancel the export task](#cancel-the-export-task).
  * [Import service](#import-service).
    * [Strat import](#strat-import).
    * [Import using mulpipart form](#import-using-mulpipart-form).
    * [Check the Import State](#check-the-import-state).
    * [Getting and restarting import task](#checking-the-export-state).
    * [Check import state](#check-import-state).
    * [Cancel import task](#cancel-import-task).
* [Metadata](#metadata)
  * [Domain metadata](#domain-metadata).
  * [Report metadata](#report-metadata).
* [Thumbnail Search Service](#thumbnail-search-service).
* [Diagnostic Service](#diagnostic-service).
* [Contexts service](#contexts-service)
  * [Domain Context Service](#domain-context-service).
* [Data Discovery Service](#data-discovery-service).
  * [Domain data discovery](#domain-data-discovery).
  * [DomEl data discovery](#domel-data-discovery).
  * [Derived table data discovery](#derived-table-data-discovery).
* [Query Execution Service](#query-execution-service).
* [Server Information Service](#server-information-service).
* [Bundles service](#bundles-service).
* [Asynchronous API](#asynchronous-api).
* [Getting serialized content from response](#getting-serialized-content-from-response).
* [Possible issues](#possible-issues).
* [Maven dependency to add jasperserver-rest-client to your app](#maven-dependency-to-add-jasperserver-rest-client-to-your-app).
* [License](#license).

Introduction
============
With this library you can easily write Java applications which can interact with one or more JasperReports servers simultaneously in a very simple way. Library provides very friendly API for user, it minimizes possibility of building wrong requests. To use library in your maven-based application you need just to specify dependency and repository which are given below or download jar file manually from
```
http://jaspersoft.artifactoryonline.com/jaspersoft/repo/com/jaspersoft/jrs-rest-java-client/{version}/jrs-rest-java-client-{version}.jar
```

Configuration
=============
To start working with the library you should firstly configure one ore more instances of `JasperserverRestClient`.
To do this you should create instance of `RestClientConfiguration`. It can be done in two ways:
- loading configuration from file;
- creation of manual configuration in java code.

Loading configuration from file:
--------------------------------
```java
RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("configuration.properties");
```
Here is example of `configuration.properties` file:
```java
// required content
url=http://localhost:8080/jasperserver-pro
// optional content
connectionTimeout=20                        
readTimeout=20
jasperserverVersion=v6_0_0
authenticationType=SPRING
logHttp=true
logHttpEntity=true
restrictedHttpMethods=false
handleErrors=true
contentMimeType=JSON
acceptMimeType=JSON
```
File must contain at least URL which is entry point to your server's REST services and it is needed to URL  corresponds to this pattern `{protocol}://{host}:{port}/{contextPath}`.
Please notice, configuration settings may be changed after loading manually in java code.

Creation of manual configuration
--------------------------------
To configure `JasperserverRestClient` manually, use the constructor of `RestClientConfiguration` and properties:
```java
RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:8080/jasperserver");
configuration.setAcceptMimeType(MimeType.JSON).setContentMimeType(MimeType.JSON).setJrsVersion(JRSVersion.v6_0_0).setLogHttp(true);
```
HTTPS configuration
-------------------
**To use HTTPS you need:**
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
X-HTTP-Method override
----------------------
To avoid situation, when your proxies or web services do not support arbitrary HTTP methods or newer HTTP methods, you can use “restricted mode”. In this mode `JaperserverRestClient` sends requests through POST method and set the `X-HTTP-Method-Override` header with value of intended HTTP method. To use this mode you should set flag `RestrictedHttpMethods`:
```java
configuration.setRestrictedHttpMethods(true);
```
Or in configuration file:
```java
restrictedHttpMethods=false
````
If you do not use the "restricted mode", POST or GET methods and server returns  the response with 411 error code, `JaperserverRestClient` resend this request through POST method with the X-HTTP-Method-Override header automatically.

Switching authentication type
-----------------------------
`JasperserverRestClient` supports two authentication types: SPRING and BASIC. 
`SPRING` type of authentication means that your credentials are sent as a form  to `/j_security_check directly/` uri. Using these types you obtain JSESSIONID cookie of authenticated session after sending credentials.
In the `BASIC` mode `JasperserverRestClient` uses basic authentication (sends encrypted credentials with each request).
Client uses `SPRING` authentication by default but you can specify authentication type in RestClientConfiguration instance:
```java
configuration.setAuthenticationType(AuthenticationType.SPRING);
```
Or set authentication type in configuration file:
 ```java
 authenticationType=SPRING
 or
 authenticationType=BASIC
 ```
Please notice, the basic authentication is not stateless and it is valid till method logout() is called or the application is restarted and you can not use this authentication type for Report Service, because all operations must be executed in the same session (for details, read section [Report services](https://github.com/Jaspersoft/jrs-rest-java-client/blob/master/README.md#report-services)).

Exception handling
------------------
You can choose strategy of errors that are specified by status code of server response:
1. handling of errors directly. This mode is allowed by default.
2. getting operation result in any case with null entity and handling error after calling `getEntity()` method:
```java
OperationResult<InputStream> result = session
                .thumbnailsService()
                .thumbnail()
                .report("/")
                .get(); // response status is 406, but exception won't be thrown
result.getEntity();     // the error will be handled and an exception will be thrown
```
To apply the second strategy set `handleErrors` property of `RestCleintConfiguration` to `false`:
```java
configuration.setHandleErrors(false);
```
or specify this property in configuration file:
```java
handleErrors=false
```
You can customize exception handling for each endpoint. To do this you need to pass `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler` implementation to `JerseyRequestBuilder.buildRequest()` factory method.

JRS REST client exception handling system is based on `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler` interface. Its `void handleError(Response response)` method is responsible for all error handling logic. You can use existed handlers, define your own handlers or extend existed handlers.

 1. Existed handlers:
   * `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultExceptionHandler` - this implementation is suitable for most of the JRS errors, but sometimes you can meet some not standart errors and here such implementations as `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler`, `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.RunReportErrorHandler`, etc. take responsibility.
 2. You can create your own handler by implementing `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler`.
 3. You can extend `com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultExceptionHandler` or any other handler and override its methods `void handleBodyError(Response response)` and/or `void handleStatusCodeError(Response response, String overridingMessage)`.

Logging
-------
It is possible to log outgoing requests and incoming responses using `logHttp` property of `RestCleintConfiguration`:
```java
config.setLogHttp(true);
```
Also, you are able to log entities using `logHttpEntity` option:
```java
config.setLogHttpEntity(true).
```
In configuration file:
```java
logHttp=true
logHttpEntity=true
```
Switching between JSON and XML
------------------------------
You can configure a client to make request either with JSON or XML content.
```java
RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver");
configuration.setContentMimeType(MimeType.XML);
configuration.setAcceptMimeType(MimeType.XML);
```
Or in `configuration.properties`:
```
contentMimeType=JSON
acceptMimeType=JSON
or 
contentMimeType=XML
acceptMimeType=XML
```
Client instantiation:
---------------------
After configuration you need just to pass `configuration` instance to `JasperserverRestClient` constructor.
```java
JasperserverRestClient client = new JasperserverRestClient(configuration);
```

Authentication
==============
This library automatically encrypts your password before send it if encryption is on, so to authenticate you need just specify login and password (not encrypted) in `authenticate()` method.
```java
Session session = client.authenticate("jasperadmin", "jasperadmin");
//authentication with multitenancy enabled
Session session = client.authenticate("jasperadmin|organization_1", "jasperadmin");
```
If you need to set user time zone different from default system timezone (for example, for running reports) use the code below:
```java
Session session = client.authenticate("jasperadmin", "jasperadmin", TimeZone.getTimeZone("America/Los_Angeles"));
// or
Session session = client.authenticate("jasperadmin", "jasperadmin", "America/Los_Angeles");
```
Also you can set user locale at authentication:
```java
Session session = client.authenticate("jasperadmin", "jasperadmin", new Locale("de"), TimeZone.getTimeZone("America/Los_Angeles"));
// or
Session session = client.authenticate("jasperadmin", "jasperadmin", "de", America/Los_Angeles");
```
Anonymous session
-----------------
For some Jasperserver services authentication is not required (for example, settings service, bundles service or server info service), so you can use anonymous session:
 ```java
AnonymousSession session = client.getAnonymousSession();
```
Invalidating session
--------------------
Not to store session on server you can invalidate it with `logout()` method.
```java
session.logout();
```

Report services
===============
This service allows to start report execution(export with certain options) and get report execution metadata (i.e. execution status, total pages count, errors occured during execution, attachments names etc.). 
Another major feature is possibility to run/export report asynchronously.
For reporting service there is one feature that should be noted - when you are running a report all subsequent operations must be executed **in the same session**. Here's the code:

Running a report:
-----------------
jaserserver REST API allows to run report execution in synchronous and asynchronous modes. The `async` mode is set in ReportExecutionRequest or as part of builer chain:
```java
        OperationResult<ReportExecution> result = session
                .reportingService()
                .report(reportUnitUri)
                .reportExecutions()
                .outputFormat(ReportOutputFormat.PDF)
                .pages(1)
                .reportParameter("Cascading_state_multi_select", "CA")
                .reportParameter("Cascading_state_multi_select", "OR", "WA")
                .reportParameter("Country_multi_select", "USA")
                .async(Boolean.FALSE)
                .run();

        ReportExecution entity = result.getEntity();
	
	// OR set parameters as ReportEecutionrequest
	
	 final ReportParameter reportParameter1 = new ReportParameter().setName(name).setValues(asList(value1, Value2));
        reportParameters.setReportParameters(asList(reportParameter1));

        final ReportExecutionRequest reportExecutionRequest = new ReportExecutionRequest()
                .setReportUnitUri(reportUnitUri)
                .setPages("1")
                .setParameters(reportParameters)
                .setOutputFormat("pdf")
		.setAsync(Boolean.TRUE);
        
        OperationResult<ReportExecution> result = session
                .reportingService()
                .reportExecution(reportExecutionRequest)
                .run();

        ReportExecution entity = result.getEntity();
```
Please notice, if you pass zero as number of page, you  will get all  pages of report.

In order to run a report, you need firstly build `ReportExecutionRequest` instance and specify all the parameters needed to launch a report. The response from the server is the `ReportExecution`, the edscriptor of execution, contains the request ID needed to track the execution until completion and others report parameters. 

Requesting report execution status:
-----------------------------------
After you've got `ReportExecutionDescriptor` you can request for the report execution status:
```java
        final OperationResult<ReportExecutionStatusObject> operationResult = session
                .reportingService()
                .reportExecution(executionId)
                .status();
        final ReportExecutionStatusObject entity = operationResult.getEntity();

```
To get report execution status with error descriptor from server just specify `.withErrorDescriptor(Boolean.TRUE)` setting:
```java
        final OperationResult<ReportExecutionStatusEntity> statusOperationResult = session
                .reportingService()
                .reportExecution(executionId)
                .export(exportId)
                .withErrordescriptor(Boolean.TRUE)
                .status();
```

Requesting report execution details:
------------------------------------
Once the report is ready, your client must determine the names of the files to download by requesting the
reportExecution descriptor again.
```java
        final OperationResult<ReportExecution> operationResult = session
                .reportingService()
                .reportExecution(executionId)
                .details();
		
	ReportExecution details = operationResult.getEntity();
```

Requesting Report Output
------------------------
After requesting a report execution and waiting synchronously or asynchronously for it to finish, you are ready to download the report output. Every export format of the report has an ID that is used to retrieve it. For example, the HTML export has the ID html. To download the main report output, specify this export ID in the `export` method. For example, to download the main HTML of the report execution response above, use the following code:
```java
        OperationResult<ReportExecution> runOperationResult = session
                .reportingService()
                .report(reportUri)
                .reportExecutions()
                .outputFormat(ReportOutputFormat.HTML)
                .pages(1)
                .async(Boolean.FALSE)
                .run();

        final ReportExecution entity = runOperationResult.getEntity();
	
        String executionId = entity.getRequestId();
	
        final ExportExecution export = entity.getExports().iterator().next();
	
        final String exportId = export.getId();
	
	OperationResult<InputStream> outputResourceOperationResult = session
                .reportingService()
                .reportExecution(executionId)
                .export(exportId)
		.getOutputResource();
```
As a response you'll get an `InputStream` instance. If the output resourse is one of test formats(txt, html, csv etc), resource output is sent as String, so to get output,  use  
```java
	OperationResult<String> outputResourceOperationResult = session
                .reportingService()
                .reportExecution(executionId)
                .export(exportId)
		.getOutputResourceAsText();
	String output = outputResourceOperationResult.getEntity();
```
There is a shorter way to get report output, but you sould take into account that it works onlly in asynchronous way, allows set limited set od settings and retuns report parametest in deprecated format:
```java
OperationResult<InputStream> result = session
                .reportingService()
                .report(reportUri)
                .prepareForRun(ReportOutputFormat.PDF, 1)
                .forTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))//if the time zone is defferent from session time zone
                .run();
InputStream report = result.getEntity();
```

Download file attachments for report output:
--------------------------------------------
To download file attachments for HTML output, use the following code. You must download all attachments to display the HMTL content properly.
```java
      OperationResult<ReportExecution> runOperationResult = session
                .reportingService()
                .report(reportUri)
                .reportExecutions()
                .outputFormat(ReportOutputFormat.HTML)
                .pages(1)
                .async(Boolean.FALSE)
                .run();

        final ReportExecution entity = runOperationResult.getEntity();
        String executionId = entity.getRequestId();
        final ExportExecution export = entity.getExports().iterator().next();
        final OutputResourceDescriptor attachment = export.getAttachments().entrySet().iterator().next().getValue();

        final String exportId = export.getId();

        final OperationResult<InputStream> outputResourceOperationResult = session
                .reportingService()
                .reportExecution(executionId)
                .export(exportId)
                .getOutputResourceAttachment(attacment);
```
Updating report execution parameters
------------------------------------
Use the next code to change report execution parameters (e.g. input controls values) without change of report execution ID and to keep exports set. Report is executed again on this request but with updated parameters.
```java
        session
                .reportingService()
                .reportExecution(executionId)
                .updateParameters(reportParameters);
```

Running new export
------------------
After running a report and downloading its content in a given format, you can request the same report in other formats (or with other options) without restaring export execution. As with exporting report formats through the user interface, the report does not run again because the export process is independent of the report.
```java
         ExportExecutionOptions executionOptions = new ExportExecutionOptions();
        executionOptions.setBaseUrl(jasperserverUri);
        executionOptions.setOutputFormat(ReportOutputFormat.PDF.name());
        executionOptions.setPages("1");
        executionOptions.setIgnorePagination(Boolean.FALSE);
        final OperationResult<ExportExecution> expoirtExecutionOpertaionResult = session
                .reportingService()
                .reportExecution(executionId)
                .export()
                .withOptions(executionOptions)
                .run();
```
Polling Export Execution
------------------------
As with the execution of the main report, you can also poll the execution of the export process.
For example, to get the status export use the following code:
```java
        final OperationResult<ReportExecutionStatusEntity> statusOperationResult = session
                .reportingService()
                .reportExecution(executionId)
                .export(exportId)
                .status();

ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
```

Finding Running Reports and Jobs
--------------------------------
You can search for reports that are running on the server, including report jobs triggered by the scheduler. To search for running reports, use the search arguments from `ReportSearchParameter` enumeration.
```java
        final OperationResult<ReportExecutionsSetWrapper> operationResult = session
                .reportingService()
                .reportExecutions()
                .queryParameter(ReportSearchParameter.REPORT_URI, reportUnitUri)
                .search();
        final ReportExecutionsSetWrapper entity = operationResult.getEntity();
```

Stopping Running Reports
---------------------------------
To stop a report that is running and cancel its output, use the code below:
```java
        final OperationResult<ReportExecutionStatusEntity> operationResult = session
                .reportingService()
                .reportExecution(executionId)
                .cancel();
```
To delete report execution: 
```java
        final OperationResult deleteOperationresult = session
                .reportingService()
                .reportExecution(executionId)
                .delete();
```

Report options service
----------------------
Report options are sets of input control values that are saved in the repository. A report option is always associated with a report.

### Listing report options
```java
            OperationResult<ReportOptionsSummaryList> result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions()
                    .get();

            final ReportOptionsSummaryList entity = result.getEntity();
```
The body of the response contains the description of the report options(URI, Id and label).

### Creating report options
The following code example creates a new report option for a given report. A report option is defined by a set of values for **all** of the report’s input controls.
```java
            final MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
            map.addAll("Country_multi_select", "Mexico");
            map.addAll("Cascading_state_multi_select", "Guerrero", "Sinaloa");
            map.addAll("Cascading_name_single_select", "Crow-Sims Construction Associates");

            OperationResult<ReportOptionsSummary> result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions(map)
                    .label(OPTIONS_LABEL)
                    .create();

            final ReportOptionsSummary entity = result.getEntity();
 ```
 or
 ```java
             final List<ReportParameter> reportParameters = new LinkedList<ReportParameter>();
            reportParameters.add(new ReportParameter().setName("Country_multi_select").setValues(asList("Mexico")));
            reportParameters.add(new ReportParameter().setName("Cascading_state_multi_select").setValues(asList("Guerrero", "Sinaloa")));
            reportParameters.add(new ReportParameter().setName("Cascading_name_single_select").setValues(asList("Crow-Sims Construction Associates")));

            
            OperationResult<ReportOptionsSummary> result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions(reportParameters)
                    .label(OPTIONS_LABEL)
                    .create();

            final ReportOptionsSummary entity = result.getEntity()
 ```
The server responds with a JSON object that describes the new report options
 
### Updating report options 
Use the following example to modify the values in a given report option
```java
            final MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
            map.addAll("Country_multi_select", "USA");

            OperationResult<ReportOptionsSummary> result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions(OPTIONS_LABEL)
                    .update(map);
```
or
```java
            final List<ReportParameter> reportParameters = new LinkedList<ReportParameter>();
            reportParameters.add(new ReportParameter().setName("Country_multi_select").setValues(asList("USA")));


            
            OperationResult<ReportOptionsSummary> result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions(OPTIONS_LABEL)
                    .update(reportParameters);

            final ReportOptionsSummary entity = result.getEntity();
```

### Deleting report options
To delete a given report option you can use  followingcode:
```java
            OperationResult result = session
                    .reportingService()
                    .report(reportUnitUri)
                    .reportOptions(OPTIONS_LABEL)
                    .delete();
```

Input controls service:
=======================
The reports service includes methods for reading and setting input controls of any input controls container, i.e. reportUnit, reportOptions, dashboard, adhocDataView. JRS Rest client allows to work with report input controls as part of ReportService or as independent InputControlService.

Listing Report Parameters Structure
-----------------------------------
The following code returns a description of the structure of the input controls for a given container.
```java
        OperationResult<ReportInputControlsListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .get();
		
		// or
		
 OperationResult<ReportInputControlsListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport(REPORT_URI)
                .get();
 ReportInputControlsListWrapper result = operationResult.getEntity();
```
The response contains the structure of the input controls for the container. It contains the information needed by your application to display the report parameters to your users and allow them to make a selection. In particular, this includes any cascading structure as a set of dependencies between container parameters. Each input control also has a type that indicates how the user should be allowed to make a choice: `bool, singleSelect, singleSelectRadio, multiSelectCheckbox, multiSelect, singleValue, singleValueText, singleValueNumber, singleValueDate, singleValueDatetime, singleValueTime`.
The structure includes a set of validation rules for each report parameter. These rules indicate what type of validation your client should perform on input control values it receives from your users, and if the validation fails, the message to display. Depending on the type of the report parameter, the following validations are possible:
* mandatoryValidationRule – This input is required and your client should ensure the user enters a value.
* dateTimeFormatValidation – This input must have a data time format and your client should ensure the user enters a valid date and time.
To skip input controls state generation use `excludeState(true)` setting:
```java
        OperationResult<ReportInputControlsListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .excludeState(Boolean.TRUE)
                .get();
		
		// or
		
OperationResult<ReportInputControlsListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport(REPORT_URI)
                .excludeState(true)
                .get();
ReportInputControlsListWrapper result = operationResult.getEntity();
```

Reordering input controls structure
-----------------------------------
You can change structure of input controls according to client demands using the next code:
```java
        OperationResult<ReportInputControlsListWrapper> reorderedOperationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .reorder(inputParameters);
		
		//or
		
		
OperationResult<ReportInputControlsListWrapper> reorderedOperationResult = session
                .inputControlsService()
                .inputControls()
                .forReport(REPORT_URI)
                .reorder(inputParameters);
```
It is impossible to change input controls except change of theirs order. Sent to server structure MUST be the same as it received
from there, except order.
You cannot modify some values, add or remove control, etc.

Listing input controls values
-----------------------------
The following code returns a description of the possible values of all report parameters for the report. Among these choices, it shows which ones are selected.
```java
        OperationResult<InputControlStateListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .values()
                .get();
		
OperationResult<InputControlStateListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport(REPORT_URI)
                .values()
                .get();
 InputControlStateListWrapper result = operationResult.getEntity();
```
The response contains the structure of the report parameters for the report.
If a selection-type report parameter has a null value, it is given as `NULL`. If no selection is made, its value is given as `NOTHING`.
Use setting `useCashedData(false)` to avoid getting cashed data:
```java
 OperationResult<InputControlStateListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .container("/reports/samples/Cascading_multi_select_report")
                .values()
                .useCashedData(false)
                .get();
InputControlStateListWrapper result = operationResult.getEntity();
```

Setting input controls values
-----------------------------
The following code updates the state of specified input controls values, so they are set for the next run of the report.
```java
        OperationResult<InputControlStateListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .values()
                .forInputConrol(id, value1, value2)
                .forInputConrol(id1, value3)
                .update();
		
		//or
		
	MultivaluedHashMap inputControls = new NullableMultivaluedHashMap();
        inputControls.addAll("Country_multi_select", "Mexico");
        inputControls.addAll("Cascading_state_multi_select", "Guerrero", "Sinaloa");

        OperationResult<InputControlStateListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .values()
                .update(inputControls);
		
		// or
				
OperationResult<InputControlStateListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport("/reports/samples/Cascading_multi_select_report")
                .values()
                .forInputControl(id, value)
                .forInputControl(id, value1, value2)
                .run();
InputControlStateListWrapper result = operationResult.getEntity();
```
In response you get updated values for specified input controls. If you want to get updated values with full structure of input controls, you should use `includeFullStructure(true)` setting:
```java
        OperationResult<InputControlStateListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .values()
                .forInputConrol(id, value1, value2)
                .forInputConrol(id1, value3)
		.includeFullStructure(true)
                .update();
		
		// or
		
		
OperationResult<InputControlStateListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport("/reports/samples/Cascading_multi_select_report")
                .values()
                .forInputControl(id, value)
                .forInputControl(id, value1, value2)
		.includeFullStructure(true)
                .run();
InputControlStateListWrapper result = operationResult.getEntity();
```
To uptate values of input controls with their structure in the same request: 
```java
        MultivaluedHashMap<String, String> inputControls = new NullableMultivaluedHashMap();
        inputControls.addAll("Cascading_state_multi_select", "OR", "CA", "WA");
        inputControls.addAll("Country_multi_select", "USA", "Canada");

        final OperationResult<ReportInputControlsListWrapper> operationResult = session
                .reportingService()
                .report(REPORT_URI)
                .inputControls()
                .values()
                .updateAndReorder(inputControls);
		
		// or
		
        final OperationResult<ReportInputControlsListWrapper> operationResult = session
                .inputControlsService()
                .inputControls()
                .forReport("/reports/samples/Cascading_multi_select_report")
                .values()
                .updateAndReorder(inputControls);
```
Administration services:
========================
Only administrative users may access the REST services for administration.

Organizations service
---------------------
It provides methods that allow you to list, view, create, modify, and delete organizations (also known as tenants). Because the organization ID is used in the URL, this service can operate only on organizations whose ID is less than 100 characters long and does not contain spaces or special symbols. As with resource IDs, the organization ID is permanent and cannot be modified for the life of the organization.

### Searching for Organizations
The service searches for organizations by ID, alias, or display name. If no search is specified, it returns a list of all organizations. Searches and listings start from but do not include the
logged-in user’s organization or the specified base.
```java
OperationResult<OrganizationsListWrapper> result = session
        .organizationsService()
        .allOrganizations()
        .parameter(OrganizationParameter.INCLUDE_PARENTS, "true")
        .get();
```

### Viewing an Organization
The `organization()` method with an organization ID retrieves a single descriptor containing the list of properties for the organization. When you specify an organization, use its unique ID, not its path.
```java
OperationResult<ClientTenant> result = session
        .organizationsService()
        .organization("myOrg1")
        .get();
```
Also you may specify organization as object:
```java
ClientTenant organization = new ClientTenant();
organization.setId("test_Id");

OperationResult<ClientTenant> result = session
        .organizationsService()
        .organization(organization)
        .get();
```

### Creating an Organization
To create an organization, put all information in an organization descriptor, and include it in a request to the `rest_v2/organizations` service, with no ID specified. The organization is created in the organization specified by the `parentId` value of the descriptor.
```java
OperationResult<Organization> result = session
        .organizationsService()
        .organization(organization)
        .create();
```
The another way to create organization is to use `createOrUpdate()` method:
```java
OperationResult<Organization> result = session
        .organizationsService()
        .organization(organization)
        .createOrUpdate(organization);
```
Be carefully using this method because you can damage existing organization if the `organizationId` of new organization is already used.
The descriptor is sent in the request should contain all the properties you want to set on the new organization. Specify the `parentId` value to set the parent of the organization, not the `tenantUri` or `tenantFolderUri` properties.
However, all properties have defaults or can be determined based on the alias value. The minimal descriptor necessary to create an organization is simply the alias property. In this case, the organization is created as child of the logged-in user’s home organization.

### Modifying Organization Properties
To modify the properties of an organization, use the `update` method and specify the organization ID in the URL. The request must include an organization descriptor with the values you want to change. You cannot change the ID of an organization, only its name (used for display) and its alias (used for logging in).
```java
Organization organization = new Organization();
organization.setAlias("lalalaOrg");

OperationResult<ClientTenant> result = session
        .organizationsService()
        .organization("myOrg1")
        .createOrUpdate(organization);
```

### Deleting an Organization
To delete an organization, use the `delete()` method and specify the organization ID in the `organization()` method. When deleting an organization, all of its resources in the repository, all of its sub-organizations, all of its users, and all of its roles are permanently deleted.
```java
OperationResult<ClientTenant> result = session
        .organizationsService()
        .organization("myOrg1")
        .delete();
```

Users service
-------------
It provides methods that allow you to list, view, create, modify, and delete user accounts, including setting role membership.
Because the user ID is used in the URL, this service can operate only on users whose ID is less than 100 characters long and does not contain spaces or special symbols. As with resource IDs, the user ID is permanent and cannot be modified for the life of the user account.

### Searching for Users
You can search for users by name or by role. If no search is specified, service returns all users.
```java
OperationResult<UsersListWrapper> operationResult =
        session
                .usersService()
                .allUsers()
                .param(UsersParameter.REQUIRED_ROLE, "ROLE_USER")
                .get();

UsersListWrapper usersListWrapper = operationResult.getEntity();
```

### Viewing a User
Method `username()` with a user ID (username) retrieves a single descriptor containing the full list of user properties and roles.
```java
OperationResult<ClientUser> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .user("jasperadmin")
                .get();

ClientUser user = operationResult.getEntity();
```
Also you may specify user as object:
```java
ClientUser userObject = new ClientUser()
                .setUsername("test_user")
                .setPassword("test_password")
                .setEmailAddress("john.doe@email.net")
                .setEnabled(true)
                .setExternallyDefined(false)
                .setFullName("John Doe");
OperationResult<ClientUser> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .user(userObject)
                .get();

ClientUser user = operationResult.getEntity();
```
The full user descriptor includes detailed information about the user account, including any roles.

### Creating a User
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
    .user(user.getUsername())
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
    .user(user.getUsername())
    .createOrUpdate(user);
```

### Modifying User Properties
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
    .user("john.doe")
    .createOrUpdate(user);
```

### Deleting a User
To delete a user, call the `delete()` method and specify the user ID in the `username()` method.
```java
client
    .authenticate("jasperadmin", "jasperadmin")
    .usersService()
    .user(user.getUsername())
    .delete();
```

Attributes service
------------------
Attributes, also called profile attributes, are name-value pairs associated with a user, organization or server. Certain advanced features such as Domain security and OLAP access grants use profile attributes in addition to roles to grant certain permissions. Unlike roles, attributes are not pre-defined, and thus any attribute name can be assigned any value at any time.
Attributes service provides methods for reading, writing, and deleting attributes on any given holder (server, organization or user account). All attribute operations apply to a single specific holder; there are no operations for reading or searching attributes from multiple holders.
As the holder's id is used in the URL, this service can operate only on holders whose ID is less than 100 characters long and does not contain spaces or special symbols. In addition, both attribute names and attribute values being written with this service are limited to 255 characters and may not be empty (null) or not contain only whitespace characters.

### Viewing User Attributes
The code below allow you to retrieve single attribute defined for the user:
```java
   HypermediaAttribute userAttribute = session
                   .attributesService()
                   .forUser("jasperadmin")
                   .attribute(attribute.getName())
                   .get()
                   .getEntity();
 ```
 You may work work with user as object:
```java
    CleintUser userObject = new ClientUser();
    userObject.setName("jasperadmin");
    HypermediaAttribute userAttribute = session
                       .attributesService()
                       .forUser(userObject)
                       .attribute(attribute.getName())
                       .get()
                       .getEntity();
```
 If user belong to organization you may specify it by name or as object:
```java
 HypermediaAttribute userAttribute = session
                 .attributesService()
                 .forOrganization("organization_1")
                 .forUser("jasperadmin")
                 .attribute(attribute.getName())
                 .get()
                 .getEntity();

  ClientTenant orgObject = new CleintTenant();
  orgObject.setId("someId");
 
 HypermediaAttribute userAttribute = session
                 .attributesService()
                 .forOrganization(orgObject)
                 .forUser("jasperadmin")
                 .attribute(attribute.getName())
                 .get()
                 .getEntity();
```
  
The code below retrieves the list of attributes defined for the user.
```java
   HypermediaAttribute userAttribute = session
                   .attributesService()
                   .forUser("jasperadmin")
                   .attribute("attributeName", "attributeName1", "attributeName2")
                   .get()
                   .getEntity();
   ```
 If user belong to organization you may specify organization:
```java
 HypermediaAttribute userAttribute = session
                 .attributesService()
                 .forOrganization("organization_1")
                 .forUser("jasperadmin")
                 .allAttributes()
                 .get()
                 .getEntity();
 ```
You can get the list of all attributes that includes the name and value of each attribute:
 ```java
 List<HypermediaAttribute> attributes = session
                 .attributesService()
                 .forOrganization("organization_1")
                 .forUser("jasperadmin")
                 .allAttributes()
                 .get()
                 .getEntity()
                 .getProfileAttributes();
```
 Each attribute may only have one value, however that value may contain a comma-separated list that is interpreted by the server as being multi-valued.

### Setting User Attributes
The `createOrUpdate()` method of the attributes service adds or replaces attributes on the specified user. The list of attributes defines the name and value of each attribute. Each attribute may only have one value, however, that value may contain a comma separated list that is interpreted by the server as being multi-valued.
There are two syntaxes, the following one is for adding or replacing all attributes
```java
HypermediaAttributesListWrapper serverAttributes = new HypermediaAttributesListWrapper();
serverAttributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_1").setValue("test_value_1")),
                                             new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_2").setValue("test_value_2"))));
       session
                       .attributesService()
                       .forOrganization("organization_1")
                       .forUser("jasperadmin")
                       .attributes("test_attribute_1","test_attribute_2")
                       .createOrUpdate(serverAttributes);
```

Be careful with definition of attribute names because the server uses different strategies for creating or updating attributes depending on list of attribute names, list of attributes and existing attributes on the server:
 1. if requested attributes' names in `attributes()` method matches with attribute name of object defined in `createOrUpdate()` method and the attribute does not exist on the server it will be *created* on the server;
 2. if requested attributes' names in `attributes()` method matches with attribute name of object defined in `createOrUpdate()` method and the attribute exists on the server it will be *updated* on the server;
 3. if requested attributes' names in `attributes()` method does not  match with any attribute names of object defined in `createOrUpdate()` method and the attribute exists on the server it will be *deleted* on the server;
 4. if requested attribute in `createOrUpdate()` method  method does not  match with any attribute names in `attributes()` it will be *ignored* and will not be sent to the server;
 5. if requested list of attributes' names  in `attributes()` method is empty or you use `allAttributes()` method and pass attributes in `createOrUpdate()` method  -  the existing attributes on the server it will be complitely *replaced* with passed ones:
 ```java
  session
                       .attributesService()
                       .forOrganization("organization_1")
                       .forUser("jasperadmin")
                       .allAttributes()
                       .createOrUpdate(serverAttributes);
 ```
The second way of using the attributes service is adding or replacing individual attribute:
```java
        HypermediaAttribute attribute = new HypermediaAttribute();
                attribute.setName("test_attribute");
                attribute.setValue("test_value");

        session
                        .attributesService()
                        .forOrganization("organization_1")
                        .forUser("jasperadmin")
                        .attribute("test_attribute")
                        .createOrUpdate(attribute)
                        .getEntity();
```

### Deleting User Attributes
The `delete()` method of the attributes service removes attributes from the specified user. When attributes are
removed, both the name and the value of the attribute are removed, not only the value.
There are two syntaxes, the following one is for deleting multiple attributes or all attributes at once.
```java
 session
                .attributesService()
                .forOrganization("organization_1")
                .forUser("jasperadmin")
                .attributes("test_attribute_1","test_attribute_2")
                .delete();

// or
 session
                .attributesService()
                .forOrganization("organization_1")
                .forUser("jasperadmin")
                .allAttributes()
                .delete();


```
The second syntax deletes a single attribute for the specified user:
```java
session
                .attributesService()
                .forOrganization("organization_1")
                .forUser("jasperadmin")
                .attribute("attributeName")
                .delete();
```

### Viewing Organization Attributes
The code below retrieves the list of attributes, if any, defined for the organization.
```java
List<HypermediaAttribute> attributes = session
                .attributesService()
                .forOrganization("organization_1")
                .allAttributes()
                .get()
                .getEntity()
                .getProfileAttributes();
```
You can retrieve any specified attributes. In this case all you need is to define required attributes. See code snippet below:
```java
List <HypermediaAttribute> attributes = session
        .attributesService()
        .forOrganization("organization_1")
        .attributes("number_of_employees", "number_of_units", "country_code")
        .get()
        .getEntity()
        .getAttributes();
```
Or to get a single organization attribute.
```java
HypermediaAttribute attributes = session
        .attributesService()
        .forOrganization("organization_1")
        .attribute("industry")
        .get()
        .getEntity();
```

### Setting Organization Attributes
Service allows you to create new organization attributes. See code below:
```java
HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
attributes.setProfileAttributes(asList(
        new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_1").setValue("test_value_1")),
        new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_2").setValue("test_value_2"))));
                
OperationResult<HypermediaAttributesListWrapper> attributes = session
                .attributesService()
                .forOrganization("organization_1")
                .attributes(asList("test_attribute_1", "test_attribute_2")
                .createOrUpdate(serverAttributes);
```
If you want to replace all existing attributes with new ones:
```java
OperationResult<HypermediaAttributesListWrapper> attributes = session
                .attributesService()
                .forOrganization("organization_1")
                .allAttributes()
                .createOrUpdate(serverAttributes);
```
Be careful with definition of attribute names because the server uses different strategies for creating or updating attributes depending on list of attribute names, list of attributes and existing attributes on the server (see section [Setting User Attributes] (https://github.com/Jaspersoft/jrs-rest-java-client#setting-user-attributes)).
Or to create a single organization attribute code below:
```java
HypermediaAttribute attribute = new HypermediaAttribute(new ClientTenantAttribute().setName("industry").setValue("IT"));
OperationResult<HypermediaAttribute> retrieved = session
        .attributesService()
        .forOrganization("organization_1")
        .attribute("industry")
        .createOrUpdate(attribute);
```
Attribute name should not exist on the server and match with `name` field of `attribute` object, otherwise the attribute will be deleted. 

### Deleting Organization Attributes
You can also delete a single organization attribute.
```java
OperationResult<HypermediaAttribute> operationResult = session
                .attributesService()
                .forOrganization("organization_1")
                .attribute("industry")
                .delete();
```
Or to delete list of attributes:
```java
OperationResult<HypermediaAttributesListWrapper> operationResult = session
                .attributesService()
                .forOrganization(orgName)
                .attributes("number_of_employees", "country_code")
                .delete();
```

### Viewing Server Attributes
We have also provided service to get server attributes. Code below return available server attributes. 
```java
List<HypermediaAttribute> attributes = session
        .attributesService()
        .allAttributes()
        .get()
        .getEntity()
        .getProfileAttributes();
```
Or you can specify any concrete attribute.
```java
HypermediaAttribute entity = session
                .attributesService()
                .attribute("attribute_name")
                .get()
                .getEntity();
```
### Setting Server Attributes
It is possible to create new server attributes.
```java
HypermediaAttributesListWrapper serverAttributes = new HypermediaAttributesListWrapper();
        serverAttributes.setProfileAttributes(asList(
                new HypermediaAttribute(new ClientUserAttribute().setName("max_threads").setValue("512")),
                new HypermediaAttribute(new ClientUserAttribute().setName("admin_cell_phone").setValue("03"))));

OperationResult<HypermediaAttributesListWrapper> attributes = session
                .attributesService()
                .attributes("max_threads", "admin_cell_phone")
                .createOrUpdate(newServerAttributes);
```
If you want to replace all existing attributes with new ones:
```java
OperationResult<HypermediaAttributesListWrapper> attributes = session
                .attributesService()
                .allAttributes()
                .createOrUpdate(serverAttributes);
```
Be careful with definition of attribute names because the server uses different strategies for creating or updating attributes depending on list of attribute names, list of attributes and existing attributes on the server (see section [Setting User Attributes] (https://github.com/TanyaEf/jrs-rest-java-client#setting-user-attributes)).
To create a single server attribute:
```java
HypermediaAttribute attribute = new HypermediaAttribute(new ClientUserAttribute().setName("latency").setValue("5700"));

        session
                .attributesService()
                .attribute("latency")
                .createOrUpdate(attribute);
```
Attribute name should not exist on the server and match with `name` field of `attribute` object, otherwise the attribute will be deleted. 

### Deleting Server Attributes
You can also delete all server attribute.
```java
        session
                .attributesService()
                .allAttributes()
                .delete()
                .getEntity();
```
You can also delete a single server attribute.
```java
        session
                .attributesService()
                .attribute("latency")
                .delete();
```
Or any specified attributes.
```java
session
                .attributesService()
                .attributes("max_threads", "admin_cell_phone")
                .delete();
```

### Getting attributes permissions
Since `6.1` version of `JaspersoftReportServer` you can obtain attributes with permissions using additional parameter `setIncludePermissions()`:
```java

 HypermediaAttribute entity = session
                 .attributesService()
                 .attribute("attribute_name")
                 .setIncludePermissions(true)
                 .get()
                 .getEntity();
```
Pay attention, the setting `setIncludePermission()` specify only the **server response format**, you can not set any permissions with this setting.

### Seasching Attributes
To get full list of attributes with specified parameters use the next code:
```java
        session
            .attributesService()
            .allAttributes()
            .parameter(AttributesSearchParameter.HOLDER, "/")
            .parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.AWS)
            .parameter(AttributesSearchParameter.OFFSET, 20)
            .parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE)
            .search();
    HypermediaAttributesListWrapper attributes = operationResult.getEntity();
```
Supported parameters are:
**holder** - represent the target holder, attributes should be fetched from;
**group** - attribute group;
**custom** - custom attributes(doesn't affect on server);
**log4j** - logger specific attributes;
**mondrian** - server attributes that make affect on Mondrian engine;
**aws** - aws specific server attributes;
**jdbc** - jdbc drivers specific attributes;
**adhoc** - adhoc specific attributes;
**ji** - profiling attributes;
**customServerSettings** - updated server settings(changed log4j, mondrian, aws, jdbc, adhoc, ji server setting).
**recursive** - flag indicates if attributes will be fetched also from lower level;
**includeInherited** - flag indicates if search should include also higher level attributes, relatively to target holder;
**offset** - pagination, start index for requested pate;
**limit** - pagination, resources count per page.

You can also specified names of attributes:

```java
        session
            .attributesService()
            .attributes("attrName1", "attrName2")
            .parameter(AttributesSearchParameter.HOLDER, "/")
            .parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM)
            .parameter(AttributesSearchParameter.OFFSET, 20)
            .parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE)
            .search();
    HypermediaAttributesListWrapper attributes = operationResult.getEntity();
```
Notice, for root 'HOLDER` is `/`, for organization - `organizationId`, for user in organization - 'organizationId/userName'.
To specify the holder you can use the existing API:
```java
        session
            .attributesService()
            .forOrganization("/")
            .forUser("jasperadmin")
            .attributes("attrName1", "attrName2")
            .parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM)
            .parameter(AttributesSearchParameter.OFFSET, 20)
            .parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE)
            .search();
    HypermediaAttributesListWrapper attributes = operationResult.getEntity();
```

### Getting attributes permissions
Since `6.1` version of `JaspersoftReportServer` you can obtain attributes with permissions using additional parameter `setIncludePermissions()`:
```java

 HypermediaAttribute entity = session
                 .attributesService()
                 .attribute("attribute_name")
                 .setIncludePermissions(true)
                 .get()
                 .getEntity();
```
Pay attention, the setting `setIncludePermission()` specify only the **server response format**, you can not set any permissions with this setting.

The Roles Service
-----------------
It provides similar methods that allow you to list, view, create, modify, and delete roles. The new service provides improved search functionality, including user-based role searches. Because the role ID is used in the URL, this service can operate only on roles whose ID is less than 100 characters long and does not contain spaces or special symbols. Unlike resource IDs, the role ID is the role name and can be modified.

### Searching for Roles
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

### Viewing a Role
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

### Creating a Role
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

### Modifying a Role
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

### Setting Role Membership
To assign role membership to a user, set the roles property on the user account with the PUT method of the rest_
v2/users service. For details, see section [creating a user](https://github.com/Jaspersoft/jrs-rest-java-client/blob/master/README.md#creating-a-user).

### Deleting a Role
To delete a role, send the DELETE method and specify the role ID (name) in the URL.
When this method is successful, the role is permanently deleted.
```java
OperationResult<ClientRole> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .rolesService()
                .rolename("ROLE_HELLO")
                .delete();
Response response = operationResult.getResponse();
```

Settings Service
----------------
It provides method that allow you to get server specific settings, required by UI to work with the server in sync. There can be formats and patterns, modes for some modules etc.

### Getting server specific settings
To get settings, use the `getEntity()` method and specify the group of settings in the `group()` method and class of entity as shown below. The method `getEntity()` returns instance of specified class:
```java 
final Map settings = session
                .settingsService()
                    .settings()
                        .group(group, Map.class)
                            .getEntity();

``` 
Please notice, you can get settings of user’s time zones  in this way as List only:
```java
final List settings = session
        .settingsService()
        .settings()
        .group("userTimeZones", List.class)
        .getEntity();
```
Supported groups of settings are: 

1.	“request”. Settings related to current AJAX request configuration. Returned settings are: maxInactiveInterval, contextPath;

2.	“dataSourcePatterns”. Validation patterns for data source UI. Returned settings are: dbHost, dbPort, dbPort, dbName, sName, driverType, schemaName, informixServerName, dynamicUrlPartPattern;

3.	“userTimeZones”. Time zones of current user. Returned settings are pairs of code and description of time zone;

4.	“globalConfiguration”. AWS specific settings. Returned settings are : paginatorItemsPerPage, paginatorPagesRange, reportLevelConfigurable, paginationForSinglePageReport, calendarInputJsp, userItemsPerPage, roleItemsPerPage, tenantItemsPerPage, userNameNotSupportedSymbols, roleNameNotSupportedSymbols, userNameSeparator, defaultRole, passwordMask, viewReportsFilterList, outputFolderFilterList, outputFolderFilterPatterns, tenantNameNotSupportedSymbols, tenantIdNotSupportedSymbols, resourceIdNotSupportedSymbols, publicFolderUri, themeDefaultName, themeFolderName, themeServletPrefix, dateFormat, currentYearDateFormat, timestampFormat, timeFormat, entitiesPerPage, tempFolderUri, organizationsFolderUri, jdbcDriversFolderUri, emailRegExpPattern, enableSaveToHostFS, allFileResourceTypes, dataSourceTypes;

5.	“awsSettings”. AWS specific settings. Returned settings are: productTypeIsEc2, isEc2Instance, productTypeIsJrsAmi, awsRegions, productTypeIsMpAmi, suppressEc2CredentialsWarnings;

6.	“decimalFormatSymbols”. Response is locale dependent;

7.	“dateTimeSettings”. All settings related to client date-time formatting. Response is locale dependent; 

8.	“dashboardSettings”. Settings depend on configuration of Jaspersoft server;

9.	“inputControls”. Different settings for input controls. Configuration of settings depend on configuration of Jaspersoft server;

10.	“metadata”. Configuration of settings depends on configuration of Jaspersoft server;

11.	“adhocview”.  Different configuration dictionary values and lists for ad hoc. Configuration of settings depends on configuration of Jaspersoft server.

There is another way to get settings using specified methods for groups of settings that return specific object of settings:
 ```java
 Final RequestSettings settings = session
               .settingsService()
                .settings()
                .ofRequestGroup()
                .getEntity();
```
Pleace notice, you should use List interface to get user’s time zones setting in this way:
```java
final List<UserTimeZone> settings = session
        .settingsService()
        .settings()
        .ofUserTimeZonesGroup()
        .getEntity();
```
Or you can get List of specified DTO for user’s time zones using GenericType class:
```java
final List<UserTimeZone> settings = session
        .settingsService()
        .settings()
        .group("userTimeZones", new GenericType<List<UserTimeZone>>() {})
        .getEntity();
```
Supported specified methods are:
```java
OperationResult<RequestSettings> ofRequestGroup();
OperationResult<DataSourcePatternsSettings> ofDataSourcePatternsGroup();
OperationResult<List<UserTimeZone>> ofUserTimeZonesGroup();
OperationResult<AwsSettings> ofAwsGroup();
OperationResult<DecimalFormatSymbolsSettings> ofDecimalFormatSymbolsGroup();
OperationResult<DashboardSettings> ofDashboardGroup();
OperationResult<GlobalConfigurationSettings> ofGlobalConfigurationGroup();
OperationResult<DateTimeSettings> ofDateTimeGroup();
OperationResult<InputControlsSettings> ofInputControlsGroup();
```

Repository Services
=====================

Resources Service
-----------------
This service provides greater performance and more consistent handling of resource descriptors for all repository resource types. The service has two formats, one takes search parameters to find resources, the other takes a repository URI to access resource descriptors and file contents.

### Searching the Repository
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

### Viewing Resource Details
Use the `resource(uri)` method and a resource URI with `details()` method to request the file resource's complete descriptor.
```java
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/properties/GlobalPropertiesList")
        .details();
```
To get details of resource with particular type, use `detailsForType(resourceClass)` method:
```java
OperationResult<ClientResource> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .resourcesService()
        .resource("/properties/GlobalPropertiesList")
        .detailsForType();
```


### Downloading File Resources
There are two operations on file resources:
* Viewing the file resource details to determine the file format
* Downloading the binary file contents

To view the file resource details, specify the URL of the file in `resource()` method and use the code form [Viewing Resource Details](https://github.com/Jaspersoft/jrs-rest-java-client/blob/master/README.md#viewing-resource-details) section.
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

### Creating a Resource
The `create()` and `createOrUpdate()` methods offer two alternative ways to create resources. Both take a resource descriptor but each handles the URL differently and use different HTTP methods. With the `create()` method, specify a folder in the URL, and the new resource ID is created automatically from the label attribute in its descriptor in body of  POST request. With the `createOrUpdate()` method, specify a unique new resource ID as part of the URL in `resource()` method and uses PUR method.
```java
        ClientFolder new Folder = new ClientFolder();
        folder
                .setLabel("testFolder")
                .setDescription("Test folder");

        final OperationResult<ClientResource> operationResult = session
                .resourcesService()
                .resource(newFolder)
                .inFolder("/public")
                .create();
//OR
        ClientFolder folder = new ClientFolder();
        folder
                .setUri("/public/testFolder1")
                .setLabel("testFolder1")
                .setDescription("Test folder")
                .setVersion(-1);

        OperationResult<ClientResource> operationResult = session
                .resourcesService()
                .resource(folder.getUri())
                .createOrUpdate(folder);
```
#### Creating File resource
The REST client allows to create file resource in few ways:
 - **as direct streaming** 
 ```java
 	ClientFile fileResourceDescriptor = new ClientFile()
                                .setLabel("testImage.jpg")
                                .setDescription("test description")
                                .setType(ClientFile.FileType.img);
         OperationResult<ClientFile> result = session
                .resourcesService()
                .fileResource(new FileInputStream(pathToFile), fileResourceDescriptor)
                .asInputStream()
                .inFolder("/public")
                .create();
 ```
 - **as Base64** encoded content:
 ```java
         final ClientFile resourceDescriptor = new ClientFile().setLabel("testImage1.jpg")
                .setDescription("test description")
                .setType(ClientFile.FileType.img);
        
        OperationResult<ClientFile> result = session
                .resourcesService()
                .fileResource(new FileInputStream(pathToFile), resourceDescriptor)
                .asBase64EncodedContent()
                .inFolder("/public")
                .create();
 ```
  - **as multipart form** :
  ```java
          final ClientFile resourceDescriptor = new ClientFile()
                .setLabel("testImage2.jpg")
                .setDescription("test description")
                .setType(ClientFile.FileType.img);
        OperationResult<ClientFile> result = session
                .resourcesService()
                .fileResource(new FileInputStream(pathToFile),
                        resourceDescriptor)
                .asMultipartForm()
                .inFolder("/public")
                .create();
  ```
### Modifying a Resource
Use the `createOrUpdate()` method above to overwrite an entire resource. Specify the path of the target resource in the `resource()` method and specify resource of the same type. Use `parameter(ResourceServiceParameter.OVERWRITE, "true")` to replace a resource of a different type. The resource descriptor must completely describe the updated resource, not use individual fields. The descriptor must also use only references for nested resources, not other resources expanded inline. You can update the local resources using the hidden folder _file.
```java
        OperationResult<ClientResource> result = session
                .resourcesService()
                .resource(testResourceUri)
                .createOrUpdate(testResource.setLabel("New test label").setDescription("new test description"));
```
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

### Copying a Resource
To copy a resource, specify in `toFolder()` method its URI and in `resource()` method URI of destination location.
```java
        OperationResult<ClientResource> clientResource = session.resourcesService()
                .resource(testDomainUri)
                .inFolder("/public/testFolder")
                .copy();
```

### Moving a Resource
To move a resource, specify in `moveFrom()` method its URI and in `resource()` method URI of destination location.
```java
        OperationResult<ClientResource> clientResource = session.resourcesService()
                .resource(testResourceUri)
                .inFolder("/public/testFolder")
                .move();
```
### Uploading complex resources
RestClient also provides API that allows to create complex resources and their nested resources in a single multipart request. Supported resources are:
	- SemanticLayerDataSource;
	- Domain;
	- ReportUnit;
	- MondrianConnection;
	- SecureMondrianConnection.
	
#### Uploading SemanticLayerDataSource
SemanticLayerDataSource nested resources (schema, bundles and security file) might be specified as java.io.InputStream, java.io.File, content as java.lang.String or resource descriptor (with resource's URI or BASE64 encoded content). 
```java
        OperationResult<ClientSemanticLayerDataSource> testResource =
                session.resourcesService()
                        .semanticLayerDataSourceResource()
                        .withDataSource(new ClientReference().setUri(datasourceUri))
                        .withSchema(schemaInputStream, schemaFilelabel, schemaFileDescription)
			//OR .withSchema(schemaStringXmlContent, label, description)
			//OR .withSchema(schemaUri)
			.withSecurityFile(securityFileInputStream, label, description)
			//OR .withSecurityFile(securityFile, label, description)
			//OR .withSecurityFile(securityFileXmlStringCntent, label, description)
			//OR .withSecurityFile(securityFileDecriptor)
			.withBundle(bundleInputStream, label, description)
			//OR .withBundle(bundleFile, label, description, locale)
			//OR .withBundle(bundleSringContent, label, description, locale)
			//OR .withBundle(bundleDescriptor)
			//OR .withBundles(bundlesDescriptorsList)
			.withLabel("testDomain")
                        .withDescription("testDescription")
                        .inFolder("/public")
                        .create();        
```
The other way to create SemanticLayerDataSource is to decribe it in resourceDescriptor. In this case nested resources (schema, bundles and security file) can be specified as resource descriptors (with resource's URI or BASE64 encoded content). 
```java
        ClientSemanticLayerDataSource resourceDescriptor = new ClientSemanticLayerDataSource()
                .setSchema(new ClientFile()
				.setUri(schemaUri)
				.setType(ClientFile.FileType.xml)
				.setLabel("schema.Xml"))
                .setDataSource(new ClientReference().setUri(domain.getDataSource().getUri()))
                .setLabel("testDomain");
		
        OperationResult<ClientSemanticLayerDataSource> testResource =
                session.resourcesService()
                        .semanticLayerDataSourceResource(resourceDescriptor)
                        .inFolder("/public")
                        .create();        
```
or
```java
 ClientSemanticLayerDataSource resourceDescriptor = new ClientSemanticLayerDataSource()
                .setDataSource(new ClientReference().setUri(domain.getDataSource().getUri()))
                .setLabel("testDomain")
		.setDescription("testDescription");
        OperationResult<ClientSemanticLayerDataSource> testResource =
                session.resourcesService()
                        .semanticLayerDataSourceResource(resourceDescriptor)
                        .withSchema(schemaInputStream, schemaFilelabel, schemaFileDescription)
			//OR .withSchema(schemaStringXmlContent, label, description)
			//OR .withSchema(schemaUri)
			.withSecurityFile(securityFileInputStream, label, description)
			//OR .withSecurityFile(securityFile, label, description)
			//OR .withSecurityFile(securityFileXmlStringCntent, label, description)
			//OR .withSecurityFile(securityFileDecriptor)
			.withBundle(bundleInputStream, label, description)
			//OR .withBundle(bundleFile, label, description, locale)
			//OR .withBundle(bundleSringContent, label, description, locale)
			//OR .withBundle(bundleDescriptor)
			//OR .withBundles(bundlesDescriptorsList)
			.inFolder("/public")
                        .create();        
```

#### Uploading MondrianConnection
REST Client allows you to create `MondrianConnection` resource with mondrian schema XML file specified as java.io.InputStream, java.io.File or as java.lang.String. 
  
```java
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.mondrianConnection()
            	.withMondrianSchema(schemaInputStream, label, description)
	    	//OR .withMondrianSchema(schemaFile, label, description)
	    	//OR .withMondrianSchema(schemaXmlStringContent, label, description)
	    	.withDatasource(dataSource)
	    	.withLabel(label)
		.withDescription(description)
		.inFolder(parentFolderUri)
		.create()
```
or
```java
ClientMondrianConnection resourceDescriptor = new ClientMondrianConnection()
		.setDatasource(dataSource)
	    	.setLabel(label)
		.setDescription(description)
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.mondrianConnection(resourceDescriptor)
            	.withMondrianSchema(schemaInputStream, label, description)
	    	//OR .withMondrianSchema(schemaFile, label, description)
	    	//OR .withMondrianSchema(schemaXmlStringContent, label, description)
	    	.inFolder(parentFolderUri)
		.create()
```
or create `MondrianConnection` using resource descriptor:
```java
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.mondrianConnection(respurceDescriptor)
	    	.inFolder(parentFolder)
		.create()
```

#### Uploading SecureMondrianConnection
REST Client allows you to create `MondrianConnection` resource with mondrian schema XML and AccessGrantSchemas specified as java.io.InputStream, java.io.File or as java.lang.String. 
  
```java
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.secureMondrianConnection()
            	. withMondrianSchema(schemainputStream, label, description)
	    	//OR .withMondrianSchema(schemaFile, label, description)
	    	//OR .withMondrianSchema(schemaXmlStringContent, label, description) 
	    	.withDatasource(dataSource)
	    	.withLabel(label)
		.withDescription(description)
		.inFolder(parentFolderUri)
		.create()
```
or
```java
	ClientMondrianConnection resourceDescriptor = new ClientMondrianConnection()
		.setDatasource(dataSource)
	    	.setLabel(label)
		.setDescription(description)
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.secureMondrianConnection(respurceDescriptor)
            	. withMondrianSchema(schemainputStream, label, description)
	    	//OR .withMondrianSchema(schemaFile, label, description)
	    	//OR .withMondrianSchema(schemaXmlStringContent, label, description) 
	    	.withDatasource(dataSource)
	    	.withLabel(label)
		.withDescription(description)
		.inFolder(parentFolderUri)
		.create()
```
or create `MondrianConnection` using resource descriptor:
```java
	ClientMondrianConnection connection = session
    		.resourcesService()
        	.secureMondrianConnection(respurceDescriptor)
	    	.inFolder(parentFolder)
		.create()
```

#### Uploading ReportUnit
To upload `ReportUnit` resource to the server you can use next API, where JRXML file can be added as java.lang.String, java.io.File,  java.io.InputStream or as resource URI in resource descriptor. A bunch of various additional files like images and others can be added as well.
```java
        OperationResult<ClientReportUnit> repUnut =
                session.resourcesService()
                        .reportUnitResource()
                        .withJrxml(inputStream, label, description)
                        //OR .withJrxml(jrxmlStringContent, label, description)
			//OR .withJrxml(jrxmlFile, label, description) 
			//OR .withJrxml(resourceDescriptor)
			.withFile(new ClientReference().setUri(fileUri), label, fileType)
                        .withFile(new ClientReference().setUri(fileUri), label, fileType)
                        //OR .withFile(InputStream fileData, String label, String description, fileType)
			//OR .withFile(File fileData, String label, String description, fileType)
			//OR .withFile(String fileData, String label, String description, fileType) 
			//OR .withFile(ClientReferenceableFile fileUri, String name, fileType)
			.withLabel("testReport")
                        .withDescription("testDescription")
                        .inFolder("/public")
                        .create();
```
or
```java
	ClientReportUnit resourceDescriptor = new ClientReportUnit()
                .setLabel("testReport")
                .setDescription("testDescription");
		
        OperationResult<ClientReportUnit> repUnut =
                session.resourcesService()
                        .reportUnitResource(resourceDescriptor)
                        .withJrxml(inputStream, label, description)
                        //OR .withJrxml(jrxmlStringContent, label, description)
			//OR .withJrxml(jrxmlFile, label, description) 
			//OR .withJrxml(resourceDescriptor)
			.withFile(new ClientReference().setUri(fileUri), label, fileType)
                        .withFile(new ClientReference().setUri(fileUri), label, fileType)
                        //OR .withFile(InputStream fileData, String label, String description, fileType)
			//OR .withFile(File fileData, String label, String description, fileType)
			//OR .withFile(String fileData, String label, String description, fileType) 
			//OR .withFile(ClientReferenceableFile fileUri, String name, fileType)
                        .inFolder("/public")
                        .create();
```
Also the API allows to upload reportUnit  described in resource descriptor only:
```java
        final ClientReportUnit clientReportUnit = new ClientReportUnit()
                .setLabel("testReport")
                .setDescription("testDescription");
        clientReportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
        clientReportUnit.getFiles().put(label, new ClientFile()
                .setUri(fileUri)
                .setLabel(label)
                .setType(ClientFile.FileType.img));

        clientReportUnit.setJrxml(new ClientFile()
                .setLabel(jrxmlLabel)
                .setType(ClientFile.FileType.jrxml)
                .setContent(base64EncodedContent);

        OperationResult<ClientReportUnit> repUnut =
                session.resourcesService()
                        .reportUnitResource(clientReportUnit)
                        .inFolder("/public")
                        .create();
```
#### Uploading domain
Domain's its nested resources (bundles and security file) might be specified as java.io.InputStream, java.io.File, content as java.lang.String or resource descriptor (with resource's URI or BASE64 encoded content). 
Please, pay attention, unlike SemanticLayerDataSource the Domain resource doesn't reference external schema file, so schema can be specified only as Java object.  
```java
       OperationResult<ClientDomain> resDomain =
                session.resourcesService()
                        .domainResource()
                        .withDataSource(new ClientReference().setUri(datasourceUri))
                        .withSchema(schemaObject)
                        .withLabel("testDomain")
                        .withDescription("testDescription")
                        .inFolder("/public")
                        .create();
        
```
or
```java
	ClientDomain resourceDescriptor = new ClientDomain ()
	                .setDataSource(new ClientReference().setUri(datasourceUri))
                        .setSchema(schemaObject)
                        .setLabel("testDomain")
                        .setDescription("testDescription")
       OperationResult<ClientDomain> resDomain =
                session.resourcesService()
                        .domainResource()
                        .withSchema(schemaObject)
                        .inFolder("/public")
                        .create();
        
```
The other way to create SemanticLayerDataSource is to decribe it in resourceDescriptor. In this case nested resources (schema, bundles and security file) can be specified as resource descriptors (with resource's URI or BASE64 encoded content). 
```java
        ClientDomain resDomain =
                session.resourcesService()
                        .domainResource(domain)
                        .inFolder("/public")
                        .create()       
```

### Deleting Resources
You can delete resources in two ways, one for single resources and one for multiple resources. To delete multiple resources at once, specify multiple URIs with the `ResourceSearchParameter.RESOURCE_URI` parameter.
```java
//multiple
OperationResult result = session
        .resourcesService()
        .resources()
        .parameter(ResourceSearchParameter.RESOURCE_URI, "/some/resource/uri/1")
        .parameter(ResourceSearchParameter.RESOURCE_URI, "/some/resource/uri/2")
        .delete();
//OR
//single
OperationResult result = session
        .resourcesService()
        .resource("/reports/testFolder")
        .delete();
```

The Permissions Service
-----------------------
In the permissions service, the syntax is expanded so that you can specify the resource, the recipient (user name or role name) and the permission value within the URL. This makes it simpler to set permissions because you don’t need to send a resource descriptor to describe the permissions. In order to set, modify, or delete permissions, you must use credentials or login with a user that has “administer” permissions on the target resource.
Because a permission can apply to either a user or a role, the permissions service uses the concept of a “recipient”. A recipient specifies whether the permission applies to a user or a role, and gives the ID of the user or role.
There are two qualities of a permission:
* The assigned permission is one that is set explicitly for a given resource and a given user or role. Not all permissions are assigned, in which case the permission is inherited from the parent folder.
* The effective permission is the permission that is being enforced, whether it is assigned or inherited.

### Viewing Multiple Permissions
```java
        OperationResult<RepositoryPermissionListWrapper> operationResult = session
                .permissionsService()
                .forResource(RESOURCE_URI)
                .permissions()
                .get();
```
Also serch permissions with parameters is supported. Available parameters are:
   **effectivePermissions** - when set to true shows all permissions who affect given uri, if false - only directly assigned. If recipient does not have any permission assigned or permission is not reacheable not uri will be returned. Default - false;
   **recipientType** - type of recipient (e.g. user/role);
   **recipientId** - Id of recipient, requires `recipientType`. For multitenant environment should be tenant qualified.
   **resolveAll** - describes resolving of recipients. Default - false;
   **offset** - pagination. Start index for requested pate;
   **limit** - pagination, resources count per page;

### Viewing a Single Permission
Specify the recipient in the URL to see a specific assigned permission.
```java
        OperationResult<RepositoryPermission> operationResult = session
                .permissionsService()
                .forResource(RESOURCE_URI)
                .permission()
                .permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER")
                .get();

RepositoryPermission permission = operationResult.getEntity();
```

### Setting Multiple Permissions
The `create()` method assigns any number of permissions to any number of resources specified in the body of the request. All permissions must be newly assigned, and the request will fail if a recipient already has an assigned (not inherited) permission. Use the `createOrUpdate()` method to update assigned permissions. The `createOrUpdate()` method modifies exiting permissions (already assigned).
```java
        RepositoryPermissionListWrapper permissions = new RepositoryPermissionListWrapper();
        final RepositoryPermission repositoryPermission = new RepositoryPermission().setUri(RESOURCE_URI).setRecipient("role:/ROLE_USER").setMask(1);
        final RepositoryPermission repositoryPermission1 = new RepositoryPermission().setUri(RESOURCE_URI).setRecipient("role:/ROLE_ADMIN").setMask(1);
        permissions.setPermissions(asList(repositoryPermission, repositoryPermission1));

        OperationResult<RepositoryPermissionListWrapper> operationResult = session
                .permissionsService()
                .permissions(permissions)
                .create();
```

### Setting a Single Permission
The `createNew()` method accepts a single permission descriptor.
```java
        final RepositoryPermission repositoryPermission = new RepositoryPermission()
                .setUri(RESOURCE_URI)
                .setRecipient("role:/ROLE_USER")
                .setMask(1);

        OperationResult operationResult = session
                .permissionsService()
                .permission(repositoryPermission)
                .create();
```

### Deleting Permissions in Bulk
The `delete()` method removes all assigned permissions from the designated resource. After returning successfully, all effective permissions for the resource are inherited.
```java
        OperationResult operationResult = session
                .permissionsService()
                .forResource(RESOURCE_URI)
                .permissions()
                .delete();
```

### Deleting a Single Permission
Specify a recipient in the `permissionRecipient()` method and call the `delete()` method to remove only that permission.
```java
OperationResult operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .forResource(RESOURCE_URI)
		.permission()
                .permissionRecipient(PermissionRecipient.USER, "joeuser")
                .delete();

Response response = operationResult.getResponse();
```

Jobs service
==================
The jobs service provides the interface to schedule reports and manage scheduled reports (also called jobs). In addition, this service provides an API to scheduler features that were introduced in JasperReports Server 4.7, such as bulk updates, pausing jobs, FTP output and exclusion calendars.

Listing Report Jobs
-------------------
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

Viewing a Job Definition
------------------------
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

Extended Job Search
-------------------
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

Scheduling a Report
-------------------
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

Viewing Job Status
------------------
The following method returns the current runtime state of a job:
```java
OperationResult<JobState> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .job(8600)
        .jobState();

JobState jobState = result.getEntity();
```
Response contains the `JobState` status descriptor.

Editing a Job Definition
------------------------
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

Updating Jobs in Bulk
---------------------
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

Pausing Jobs
------------
The following method pauses currently scheduled job execution. Pausing keeps the job schedule and all other details but prevents the job from running. It does not delete the job.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .pauseJobs();
```

Resuming Jobs
-------------
Use the following method to resume any or all paused jobs in the scheduler. Resuming a job means that any defined trigger in the schedule that occurs after the time it is resumed will cause the report to run again. Missed schedule triggers that occur before the job is resumed are never run.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .resumeJobs();
```

Restarting Failed Jobs
----------------------
Use the following method to rerun failed jobs in the scheduler. For each job to be restarted, the scheduler creates an immediate single-run copy of job, to replace the one that failed. Therefore, all jobs listed in the request body will run once immediately after issuing this command. The single-run copies have a misfire policy set so that they do not trigger any further failures (`MISFIRE_ INSTRUCTION_IGNORE_MISFIRE_POLICY`). If the single-run copies fail themselves, no further attempts are made automatically.
```java
OperationResult<JobIdListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .jobs()
        .parameter(JobsParameter.JOB_ID, "8600")
        .restarJobs();
```

Calendars service
=================
The scheduler allows a job to be defined with a list of excluded days or times when you do not want the job to run. For example, if you have a report scheduled to run every business day, you want to exclude holidays that change every year. The list for excluded days and times is defined as a calendar, and there are various ways to define the calendar.  The scheduler stores any number of exclusion calendars that you can reference by name. When scheduling a report, reference the name of the calendar to exclude, and the scheduler automatically calculates the correct days to trigger the report. The scheduler also allows you to update an exclusion calendar and update all of the report jobs that used it. Therefore, you can update the calendar of excluded holidays every year and not need to modify any report jobs.

Listing All Registered Calendar Names
-------------------------------------
The following method returns the list of all calendar names that were added to the scheduler.
```java
OperationResult<CalendarNameListWrapper> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .allCalendars();  

CalendarNameListWrapper calendarNameListWrapper = result.getEntity();
```

Viewing an Exclusion Calendar
-----------------------------
The following method takes the name of an exclusion calendar and returns the definition of the calendar:
```java
OperationResult<Calendar> result = client
        .authenticate("jasperadmin", "jasperadmin")
        .jobsService()
        .calendar("testCalendar")
        .getCalendar();

Calendar jobCalendar = result.getEntity();
```
As a result we have common caledar descriptor `ReportJobCalendar`.

Adding or Updating an Exclusion Calendar
----------------------------------------
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

Deleting an Exclusion Calendar
------------------------------
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

Export service
--------------

### Strat export
The export service works asynchronously: first you request the export with the desired options, then you monitor the state of the export, and finally you request the output file. You must be authenticated as the system admin (superuser)or jasperadmin for the export services.
```java
OperationResult<State> operationResult =
        client
                .authenticate("jasperadmin", "jasperadmin")
                .exportService()
                .newTask()
                .user("jasperadmin")
                .role("ROLE_USER")
                .parameter(ExportParameter.EVERYTHING)
                .create();

State state = operationResult.getEntity();
```
The export parameters you can specify are:

`everything `- export everything except audit data: all repository resources, permissions, report jobs, users, and roles. This option is equivalent to:--uris --repository-permissions --report-jobs --users --roles
(default value is false). 

`repository-permissions `- when this option is present, repository permissions are exported along with each exported folder and resource. This option should only be used in conjunction with uris (default value is false).

`role-users `- when this option is present, each role export triggers the export of all users belonging to the role. This option should only be used in conjunction with --roles (default value is false).

`include-access-events `- access events (date, time, and user name of last modification) are exported (default value is false).

`include-audit-events `- include audit data for all resources and users in the export (default value is false).
 
`include-monitoring-events `- include monitoring events (default value is false).

`include-attributes `- include attributes in export (default value is false).

`skip-attribute-values `- skip attributes values to be exported (default value is false).

`include-server-settings`	- include server settings in export(default value is false).

`skip-suborganizations `- if the parameter is set to true, the system will omit all the items(e.g. resources, user, roles, organizations) which belong to "sub organizations" even they are directly specified using corresponding options (default value is false).

`skip-dependent-resources `- skip dependent resources (domain, datasource etc.) to be exported (default value is false). 

In sent tas DTO you can specify:
`uris` - list of folder or resource URIs in the repository  to export.
`scheduledJobs` - list of repository report unit and folder URIs for which report unit jobs should be exported. For a folder URI, this option exports the scheduled jobs of all reports in the folder and all subfolders.	
`roles` - list of roles to export.
`users` - list of users to export.
`resourceTypes` - list of resource types, that will be included in export. If the parameter is null or empty then will include all resource types.
`organization` - identifier of organization to export together with its sub organizations. If it is specified it also will be the root organization, starting from it system will export all resources, users, roles e.t.c.

```java
        ExportTask exportTask = new ExportTask()
				.setUris(asList(TEST_URI))
				.setUsers(asList("superuser"))
				.setRoles(asList("ROLE_USER"));
        OperationResult<State> stateOperationResult = session
                .exportService()
                .newTask(exportTask)
                .create();
		
	State stateDto = stateOperationResult.getEntity();
        taskId = stateDto.getId();
```

### Check the export state
After receiving the export ID, you can check the state of the export operation.
```java
        OperationResult<State> stateOperationResult = session
                .exportService()
                .task(taskId)
                .state();

	State stateOperationResult = operationResult.getEntity();
```
The body of the response contains the current state of the export operation.

### Fetching the export output
When the export state is ready, you can download the zip file containing the export catalog.
```java
        String state = "inprogres";
        while (!"finished".equals(state)) {
            state = session.exportService().task(taskId).state().getEntity().getPhase();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {

            }
        }

        OperationResult<InputStream> operationResult = session
                .exportService()
                .task(taskId)
                .fetchToFile("export.zip");

        InputStream inputStream = operationResult.getEntity();
```
Please, pay attention, only export with "finished" state can be fetched.

### Cancel the export task
Only `stateDto.getPhase() = "inprogress"` export can be cancelled: 
```java
        OperationResult OperationResult = session
                .exportService()
                .task(taskId)
                .cancel();
```

Import service
--------------
### Strat import
Use the following service to upload a catalog as a zip file and import it with the given options. Specify options as arguments from `com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter`. Arguments that are omitted are assumed to be false. You must be authenticated as the system admin (superuser) or jasperadmin for the import service. Jaspersoft does not recommend uploading files greater than 2 gigabytes.
```java
        OperationResult<State> operationResult = session
                .importService()
                .newImport(pathToFile)
                .parameter(ImportParameter.UPDATE, true)
                .create();


State state = operationResult.getEntity();
```
Available parameters are:
`includeAccessEvents` - access events (date, time, and user name of last modification) are exported (default value is false).
`includeAuditEvents` - include audit data for all resources and users in the export (default value is false).
`update` - resources in the catalog replace those in the repository if their URIs and typesmatch (default value is false).
`skipUserUpdate` - when used with --update, users in the catalog are not imported or updated. Use this option to import catalogs without overwriting currently defined user  (default value is false).
`includeMonitoringEvents` - include monitoring events (default value is false).
`includeServerSettings` - include server settings  (default value is false).
`mergeOrganization` - allows merging of exported organization/resource into organization with different identifier. In the case if it is false, then system will throw an exception, if exportedOrganizationId != organizationId_we_import_Into (default value is false).
`brokenDependencies` - defines strategy with broken dependencies. Available values are:
    	fail - server will give an error (errorCode=import.broken.dependencies) if import archive contain broken dependent resources.
    	skip - import will skip from import broken resources.
	include - import will proceed with broken dependencies. In this case server will try to import broken dependent resources. a) In the case when in target environment there are already dependent resources import of target resource will be success, and resource will be skipped from import if there are no dependent resources to recover dependency chain.
`organization` - organization identifier we import into.

#### Import using mulpipart form
```java
        State state = session
                .importService()
                .newMultiPartImport(new File(pathToFile))
                .parameter(ImportParameter.UPDATE, true)
                .create()
		.getEntity();
```

### Check the Import State
After receiving the import ID, you can check the state of the import operation.
```java
        OperationResult<State> operationResult = session
                .importService()
                .task(taskId)
                .state();

        State stateDto = operationResult.getEntity();
```

### Getting and restarting import task
To get import task metadata you can use next code example:
```java
        ImportTask task = session
                .importService()
                .task(taskId)
                .getTask()
                .getEntity();
```
Also you can restart import task:
```java

        ImportTask task = importService.
                task(state.getId())
                .restartTask(new ImportTask().setBrokenDependencies("false"))
                .getEntity();
```
Notice, only task in phase "pending" can be restarted. Pending will happen if import task failed with error codes: import.organizations.not.match or import.broken.dependencies.

### Check import state
```java
        OperationResult<State> operationResult = session
                .importService()
                .task(taskId)
                .state();

        State state = operationResult.getEntity();
```

### Cancel import task
```java
        OperationResult operationResult = session
                .importService()
                .task(taskId)
                .cancel();
```
Metadata 
========

Domain Metadata
---------------
The domain metadata describes the sets and items exposed by a Domain for use in Ad
Hoc reports. Items are database fields exposed by the Domain, after all joins, filters, and calculated fields have
been applied to the database tables selected in the Domain. Sets are groups of items, arranged by the Domain
creator for use by report creators.

A limitation of the DomainMetadata Service only allows it to operate on Domains with a single data
island. A data island is a group of fields that are all related by joins between the database tables in the
Domain. Fields that belong to tables that are not joined in the Domain belong to separate data islands.

The following code retrieves metadata of Domain.
```java
// create domain context by Id of context
 ClientSemanticLayerDataSource domainContext = new ClientSemanticLayerDataSource().
                                                setUri("/organizations/organization_1/Domains/Simple_Domain");
        OperationResult<ClientSemanticLayerDataSource> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .create(domainContext);
// get uuId of context from "Location" header of response
// get metadata of domain bi uuId of context
        OperationResult<DataIslandsContainer> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .fetchMetadataById(uuId); 
                
// or you can get metadata by context directly

        OperationResult<DataIslandsContainer> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .fetchMetadataByContext(domainContext);
                
        DataIslandsContainer metadata = operationResult.getEntity();
```

Report Metadata 
---------------
Report metadata is used for building AdHoc and query accordingly. 
To get metadata use next code example:
```java
// create domain context by Id of context
ClientReportUnit reportUnit = new ClientReportUnit().setUri("/public/Samples/Domains/supermartDomain");

         OperationResult<ClientReportUnit> operationResult = session
                        .dataDiscoveryService()
                        .topicContext()
                        .create(reportUnit);
// get uuId of context from "Location" header of response
// get metadata of domain bi uuId of context
         OperationResult<ResourceGroupElement> operationResult = session
                        .dataDiscoveryService()
                        .topicContext()
                        .fetchMetadataById(uuId);
         ResourceGroupElement metadata = operationResult.getEntity();
                
// or you can get metadata by context directly

         OperationResult<ResourceGroupElement> operationResult = session
                        .dataDiscoveryService()
                        .topicContext()
                        .fetchMetadataByContext(reportUnit);
                
        ResourceGroupElement metadata = operationResult.getEntity();
```

Thumbnail Search Service
========================
This service is used for requesting a thumbnail image of an existing resource. You can get a single resource. See code below.
```java
InputStream entity = session.thumbnailsService()
        .thumbnail()
        .report("/public/Samples/Reports/08g.UnitSalesDetailReport")
        .defaultAllowed(true)
        .get()
        .getEntity();
```
Or to get multiple resources thumbnails.
```java
List<ResourceThumbnail> entity = session.thumbnailsService()
        .thumbnails()
        .reports(asList("/public/Samples/Reports/08g.UnitSalesDetailReport", 
                        "/public/Samples/Reports/11g.SalesByMonthReport"))
        .defaultAllowed(true)
        .get()
        .getEntity()
        .getThumbnails();
```
By default you obtain multiple resources using POST HTTP method, but you can switch to GET method using `requestMethod(RequestMethod.GET)` method:
```java
List<ResourceThumbnail> entity = session.thumbnailsService()
                .thumbnails()
                .reports(asList("/public/Samples/Reports/08g.UnitSalesDetailReport",
                        "/public/Samples/Reports/11g.SalesByMonthReport"))
                .defaultAllowed(true)
                .requestMethod(RequestMethod.GET)
                .get()
                .getEntity()
                .getThumbnails();
```
Please notice that ResourceThumbnail class (DTO) contains the content in Base64 string format (not InputStream).

Diagnostic Service
==================
The service is used to create, update, stop log collectors and get logs and data snapshots.
To create log collector use the code below:
```java
OperationResult<CollectorSettings> operationResult1 = session
                                                            .diagnosticService()
                                                            .forCollector(collector1)
                                                            .create();

collector1 = operationResult1.getEntity();
```
New collector gets ID and status "RUNNING" automatically.
After creation you can get metadata  of all collectors:
```java
OperationResult<CollectorSettingsList> operationResult = session
                .diagnosticService()
                .allCollectors()
                .collectorsSettings();

CollectorSettingsList result = operationResult
                .getEntity();
```
or for single log collector:
```java
OperationResult<CollectorSettings> operationResult = session
                .diagnosticService()
                .forCollector(collector1.getId())
                .collectorSettings();

CollectorSettings result = operationResult
                .getEntity();
```
You can change collector's metadata or stop it using `.updateCollectorSettings()` method:
```java
PatchDescriptor patchDescriptor = new PatchDescriptor();
List<PatchItem> items = new ArrayList<PatchItem>();
items.add(new PatchItem().setField("status").setValue("STOPPED"));
patchDescriptor.setItems(items);

OperationResult<CollectorSettings> operationResult = session
                .diagnosticService()
                .forCollector(collector1)
                .updateCollectorSettings(patchDescriptor);

CollectorSettings result = operationResult
                .getEntity();
```
Using similar method for batch operation you can update the same field of all collectors:
```java
OperationResult<CollectorSettingsList> operationResult = session
                .diagnosticService()
                .allCollectors()
                .updateCollectorsSettings(patchDescriptor);
CollectorSettingsList result = operationResult
                .getEntity();
```
Also you can update whole collector:
```java
collector1.setStatus("STOPPED");

OperationResult<CollectorSettings> operationResult = session
                .diagnosticService()
                .forCollector(collector1)
                .updateCollectorSettings(collector1);

CollectorSettings result = operationResult
                .getEntity();

```
To get collectors' content use code below:
```java
OperationResult<InputStream> operationResult = session
                .diagnosticService()
                .allCollectors()
                .collectorsContent();
                
         // or for single collector
          
OperationResult<InputStream> operationResult = session
                          .diagnosticService()
                          .forCollector(collector1)
                          .collectorContent();
          
InputStream result = operationResult
                          .getEntity();
````
Please notice, you should stop them previously using `.updateCollectorSettings()` methods. 
Stopping the collector will turn off logging and begin resource export (if "includeDataSnapshots" is `true` and resourceUri not empty).
Once stopped, collectors can't be run again.
When App Server (e.g. Tomcat) is restarted, all collectors must change to stopped state.
Before getting collectors' content check them status with method `.collectorsSettings()`.

Delete log collectors you can as single or as batch operation:
```java
OperationResult<CollectorSettings> operationResult = session
                .diagnosticService()
                .forCollector(collector1)
                .delete();
                
OperationResult<CollectorSettingsList> operationResult = session
                                .diagnosticService()
                                .allCollectors()
                                .delete();
```

Contexts Service
================
Using the service you are able to keep some state or context on the server and execute some actions on this state. General service have next API:
- save context: 
```java
        OperationResult<ContextClass> operationResult = session
                .contextService()
                .context(contextClass, contextMimeType)
                .create(context);
```
Response header `"Content-Location"`contains contextUuid.

- get context:
```java
        OperationResult<ContextClass> operationResult = session
                .contextService()
                .context(contextClass, contextMimeType, uuId)
                .get(context);
```

- modify context:
```java
        OperationResult<ContextClass> operationResult = session
                .contextService()
                .context(contextClass, contextMimeType, uuId)
                .update(context);
```
- delete context:
```java
        OperationResult<ContextClass> operationResult = session
                .contextService()
                .context(uuId)
                .delete();
```
- get context metadata:
```java
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(uuId, contextMatadataClass, contextMetadataMimeType)
                .metadata();

// get context metadata with paramerets
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(uuId, contextMatadataClass, contextMetadataMimeType)
                .addParameter("expand", "public.accounts")
                .addParameter("", "")
                .metadata();
        
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(uuId, contextMatadataClass, contextMetadataMimeType)
                .addParameter("include", "public.currency")
                .metadata();
        
        // or
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(uuId, contextMatadataClass, contextMetadataMimeType)
                .addParameter("expands", "public.account")
                .addParameter("expands", "public.category")
                .partialMetadata();
        
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(uuId, contextMatadataClass, contextMetadataMimeType)
                .addParameter("includes", "public.currency")
                .partialMetadata();
```
The difference between `.metadata()` and `.partialMetadata()` is in HTTP methods and a way to pass parameters:
`.metadata()` - sends GET request and parameters as URL query parameters;
`.partialMetadata()` - sends POST request and parameters as JSON;

- create context and get context metadata:
```java
        OperationResult<ContextMetadataClass> operationResult = session
                .contextService()
                .context(contextClass, contextMimeType,contextMatadataClass, contextMetadataMimeType)
                .createAndGetMetadata(context);
```

Domain Context Service
----------------------
This service allows to execute query of in-memory domain. I.e. domain, that doesn't exist in repository.
- to create context: 
```java
       OperationResult<ClientResourceLookup> operationResult = session
                .domainContextService()
                .context(context)
                .create();
``` 
`context` here might be instance of ClientDomain or ClientSemanticLayerDataSource with data source URI and domain schema URI of datasource.
- to get domain metadata:
```java

        OperationResult<PresentationGroupElement> operationResult = session
                .domainContextService()
                .context(context)
                .create().getMetadata();
    // or expznded metadata            
        OperationResult<PresentationGroupElement> operationResult = session
                .domainContextService()
                .context(context)
                .create()
                .addParam("expand", nodeName)
                .getMetadata();
```   
- to execute query (only multi level queries are supported):
```java
       ClientMultiLevelQuery clientMultiLevelQuery = new ClientMultiLevelQuery().setGroupBy(
                new ClientQueryGroupBy().setGroups(
                        asList(new ClientQueryGroup().
                                setId("account_type").
                                setFieldName("account_type"))));
        OperationResult<ClientMultiLevelQueryResultData> operationResult = session
                .domainContextService()
                .context(clientDomain)
                .create().executeQuery(clientMultiLevelQuery);
```

Data Discovery Service
======================
The service based on Context Service and allows to work with supported contexts(Domain, DomEl, reports etc) directly.

Domain data discovery 
---------------------
This API allows to execute query of in-memory domain. I.e. domain, that doesn't exist in repository.

```java
   private String uuId; // available in Content-Location header in responce
   private ClientDomain domainContext = new ClientDomain().setUri("/public/Samples/Domains/supermartDomain");

// create context
        OperationResult<ClientDomain> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .create(domainContext);
// get context metadata
        OperationResult<PresentationGroupElement> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .fetchMetadataById(uuId);
// create context and get metadata
        OperationResult<PresentationGroupElement> operationResult = session
                .dataDiscoveryService()
                .domainContext()
                .fetchMetadataByContext(domainContext);
```

DomEl data discovery
--------------------
This API allows to validate DomEL expressions and convert them from string representation to object representation and back.

```java
   private DomElExpressionContext expressionStringContext;
   private DomElExpressionContext expressionObjectContext;

        expressionObjectContext = new DomElExpressionContext().
                setExpression(new ClientExpressionContainer().setObject(new ClientFunction().
                        setFunctionName("concat").addOperand(new ClientVariable("test")).addOperand(new ClientVariable("test1")).addOperand(new ClientVariable("b")))).
                setVariables(Arrays.asList(new DomElVariable().setName("test").setType("java.lang.String"),
                        new DomElVariable().setName("test1").setType("java.lang.String"))).
                setResultType("java.lang.String");

        expressionStringContext= new DomElExpressionContext().
                setExpression(new ClientExpressionContainer().setString("concat(test, test1,'b')")).
                setVariables(Arrays.asList(new DomElVariable().setName("test").setType("java.lang.String"),
                        new DomElVariable().setName("test1").setType("java.lang.String"))).
                setResultType("java.lang.String");

// convert sring expression to object representation
        OperationResult<DomElExpressionContext> operationResult = session
                .dataDiscoveryService()
                .domElContext()
                .create(expressionStringContext);
// convert object representation o fexpression to stirng view
        OperationResult<DomElExpressionContext> operationResult = session
                .dataDiscoveryService()
                .domElContext()
                .create(expressionStringContext);
```

Derived table data discovery
----------------------------
This API allows to validate DomEL expressions and convert them from string representation to object representation and back.

```java
  private SqlExecutionRequest sqlExecutionRequest;
        sqlExecutionRequest = new SqlExecutionRequest().
                setSql("select * from account").
                setDataSourceUri("/public/Samples/Data_Sources/FoodmartDataSource");

     OperationResult<PresentationGroupElement> operationResult = session
                .dataDiscoveryService()
                .derivedTableContext()
                .execute(sqlExecutionRequest);


```

Query Execution Service
=======================
In addition to running reports, JasperReports Server exposes queries that you can run through the QueryExecution service.
For now the only resource that supports queries is an AdHoc data view. In present time JasperReportsServer supports only synchronize query execution.

The following code examples execute query and retrieve a result data for different queries:
- for flat query:
```java
 OperationResult<ClientFlatQueryResultData> execute = session.
                                queryExecutionService().
                                flatQuery().
                                execute(queryExecution);
```
- for multi level query:
```java
        OperationResult<ClientMultiLevelQueryResultData> execute = session.
                queryExecutionService().
                multiLevelQuery().
                execute(queryExecution);
```
Result data set of multi level query can be obtained as MultiLevelResultData(by default) or as FlatQueryResultData:
```java
        OperationResult<ClientMultiLevelQueryResultData> execute = session.
                queryExecutionService().
                multiLevelQuery().
                asResultDataSet(QueryResultDataMediaType.FLAT_DATA_JSON).
                execute(queryExecution);
```
- for multi axes query:
```java
        OperationResult execute = session.
                queryExecutionService().
                multiAxesQuery().
                execute(queryExecution);
```
- for provided query:
```java
        OperationResult<? extends ClientQueryResultData> execute = session.
                queryExecutionService().
                providedQuery().
                execute(queryExecution);
```
Please notice, that the client resolve type of result dataset according to "Content-Type" header of server's response. 

Also you can get fragment of result data:
```java
        OperationResult<? extends ClientQueryResultData> execute = session.
                queryExecutionService().
                providedQuery().
                offset(0).
                pageSize(100).
                retrieveData(uuId);
     // where uuId   - is Id of query execution, that you can obtain from "Content-Location" header of server's response after execution query.
```

To see all submitted query executions you can use:
```java
        OperationResult<QueryExecutionsListWrapper> result = seesion
                        .queryExecutionService()
                        .get()
```

To see just one specific query execution you should use:

```java
        OperationResult<? extends AbstractClientExecution> result = seesion
                        .queryExecutionService()
                        .execution(uuid) // where uuId - is Id of query execution
                        .get()
``` 

Also, you can see the status of any query execution using the following piece of code:
```java
     OperationResult<? extends AbstractClientExecution> result = seesion
                             .queryExecutionService()
                             .execution(uuid) // where uuId - is Id of query execution
                             .status()   
```

And you can delete execution using the following code:
```java
        OperationResult execute = session.
                queryExecutionService().
                execution(uuId).
                delete();
```

Server Information Service
==========================
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

Bundles service
===============
Use bundles service to get bundles of internalization properties for particular or default user’s locale as JSON. 
By default service use default system locale where the application was stared.
If user specified locale at authentication, the service will use it as default locale.
To get all bundles for particular locale(foe example, "de") different from default locale and locale specified at authentication use the code below:
```java
final Map<String, Map<String, String>> bundles = session
        .bundlesService()
        .forLocale("de")
        .allBundles()
        .getEntity();
```
or specify locale as instance of `java.util.Locale.class` or as constant of this class:
```java
final Map<String, Map<String, String>> bundles = session
        .bundlesService()
        .forLocale(Locale.US)
        .allBundles()
        .getEntity();
        
final Map<String, Map<String, String>> bundles = session
                .bundlesService()
                .forLocale(new Locale("en_US"))
                .allBundles()
                .getEntity();
```
Please notice, locale specified in `.forLocale()` method has the highest priority for the service.
If you do not call`.forLocale()` method, you will get bundles for your default locale (locale specified at authentication or locale of system where the client was stared if the others were not specified):
```java
final Map<String, Map<String, String>> bundles = session
        .bundlesService()
        .allBundles()
        .getEntity();
```
To get bundle by name you should specified locale in `.forLocale()` method and name of the bundle in `.bundles()` method:
```java
final Map<String, String> bundle = session
        .bundlesService()
        .forLocale("en_US")
        .bundle("jasperserver_messages")
        .getEntity();
```

Asynchronous API
================
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

Getting serialized content from response
========================================
If you need to get a plain response body, either JSON, XML, HTML or plain text, you gen get it it with code below:
```java
OperationResult<UsersListWrapper> result = ...
result.getSerializedContent();
```

Possible issues
===============
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

Maven dependency to add jasperserver-rest-client to your app:
=============================================================
```xml
 <dependencies>
        <dependency>
            <groupId>com.jaspersoft</groupId>
            <artifactId>jrs-rest-java-client</artifactId>
            <version>${current.version}</version>
        </dependency>
    </dependencies>

    <repositories>
        
        <repository>
            <id>jaspersoft-clients-snapshots</id>
            <name>Jaspersoft clients snapshots</name>
            <url>https://jaspersoft.jfrog.io/jaspersoft/jaspersoft-clients-releases</url>
        </repository>

    </repositories>
```

License
=======
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
