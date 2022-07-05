package org.zzz.jt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller 
@RequestMapping("/")
public class LoginController {
	
	@GetMapping(path = "/login")
	public String login( Model model) { //, HttpSession session		
		
		//Integer userId = (Integer)session.getAttribute("userId");	
		
		return "page";
	}

}
