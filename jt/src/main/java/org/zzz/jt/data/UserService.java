package org.zzz.jt.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zzz.jt.repository.EmailReposotory;
import org.zzz.jt.repository.UserRepository;
import org.zzz.jt.security.CustomException;
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
			
			User u = getByEmailOrPhone(username);
			
			if(u==null) {
				throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserWithDetails userWDetails = new UserWithDetails(u);
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userWDetails.getUsername(), password));
			
			//return jwtTokenProvider.createToken(username, userRepository.getByName(username).get().getAuthorities());
			return jwtTokenProvider.createToken(u.getId(), null);
		} catch (AuthenticationException e) {
			//throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			
			//e.printStackTrace();
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
	public List<User> findByName(String name) {
		Pageable p = PageRequest.of(0, 5);
		// return userRepository.findAllByNameStartsWith(name, p);

		// Page<User> page = userRepository.findPageByParams(name,
		// "mich4_second@mail.ru","1234", p);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
			java.util.Date date1998 = sdf.parse("11.11.1998");

			// Page<User> page = userRepository.findPageByParams(null, null,"12345", p);

			Page<User> page = userRepository.findPageByParams(null, null, "12345", date1998, p);

			return page.getContent();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Transactional
	public User getByEmailOrPhone(String name) {		
		
		User u = null;
		List<User> list = findByParams(null, name, null, null, 0, 5);
		
		if(list.size()==0) {
			list = findByParams(null, null, name, null, 0, 5);
		}		
		if(list.size()>0) {
			u = list.get(0);
		}
		
		return u;
	}
	
	
	@Transactional
	public List<User> findByParams(String userName,  String email, String phone, Date birthParam, int pageNum, int pageSize){
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		
		Pageable p = PageRequest.of(pageNum, pageSize);
		
		Page<User> page = userRepository.findPageByParams(userName, email,phone, birthParam, p);
		
		return page.getContent();
		
	}
	
}
