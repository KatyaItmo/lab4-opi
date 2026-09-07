package org.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "points")
public class Point implements Serializable{
	private static final long serialVersionUID = 5253899308311675204L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String x;
	
	@Column(nullable = false)
	private String y;
	
	@Column(nullable = false)
	private String r;
	
	@Column(nullable = false)
	private Boolean hit;
	
	@Column(name = "check_time")
	private LocalDateTime checkTime;
	
	@Column(name = "exec_time")
	private Long executionTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public Point() {};
	
	public Point(String x, String y, String r, Boolean hit, User user) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.hit = hit;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public Boolean getHit() {
		return hit;
	}

	public void setHit(Boolean hit) {
		this.hit = hit;
	}

	public LocalDateTime getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(LocalDateTime checkTime) {
		this.checkTime = checkTime;
	}

	public Long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
