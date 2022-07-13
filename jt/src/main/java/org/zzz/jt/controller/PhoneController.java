package org.zzz.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.PhoneService;

@RestController
@RequestMapping("/phone")
public class PhoneController {
	
	@Autowired
	PhoneService phoneService;

	@PostMapping(path = "/create")
	public boolean create(String phone) throws Exception {
		phoneService.create(phone);
		return true;
	}	

	@PostMapping(path = "/update")
	public boolean update(String oldPhone, String newPhone) throws Exception {
		phoneService.update(oldPhone, newPhone);
		return true;
	}

	@PostMapping(path = "/delete")
	public boolean delete(String phone) throws Exception {
		phoneService.delete(phone);
		return true;
	}


}
