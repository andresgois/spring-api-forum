package br.com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.forum.config.security.TokenService;
import br.com.forum.controller.DTO.TokenDTO;
import br.com.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> autentica(
			@RequestBody @Valid LoginForm form
	){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
			
		//System.out.println("User"+ form.getEmail());
		//System.out.println("User"+ form.getSenha());
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().build();
		}
		
		
	}

}
