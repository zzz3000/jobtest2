package org.zzz.jt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping(path = "/create")
	public boolean create(String email) throws Exception {
		emailService.create(email);
		return true;
	}
	
	
	

	@PostMapping(path = "/update")
	public boolean update(String oldEmail, String newEmail) throws Exception {
		emailService.update(oldEmail, newEmail);
		return true;
	}

	@PostMapping(path = "/delete")
	public boolean delete(String email) throws Exception {
		emailService.delete(email);
		return true;
	}

}
