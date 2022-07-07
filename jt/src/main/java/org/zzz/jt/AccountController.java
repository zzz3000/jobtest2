package org.zzz.jt;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.service.AccountService;


@RestController
@RequestMapping("/account")
public class AccountController {
	

	//@Autowired
	//private UserService userService;
	
	
	@Autowired
	private AccountService accountService;
	
	
	@PostMapping(path = "/transfer")
	public void transfer(int toUserId, BigDecimal value) {
		
		accountService.transfer(toUserId, value);
		
		//return userService.createEmail(email);
	}
	
	
	@GetMapping(path = "/transfer")
	public void transferGet(int toUserId, BigDecimal value) {
		
		accountService.transfer(toUserId, value);
		
		//return userService.createEmail(email);
	}

}
