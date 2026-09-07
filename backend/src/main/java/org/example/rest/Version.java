package org.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
public class Version {

    @GET
    public Response getVersion() {
        return Response.ok("{\"message\": \"4.0.1\"}").build();
    }
}
