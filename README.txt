LIF to TCF converter
===========

This is a converter developed as web service. The converter converts LIF (LAPPS Grid interchange format)file to TCF(WebLicht interchange format).
This demo application exposes the converter service. This web-service converts LIF to TCF. The service
processes POST requests containing LIF data and produces TCF data.

It imitates a tool that requires loading a parameter list for converting LIF to TCF vocabulries. In this web-service example the tool instance is created only
once (the corresponding list resource is loaded only once), when the application
is created. 

Dependencies
=============
The converter is dependent on lapps-lif-library. 
The library extracts annotation layer from a lif 

git clone the lapps-lif-library (https://github.com/lapps-clarin/lapps-lif-library.git)
mvn clean package

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
Following the instructions on the homepage, The home page contain an example how to test the service. 


