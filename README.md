## LIF to TCF converter


This is a RESTful web service that converts LIF (LAPPS Grid interchange format)file to TCF(WebLicht interchange format).
The service processes POST requests containing LIF data and produces TCF data.

In this web-service example the tool instance is created only
once (the corresponding list resource is loaded only once), when the application
is created. 

## Dependencies

The converter is dependent on library lif-to-tcf-converter-core. 
The library extracts annotation layer from a LIF from flat JSON and then converts it to TCF. 

## Installing

git clone the [lif-to-tcf-converter-core](https://github.com/lapps-clarin/lif-to-tcf-converter-core)

git clone the [lif-to-tcf-converter-webservice](https://github.com/lapps-clarin/lif-to-tcf-converter-webservice.git)

## How To Run And Test

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


