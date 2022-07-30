package br.com.forum.controller.DTO;

public class TokenDTO {

	private String token;
	private String string;

	public TokenDTO(String token, String string) {
		this.token = token;
		this.string = string;
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public String getString() {
		return string;
	}

	
}
