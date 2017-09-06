package br.com.cpqd.auth;

import java.io.Serializable;

public class AuthorizationEntity implements Serializable {

	private static final long serialVersionUID = -3842844978617110554L;

	private String action;
	private String resource;
	private String accessSubject;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getAccessSubject() {
		return accessSubject;
	}
	public void setAccessSubject(String accessSubject) {
		this.accessSubject = accessSubject;
	}
}