weblicht-nentities-ws-archetype

Created using WebLicht converter Webservice Dropwizard Archetype.

What is it?
===========

This is a converter developed as web service on weblicht-nentities-ws-archetype Dropwizard  archetype. The converter converts LIF (LAPPS Grid interchange format)file to TCF(WebLicht interchange format).
It can be deployed using Maven 2.0.10 or greater with Java 6.0 or greater.
This demo application exposes the converter service.

Lif file to Tcf file converter:
* /con/bytes
* /con/stream


LIF to TCF converter
==========
This web-service converts LIF to TCF. The service
processes POST requests containing LIF data and produces TCF data.

It imitates a tool that requires loading a parameter list for converting LIF to TCF vocabulries. In this web-service example the tool instance is created only
once (the corresponding list resource is loaded only once), when the application
is created. The example shows the case when the tool is not thread-safe.
Therefore, the tool's process() method requires synchronization.

* ```DemoApplication.java``` - is the application definition.
* ```FormatConverterResource.java``` - is the definition of a
resource. 
* ```FormatConverterTool.java``` - is the place where an actual
implementation of a tool resides. 

How To Run And Test
=============
Make a runnable jar
```
mvn clean package
```

Run the application
```
java -jar <generated jar> server
```

Once the application is started, it can be accessed using the following URL:

```
http://localhost:8080/
```
Following the instructions on the homepage, The home page contain an example how to test the service. It also contains more examples to test.



