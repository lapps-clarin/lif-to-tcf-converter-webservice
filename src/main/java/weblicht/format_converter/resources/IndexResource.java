package weblicht.format_converter.resources;

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

   /* @GET
    @Path("/input1_ner.xml")
    @Produces("text/xml")
    public InputStream inputTestNerData() {
        return getClass().getResourceAsStream("/input1_ner.xml");
    }*/

    @GET
    @Path("/inputMulti_con.lif")
    @Produces("text/lif")
    public InputStream inputTestMultiData() {
        return getClass().getResourceAsStream("/inputMulti_con.lif");
    }
    @GET
    @Path("/inputTok_con.lif")
    @Produces("text/lif")
    public InputStream inputTestTokData() {
        return getClass().getResourceAsStream("/inputTok_con.lif");
    }
    
    @GET
    @Path("/inputPos_con.lif")
    @Produces("text/lif")
    public InputStream inputTestPosData() {
        return getClass().getResourceAsStream("/inputPos_con.lif");
    }
    
    @GET
    @Path("/inputNer_con.lif")
    @Produces("text/lif")
    public InputStream inputTestNerData() {
        return getClass().getResourceAsStream("/inputNer_con.lif");
    }
    
    @GET
    @Path("/inputCon_con.lif")
    @Produces("text/lif")
    public InputStream inputTestConData() {
        return getClass().getResourceAsStream("/inputCon_con.lif");
    }
    
     @GET
    @Path("/inputDep_con.lif")
    @Produces("text/lif")
    public InputStream inputTestDepData() {
        return getClass().getResourceAsStream("/inputDep_con.lif");
    }
    
}
