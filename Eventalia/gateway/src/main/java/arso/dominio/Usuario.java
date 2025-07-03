package arso.dominio;

import java.util.ArrayList;
import java.util.UUID;

public class Usuario {
	
	private UUID id;
	private String username;
	private String password;
	private ArrayList<String> roles;
	
	public Usuario(UUID id, String username, String password, String... roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = new ArrayList<String>();
		for (String rol : roles) {
			this.roles.add(rol);
		}
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<String> getRoles() {
		return roles;
	}
	
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
	public void addRol(String rol) {
		this.roles.add(rol);
	}
	
	public void addRoles(String... roles) {
		for (String rol : roles) {
			this.roles.add(rol);
		}
	}
	
	public void removeRol(String rol) {
		this.roles.remove(rol);
	}
	
	public boolean hasRol(String rol) {
		return this.roles.contains(rol);
	}
	
	

}
