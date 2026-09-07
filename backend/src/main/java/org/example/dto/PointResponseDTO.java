package org.example.dto;

public class PointResponseDTO {
	private String x;
	private String y;
	private String r;
	private Boolean hit;
	private String checkTime;
	private Long executionTime;
	
	public PointResponseDTO(String x, String y, String r,
			Boolean hit, String checkTime, Long executionTime) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.hit = hit;
		this.checkTime = checkTime;
		this.executionTime = executionTime;
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
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public Long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}
}
