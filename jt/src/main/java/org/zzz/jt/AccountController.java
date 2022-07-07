package org.zzz.jt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.data.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {
	

	@Autowired
	private UserService userService;

}
