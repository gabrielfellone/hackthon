package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.dto.AcessDTO;
import br.com.techchallenge.hackthon.dto.AuthenticationDTO;
import br.com.techchallenge.hackthon.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticatioManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public AcessDTO login(AuthenticationDTO authDto) {
		
		try {
		UsernamePasswordAuthenticationToken userAuth =
				new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
		Authentication authentication = authenticatioManager.authenticate(userAuth);
		UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

		return new AcessDTO(token);
		
		}catch(BadCredentialsException ignored) {
		}
		return new AcessDTO("Acesso negado");
	}
}
