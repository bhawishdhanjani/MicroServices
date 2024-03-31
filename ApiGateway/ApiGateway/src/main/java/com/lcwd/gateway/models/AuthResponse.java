package com.lcwd.gateway.models;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	public String userId;
	public String  accessToken;
	public String  refreshToken;
	public long expireAt;
	private Collection<String> authorities;
	

}
