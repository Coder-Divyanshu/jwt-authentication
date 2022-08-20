package com.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.service.CustomUserDetailService;
import com.jwt.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = null;
		String userName=null;
		
		requestToken=request.getHeader("Authorization");
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {  
			String token =requestToken.substring(7);
			try { 
				userName = jwtUtil.extractUsername(token);
			}catch(Exception e) {
				e.printStackTrace();
		}
		UserDetails userDetails =  customUserDetailService.loadUserByUsername(userName);
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}else {
			System.out.println("Invalid");
		}
		}
		filterChain.doFilter(request, response);
	}

}
