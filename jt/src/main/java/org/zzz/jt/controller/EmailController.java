package org.zzz.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.EmailService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(String email) throws Exception {
		emailService.create(email);
		return ok().build();
	}
	

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(String oldEmail, String newEmail) throws Exception {
		emailService.update(oldEmail, newEmail);
		return ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(String email) throws Exception {
		emailService.delete(email);
		return ok().build();
	}

}
