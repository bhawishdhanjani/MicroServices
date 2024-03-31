package com.lcwd.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.gateway.models.AuthResponse;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@RequestMapping("/login")
	public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client , 
												@AuthenticationPrincipal OidcUser user,
												Model model){
		AuthResponse authResponse = new AuthResponse();
		authResponse.setUserId(user.getEmail());
		authResponse.setAccessToken(client.getAccessToken().getTokenValue());
		authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
		authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
		List<String> authorities =  user.getAuthorities().stream().map(grantedAuthority-> {
			return grantedAuthority.getAuthority();
		}).collect(Collectors.toList());
		authResponse.setAuthorities(authorities);
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
		
	}


}
