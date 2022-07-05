package org.zzz.jt.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zzz.jt.security.CustomException;
import org.zzz.jt.security.JwtTokenUtil;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenProvider;
	/*
	 * public List<User> getAll() { return userRepository.getAll(); }
	 */

	public User getByLogin(String login) {
		return userRepository.getByName(login).get();
	}

	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			//return jwtTokenProvider.createToken(username, userRepository.getByName(username).get().getAuthorities());
			return jwtTokenProvider.createToken(username, null);
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
