package org.example.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.model.User;

@Stateless
public class AuthService {
	private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	@PersistenceContext(unitName = "Lab4Unit")
    private EntityManager em;
	
	public String generateToken(String login) {
	    return Jwts.builder()
	            .setSubject(login)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
	            .signWith(KEY)
	            .compact();
	}
	
	public String getUsernameFromToken(String token) {
		try {
			return Jwts.parserBuilder()
	                .setSigningKey(KEY)
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
		} catch (Exception ex) {
			System.err.println("Не удалось расшифровать токен: " + ex.getMessage());
			return null;
		}
	}
	
	private String hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Ошибка хэширования пароля: " + ex.getMessage());
		}
	}
	
	public boolean register(String login, String password) {
		Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
				.setParameter("login", login)
				.getSingleResult();
		
		if (count > 0) {
			return false;
		}
		
		User user = new User(login, hash(password));
		em.persist(user);
		return true;
	}
	
	public User login(String login, String password) {
		try {
			User user = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
					.setParameter("login", login)
					.getSingleResult();
			
			if (user.getPassword().equals(hash(password))) {
				return user;
			}
			
			return null;
		} catch (Exception ex) {
			System.err.println("Ошибка авторизации пользователя: " + ex.getMessage());
			return null;
		}
	}
}
