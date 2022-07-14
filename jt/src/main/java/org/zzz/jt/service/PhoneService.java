package org.zzz.jt.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserPhone;
import org.zzz.jt.repository.PhoneRepository;
import org.zzz.jt.repository.UserRepository;


@Service
public class PhoneService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	public boolean checkPhoneFormat(String newPhone) {		
		if(newPhone==null || newPhone.length()!=11) {	
			return false;
		}else {
			return true;
		}		
	}
	
	@Transactional
	public void delete(String existingPhone) throws Exception {
		Optional<UserPhone>  phoneOpt = phoneRepository.getByPhone(existingPhone);
		
		if(!phoneOpt.isPresent()) {
			throw new Exception("email=" + existingPhone +" not found" );
		}
		UserPhone phoneObj = phoneOpt.get();
		User currentUser = userService.getCurrentUser();
		
		if(phoneObj.getUser() ==null || !phoneObj.getUser().equals(currentUser)) {
			throw new Exception("you are not owner of email=" + existingPhone );
		}
		
		phoneRepository.delete(phoneObj);
	}
	
	@Transactional
	public void create(String newPhone) throws Exception {		
		
		if(!checkPhoneFormat(newPhone)) {
			throw new Exception("wrong phone format for " + newPhone);
		}
		
		if(phoneRepository.getByPhone(newPhone).isPresent()) {
			throw new Exception("phone=" + newPhone +" already exists" );
		}
		
		User user = userService.getCurrentUser();		
		
		UserPhone phone = new UserPhone();
		phone.setPhone(newPhone);
		phone.setUser(user);			
		user.getPhones().add(phone);
		userRepository.save(user);	
		
	}	
	
	@Transactional
	public void update(String oldPhone,String newPhone) throws Exception {
		
		if(!checkPhoneFormat(newPhone)) {
			throw new Exception("wrong phone format for " + newPhone);
		}
		
		User currentUser = userService.getCurrentUser();
		Optional<UserPhone> oldPhoneOpt = phoneRepository.getByPhone(oldPhone);
		
		//check for oldEmail exists
		if(oldPhoneOpt.isEmpty()) {
			throw new Exception("email=" + oldPhone +" not found");
		}

		UserPhone oldPhoneObj = oldPhoneOpt.get();
		//check for old email owned by current user
		if(oldPhoneObj.getUser()==null || !oldPhoneObj.getUser().equals(currentUser)) {
			throw new Exception("you are not owner of email=" + oldPhone );
		}
		
		//check newEmail is not exists
		if(phoneRepository.getByPhone(newPhone).isPresent()) {
			throw new Exception("email=" + newPhone +" already exists" );
		}
		oldPhoneObj.setPhone(newPhone);
		phoneRepository.save(oldPhoneObj);
	}
}
