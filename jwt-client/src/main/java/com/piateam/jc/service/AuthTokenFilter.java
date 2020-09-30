package com.piateam.jc.service;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.piateam.jc.utility.JWTUtility;

public class AuthTokenFilter extends OncePerRequestFilter {
	private static final Logger					logger	= LoggerFactory.getLogger(AuthTokenFilter.class);
	@Autowired
	private JWTUtility							jwtUtility;
	@Autowired
	private UserDetailsServiceImplementation	userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = parse(request);
			if (Objects.nonNull(token)) {
				if (jwtUtility.validate(token)) {
					String username = jwtUtility.username(token);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Throwable throwable) {
			logger.error("[doFilterInternal()] Cannot authenticate!!! [NOK]", throwable);
		}
		filterChain.doFilter(request, response);
	}

	private String parse(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token;
		if (StringUtils.hasText(authorizationHeader)) {
			if (authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
			} else {
				token = null;
			}
		} else {
			token = null;
		}
		return token;
	}
}
