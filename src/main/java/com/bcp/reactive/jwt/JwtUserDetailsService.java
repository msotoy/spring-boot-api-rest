package com.bcp.reactive.jwt;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("msotoy".equals(username)) {
			return new User("msotoy", "$2a$10$Kxv4bqvGTld3OZWuSoT4bOpdqNDkPmH6l0PuIABUd4m4qUpvd7Tse",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}