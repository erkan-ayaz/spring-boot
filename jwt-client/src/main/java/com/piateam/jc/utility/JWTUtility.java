package com.piateam.jc.utility;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.piateam.jc.bean.UserDetailsBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtility {
	private static final Logger	logger	= LoggerFactory.getLogger(JWTUtility.class);
	@Value("${pia.secret}")
	private String				secret;
	@Value("${pia.validityDuration}")
	private Long				validityDuration;

	public String generateToken(Authentication authentication) {
		UserDetailsBean userDetailsBean = (UserDetailsBean) authentication.getPrincipal();
		Date issuedDate = new Date();
		return Jwts.builder()//
				.setSubject(userDetailsBean.getUsername())//
				.setIssuedAt(issuedDate)//
				.setExpiration(new Date(issuedDate.getTime() + validityDuration))//
				.signWith(SignatureAlgorithm.HS512, secret)//
				.compact();
	}

	public String username(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validate(String token) {
		boolean result;
		try {
			Jwts.parser().setSigningKey(secret).parse(token);
			result = true;
		} catch (Throwable throwable) {
			logger.error("[validate()] invalid [Token: {}]!!! [NOK]", token, throwable);
			result = false;
		}
		return result;
	}
}
