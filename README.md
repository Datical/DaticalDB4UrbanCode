DaticalDB4UDeploy
=================

This plugin brings Datical DB functionality to IBM UrbanCode uDeploy.

Two properties in the plugin step, Datical DB Install Directory and Datical DB Drivers Directory, read a default property at the resource (Agent) level. For each agent that is going to execute Datical DB, create a Resource Property called daticalDBCmd and daticalDBDriversDir.  

Release Notes:

### Version 1.169 - November 18, 2014

To support customers that wish to dynamically choose the JVM when Datical DB is executed, the command line now supports two new arguments:

 - --vm Path to a JDK install
 - --vmargs JVM arguments

The JVM arguments must be the LAST thing on the command line.

Examples:
> $ hammer show version --vm /usr/lib/jvm/java-7-openjdk-amd64 --vmargs -Xmx1024M

> C:\Users\wesley\product\repl>.\hammer.bat show version --vm "C:\Program Files\Java\jdk1.7.0_17" --vmargs -Dmx1024M

Thus, we've added new new text boxes to each Datical DB Step, Datical DB JVM and Datical DB JVM Arguments. Neither are required.

There is a known issue with placing multiple arguments in the "Datical DB JVM Arguments" text box. IBM Rational UrbanCode Deploy passes all properties as quoted strings. Thus, *--vmargs -Xms512M -Dmx512M* becomes *--vmargs "-Xms512M -Dmx512M"*. We are working with IBM on a resolution to this issue.

