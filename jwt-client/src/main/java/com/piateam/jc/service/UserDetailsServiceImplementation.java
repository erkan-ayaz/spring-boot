package com.piateam.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piateam.jc.bean.User;
import com.piateam.jc.bean.UserDetailsBean;
import com.piateam.jc.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("There is no user with [Username: " + username + "]!"));
		return UserDetailsBean.build(user);
	}
}
