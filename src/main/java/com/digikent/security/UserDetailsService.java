package com.digikent.security;

import com.digikent.security.keycloakToken.KeycloakUserToken;
import com.digikent.security.keycloakToken.Services;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.JWSInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.*;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Override
	//keycloak serverdan gelen jwt tokennın içerisinde userın role bilgileri var.
	//gelen jwt ile Keycloak userı spring security usera ceviriyoruz
    public UserDetails loadUserByUsername(final String authToken) {
		log.debug("Authenticating {}", authToken);
		//start: jwt convert to pojo
		KeycloakUserToken keycloakUserToken = null;
		try {
			keycloakUserToken = convertJWT2Pojo(authToken);

		} catch (Exception e) {
			throw new RuntimeException();
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Services services: keycloakUserToken.getResourceAccess().getCommmandClasses()){
			for(String role: services.getRoles()){
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return new org.springframework.security.core.userdetails.User(
				keycloakUserToken.getPreferredUsername(),
				keycloakUserToken.getNonce(), grantedAuthorities);
    }

	private KeycloakUserToken convertJWT2Pojo(String authToken) throws IOException, JWSInputException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JWSInput jwsInput = new JWSInput(authToken);

		return mapper.readValue(jwsInput.readContentAsString(), KeycloakUserToken.class);
	}

}
