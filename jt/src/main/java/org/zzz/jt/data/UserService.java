package org.zzz.jt.data;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zzz.jt.repository.EmailReposotory;
import org.zzz.jt.repository.UserRepository;
import org.zzz.jt.security.JwtTokenUtil;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailReposotory emailRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenProvider;
	

	public User getByLogin(String login) {
		return userRepository.getByName(login).get();
	}

	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			//return jwtTokenProvider.createToken(username, userRepository.getByName(username).get().getAuthorities());
			return jwtTokenProvider.createToken(username, null);
		} catch (AuthenticationException e) {
			//throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public User createEmail(String email) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String name = auth.getName();
		
		User user = userRepository.getByName(name).get();
		
		boolean emailExists = user.getEmails().stream().map(em -> em.getEmail()).filter(em -> email.equals(em)).findAny().isPresent();
		
		if(!emailExists) {
			UserEmail mail = new UserEmail();
			mail.setEmail(email);
			mail.setUser(user);			
			user.getEmails().add(mail);
		}		
		userRepository.save(user);		
		return user;
	}
	
	@Transactional
	public User updateEmail(String oldEmail,String newEmail) throws Exception {
		User user = null;
		Optional<UserEmail> oldEmailObj = emailRepository.getByEmail(oldEmail);
		
		if(oldEmailObj.isPresent()) {
			UserEmail oldEmail1 = oldEmailObj.get();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();			
			String name = auth.getName();			
			user = userRepository.getByName(name).get();
			
			if(!oldEmailObj.get().getUser().equals(user)) {
				throw new Exception("User don't have email="+oldEmail);
			}else {
				oldEmail1.setEmail(newEmail);
			}
			
		}
		
		user = userRepository.getById(user.getId());
		return user;		
	}
	
	@Transactional
	public List<User> findByName(String name){
		Pageable p = PageRequest.of(0, 50);
		//return userRepository.findAllByNameStartsWith(name, p);
		
		Page<User> page =  userRepository.findPageByParams(name, null, p);
		return page.getContent();
		
		
	}
}
