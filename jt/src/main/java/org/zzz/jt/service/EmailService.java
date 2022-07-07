package org.zzz.jt.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserEmail;
import org.zzz.jt.repository.EmailReposotory;
import org.zzz.jt.repository.UserRepository;

@Service
public class EmailService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	EmailReposotory emailRepository;
	
	
	@Transactional
	public void delete(String existingEmail) throws Exception {
		Optional<UserEmail>  emailOpt = emailRepository.getByEmail(existingEmail);
		
		if(!emailOpt.isPresent()) {
			throw new Exception("email=" + existingEmail +" not found" );
		}
		UserEmail emailObj = emailOpt.get();
		User currentUser = userService.getCurrentUser();
		
		if(emailObj.getUser() ==null || !emailObj.getUser().equals(currentUser)) {
			throw new Exception("you are not owner of email=" + existingEmail );
		}
		
		emailRepository.delete(emailObj);
	}
	
	@Transactional
	public void create(String newEmail) throws Exception {
		
		if(emailRepository.getByEmail(newEmail).isPresent()) {
			throw new Exception("email=" + newEmail +" already exists" );
		}
		
		User user = userService.getCurrentUser();		
		
		UserEmail mail = new UserEmail();
		mail.setEmail(newEmail);
		mail.setUser(user);			
		user.getEmails().add(mail);
		userRepository.save(user);	
		
	}	
	
	@Transactional
	public void update(String oldEmail,String newEmail) throws Exception {
		
		User currentUser = userService.getCurrentUser();
		Optional<UserEmail> oldEmailOpt = emailRepository.getByEmail(oldEmail);
		
		//check for oldEmail exists
		if(oldEmailOpt.isEmpty()) {
			throw new Exception("email=" + oldEmail +" not found");
		}

		UserEmail oldEmailObj = oldEmailOpt.get();
		//check for old email owned by current user
		if(oldEmailObj.getUser()==null || !oldEmailObj.getUser().equals(currentUser)) {
			throw new Exception("you are not owner of email=" + oldEmail );
		}
		
		//check newEmail is not exists
		if(emailRepository.getByEmail(newEmail).isPresent()) {
			throw new Exception("email=" + newEmail +" already exists" );
		}
		oldEmailObj.setEmail(newEmail);
		emailRepository.save(oldEmailObj);
	}

}
