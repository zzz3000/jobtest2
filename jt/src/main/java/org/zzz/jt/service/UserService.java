package org.zzz.jt.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zzz.jt.data.Account;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserEmail;
import org.zzz.jt.data.UserPhone;
import org.zzz.jt.data.UserWithDetails;
import org.zzz.jt.repository.UserRepository;
import org.zzz.jt.security.CustomException;
import org.zzz.jt.security.JwtTokenUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;		
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenProvider;
	

	public String signin(String emailOrPhone, String password) {
		try {
			User u = getByEmailOrPhone(emailOrPhone);
			if(u==null) {
				throw new CustomException("User not found by email or phone", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserWithDetails userWDetails = new UserWithDetails(u);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userWDetails.getUsername(), password));
			return jwtTokenProvider.createToken(u.getId(), null);
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);		
		}
	}
	
	@Transactional
	public User getById(int id) {
		return userRepository.getById(id);
	}
	
	@Transactional
	public User getByIdEager(int id) {
		User u =  userRepository.getById(id);
		if(u.getAccount()!=null) {
			u.getAccount().getBalance();
		}
		
		//System.out.println(u.getEmails());
		
		u.getEmails().size();
		u.getPhones().size();
		return u;
	}
	
	
	@Transactional
	public int createUser(String userName, String password, Date dateOfBirt,String[] phones, String[] emails, BigDecimal balance) {
		
		User u = new User();
		u.setEmails(new HashSet<UserEmail>());
		u.setPhones(new HashSet<UserPhone>());
		
		u.setName(userName);
		u.setPassword(password);
		u.setDateOfBirth(dateOfBirt);
		Account account = new Account();
		account.setUser(u);
		account.setBalance(balance);
		
		u.setAccount(account);
		
		for (String phone : phones) {
			UserPhone uPhone = new UserPhone();
			uPhone.setPhone(phone);
			uPhone.setUser(u);
			u.getPhones().add(uPhone);
		}
		
		for (String email : emails) {
			UserEmail uEmail = new UserEmail();
			uEmail.setEmail(email);
			uEmail.setUser(u);
			u.getEmails().add(uEmail);
		}		
		
		userRepository.save(u);
		return u.getId();
		
	}
	
	
	@Transactional
	public int getCurrentUserId() {				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		String userIdStr = auth.getName();
		int fromUserId = Integer.parseInt(userIdStr);
		return fromUserId;
	}
	
	@Transactional
	public User getCurrentUser() {	
		return userRepository.getById(getCurrentUserId());
	}
	
	@Transactional
	public User getByEmailOrPhone(String name) {			
		User u = null;
		List<User> list = findByParams(null, name, null, null, 0, 5).getContent();
		
		if(list.size()==0) {
			list = findByParams(null, null, name, null, 0, 5).getContent();
		}		
		if(list.size()>0) {
			u = list.get(0);
		}		
		return u;
	}
	
	
	@Transactional
	public Page<User> findByParams(String userName,  String email, String phone, Date birthParam, int pageNum, int pageSize){		
		Pageable p = PageRequest.of(pageNum, pageSize,Sort.by("id").ascending());	
	
		Page<User> page = userRepository.findPageByParams(userName, email,phone, birthParam, p);		
		
		return page;	
	}	
	
	
}
