package de.tuebingen.uni.sfs.lapps.lifconverter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.InputStream;

/**
 * Resource that serves up the index page.
 */
@Path("/")
public class IndexResource {

    @GET
    @Produces("text/html")
    public InputStream index() {
        return getClass().getResourceAsStream("/index.html");
    }

    /*@GET
    @Path("/lif-multipleLayer.json")
    @Produces("application/json")
    public InputStream inputTestMultiData() {
        return getClass().getResourceAsStream("/lif-multipleLayer.json");
    }

    @GET
    @Path("/lif-textLayer.json")
    @Produces("application/json")
    public InputStream inputTestTextData() {
        return getClass().getResourceAsStream("/lif-textLayer.json");
    }

    @GET
    @Path("/lif-tokenLayer.json")
    @Produces("application/json")
    public InputStream inputTestTokData() {
        return getClass().getResourceAsStream("/lif-tokenLayer.json");
    }

    @GET
    @Path("/lif-posLayer.json")
    @Produces("application/json")
    public InputStream inputTestPosData() {
        return getClass().getResourceAsStream("/lif-posLayer.json");
    }

    @GET
    @Path("/lif-nameEntittyLayer.json")
    @Produces("application/json")
    public InputStream inputTestNerData() {
        return getClass().getResourceAsStream("/lif-nameEntittyLayer.json");
    }

    @GET
    @Path("/lif-constituentLayer.json")
    @Produces("application/json")
    public InputStream inputTestConData() {
        return getClass().getResourceAsStream("/lif-constituentLayer.json");
    }

    @GET
    @Path("/lif-dependencyLayer.json")
    @Produces("application/json")
    public InputStream inputTestDepData() {
        return getClass().getResourceAsStream("/lif-dependencyLayer.json");
    }
    
    @GET
    @Path("/lif-corferenceLayer.json")
    @Produces("application/json")
    public InputStream inputTestCorferenceData() {
        return getClass().getResourceAsStream("/lif-corferenceLayer.json");
    }*/
    
    @GET
    @Path("/karen-all.json")
    @Produces("application/json")
    public InputStream inputTestAllData() {
        return getClass().getResourceAsStream("/karen-all.json");
    }

}
