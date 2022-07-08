package org.zzz.jt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.data.User;
import org.zzz.jt.security.JwtTokenFilter;
import org.zzz.jt.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");

	@Autowired
	private UserService userService;	
	
	@GetMapping(path = "/find")
	public List<User> findByParams(String name,String email, String phone, String birthDate,int pageNum, int pageSize) throws Exception{
		
		Date birthD = null;
		if(!StringUtils.isEmpty(birthD)) {
			synchronized (sdf) {
				birthD = sdf.parse(birthDate);	
			}
		}
		
		return userService.findByParams(name, email, phone, birthD, pageNum, pageSize);
	}
	
	//for browser signin
	@PostMapping("/signin")
	public String login(//
			@RequestParam String username,
			@RequestParam String password,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String token =  userService.signin(username, password);		
		response.addHeader(JwtTokenFilter.AUTH_HEADER, token);		
		return token;
	}
	
	
	//for JSON params request
	@PostMapping("/signin1")
	public String process(@RequestBody Map<String, Object> payload) throws Exception {

		String username = (String) payload.get("username");
		String password = (String) payload.get("password");

		String token = userService.signin(username, password);
		
		return token;
	}
	


	/*
	@GetMapping(path = "/login")
	public String login( Model model) { //, HttpSession session		
		//Integer userId = (Integer)session.getAttribute("userId");
		return "page";
	}
	*/

}
