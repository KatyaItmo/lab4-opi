package org.example.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.dto.PointDTO;
import org.example.dto.PointResponseDTO;
import org.example.service.AuthService;
import org.example.service.PointService;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {
	
	@Inject
    private PointService pointService;
    @Inject
    private AuthService authService;
    
    @POST
    public Response addPoints(@CookieParam("auth_token") String token, List<PointDTO> dtos) {
    	String login = authService.getUsernameFromToken(token);
        if (login == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        
        List<PointResponseDTO> newPoints = pointService.processPoints(dtos, login);
        
        return Response.ok(newPoints).build();
    }
    
    @GET
    public Response getHistoryPage(
    		@CookieParam("auth_token") String token,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
    	
    	String login = authService.getUsernameFromToken(token);
        if (login == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        
        List<PointResponseDTO> history = pointService.getHistory(login, page, size);
        return Response.ok(history).build();
    }
    
    @GET
    @Path("/all")
    public Response getAllPoints(@CookieParam("auth_token") String token) {
    	String login = authService.getUsernameFromToken(token);
        if (login == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        
        List<PointResponseDTO> allPoints = pointService.getAllUserPoints(login);
        return Response.ok(allPoints).build();
    }
    
    @DELETE
    public Response clearHistory(@CookieParam("auth_token") String token) {
    	String login = authService.getUsernameFromToken(token);
        if (login == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        
        pointService.clearHistory(login);
        return Response.ok("{\"message\": \"История очищена\"}").build();
    }
}
