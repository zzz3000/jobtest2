package org.zzz.jt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.data.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public String login(//
			@RequestParam String username, //
			@RequestParam String password) {
		return userService.signin(username, password);
	}
	
	
	@GetMapping(path = "/login")
	public String login( Model model) { //, HttpSession session		
		
		//Integer userId = (Integer)session.getAttribute("userId");
		
		
		
		return "page";
	}

}
