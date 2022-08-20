package com.jwt.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username){
		
		if(username.equals("Divyanshu")) 
			return new User("Divyanshu", "Password", new ArrayList<>());
		else {
			throw new UsernameNotFoundException("User not found");
		}
			
			
	}
}
