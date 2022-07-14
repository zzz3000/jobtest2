package org.zzz.jt.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.PhoneService;

@RestController
@RequestMapping("/phone")
public class PhoneController {
	
	@Autowired
	private PhoneService phoneService;

	@RequestMapping(method = RequestMethod.POST)
	 public ResponseEntity<Void> create(String phone) throws Exception {
		phoneService.create(phone);
		return ok().build();
	}	

	@RequestMapping(method = RequestMethod.PUT)
	 public ResponseEntity<Void> update(String oldPhone, String newPhone) throws Exception {
		phoneService.update(oldPhone, newPhone);
		return ok().build();
	}

	@RequestMapping(method = RequestMethod.DELETE)
	 public ResponseEntity<Void> delete(String phone) throws Exception {
		phoneService.delete(phone);
		return ok().build();
	}


}
