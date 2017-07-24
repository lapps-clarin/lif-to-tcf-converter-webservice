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

    @GET
    @Path("/inputMulti.json")
    @Produces("application/json")
    public InputStream inputTestMultiData() {
        return getClass().getResourceAsStream("/inputMulti.json");
    }
    @GET
    @Path("/inputTok.json")
    @Produces("application/json")
    public InputStream inputTestTokData() {
        return getClass().getResourceAsStream("/inputTok.json");
    }
    
    @GET
    @Path("/inputPos.json")
    @Produces("application/json")
    public InputStream inputTestPosData() {
        return getClass().getResourceAsStream("/inputPos.json");
    }
    
    @GET
    @Path("/inputNer.json")
    @Produces("application/json")
    public InputStream inputTestNerData() {
        return getClass().getResourceAsStream("/inputNer.json");
    }
    
    @GET
    @Path("/inputCon.json")
    @Produces("application/json")
    public InputStream inputTestConData() {
        return getClass().getResourceAsStream("/inputCon.json");
    }
    
     @GET
    @Path("/inputDep.json")
    @Produces("application/json")
    public InputStream inputTestDepData() {
        return getClass().getResourceAsStream("/inputDep.json");
    }
    
}
