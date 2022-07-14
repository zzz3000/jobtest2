package org.zzz.jt.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.AccountService;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping(path = "/transfer")
	public ResponseEntity<Void> transfer(int toUserId, BigDecimal value) {		
		accountService.transfer(toUserId, value);
		return ok().build();
	}
	
}
