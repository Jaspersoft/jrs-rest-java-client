5.5.0.2-ALPHA-SNAPSHOT
----------------------
###Features
* Automatical password encryption (if encryption is on)
* Added ability to get report in HTML format with all its attachments in one call.
* Added builder for `ReportExecutionRequest`.
* Added utils to work with input controls.

###Bug fixes
* Java 6 compatibility

###Breaking changes
* You shouldn't specify an encrypted password now, password is being encrypted on client side automatically

5.5.0.1-ALPHA
----------------------

###Features
* Customizable exception handling system
* Ability to get an `ErrorDescriptor` from exception instance
* Multitenancy support
* Ability to configure to work over HTTPS
* Ability to switch between JSON and XML
* Asynchronous API
* Logout functionality

###Bug fixes
* Input controls values are not applied while scheduling the report
* Job validation exception's stack trace is unclear
 
###Breaking changes
* Removed `JobExtension` class, now you should use only `Job` for scheduling
* Calendar service doesn't return `ReportJobCalendar` instance anymore, now it returns one of the implementations of `com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar`
