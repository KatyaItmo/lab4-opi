package org.example.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.service.AuthService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
	
	@Inject
	AuthService authService;
	
	@POST
	@Path("/register")
	public Response register(UserDTO dto) {
		if (authService.register(dto.getLogin(), dto.getPassword())) {
			return Response.ok("{\"message\": \"Успешная регистрация\"}").build();
		}
		return Response.status(Response.Status.CONFLICT).entity("{\"message\": \"Логин занят\"}").build();
	}
	
	@POST
	@Path("/login")
	public Response login(UserDTO dto) {
		User user = authService.login(dto.getLogin(), dto.getPassword());
		
		if (user != null) {
			String token = authService.generateToken(user.getLogin());
			
			NewCookie cookie = new NewCookie("auth_token", token, "/", null, null, 86400, false);
			
			return Response.ok("{\"message\": \"Вход выполнен\"}").cookie(cookie).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED)
				.entity("{\"message\": \"Неверный логин или пароль\"}").build();
	}
	
	@POST
	@Path("/logout")
	public Response logout() {
		NewCookie cookie = new NewCookie("auth_token", "", "/", null, null, 0, false);
		
		return Response.ok("{\"message\": \"Выход из аккаунта\"}").cookie(cookie).build();
	}
}
