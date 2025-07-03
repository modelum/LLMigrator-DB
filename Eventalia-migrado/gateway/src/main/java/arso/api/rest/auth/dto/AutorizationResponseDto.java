package arso.api.rest.auth.dto;

import java.util.ArrayList;
import java.util.UUID;

public class AutorizationResponseDto {
	
	private UUID id;
	private String username;
	private ArrayList<String> roles;
	private String token;
	
	public AutorizationResponseDto(UUID id, String username, ArrayList<String> roles, String token) {
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.token = token;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ArrayList<String> getRoles() {
		return roles;
	}
	
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	


}
