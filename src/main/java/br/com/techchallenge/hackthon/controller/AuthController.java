package br.com.techchallenge.hackthon.controller;

import br.com.techchallenge.hackthon.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techchallenge.hackthon.service.AuthService;

@RestController
@RequestMapping("/api/autenticacao")
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping()
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto){
		return ResponseEntity.ok(authService.login(authDto));
	}
	
}
