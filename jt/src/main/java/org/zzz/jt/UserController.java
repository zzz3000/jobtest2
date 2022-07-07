package org.zzz.jt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserService;
import org.zzz.jt.security.JwtTokenFilter;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public String login(//
			@RequestParam String username, //
			@RequestParam String password,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		//System.out.println(request.getContentType());
		String token =  userService.signin(username, password);
		
		response.addHeader(JwtTokenFilter.AUTH_HEADER, token);
		
		return token;
	}
	
	@RequestMapping(value = "/signin1", method = RequestMethod.POST)
	public String process(@RequestBody Map<String, Object> payload) throws Exception {

		String username = (String) payload.get("username");

		String password = (String) payload.get("password");

		String token = userService.signin(username, password);
		
		return token;
		// response.addHeader("Authorization", token);

	}

	@GetMapping(path = "/login")
	public String login( Model model) { //, HttpSession session		
		//Integer userId = (Integer)session.getAttribute("userId");
		return "page";
	}
	
	@PostMapping(path = "/createEmail")
	public User createEmail(String email) {
		return userService.createEmail(email);
	}
	
	
	
	
	@PostMapping(path = "/updateEmail")
	public User updateEmail(String oldEmail, String newEmail) {
		try {
			return userService.updateEmail(oldEmail,newEmail);
		}catch (Exception e) {
			//return null;
			throw new RuntimeException(e);
		}
	}

	public User deleteEmail(String email) {
		return null;
	}
	
	@GetMapping(path = "/find")
	public List<User> findByParams(String name,String email, String phone, String birthDate,int pageNum, int pageSize){
		Date birthD = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
			birthD = sdf.parse(birthDate);
		}catch (Exception e) {
			//TODO
		}
		
		return userService.findByParams(name, email, phone, birthD, pageNum, pageSize);
		
	}

}
