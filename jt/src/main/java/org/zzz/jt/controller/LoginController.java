package org.zzz.jt.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.security.JwtTokenFilter;
import org.zzz.jt.service.UserService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private UserService userService;

	// for browser signin
	@PostMapping("/login")
	public String login(//
			@RequestParam String username, @RequestParam String password, HttpServletRequest request,
			HttpServletResponse response) {

		String token = userService.signin(username, password);
		response.addHeader(JwtTokenFilter.AUTH_HEADER, token);
		return token;
	}

	// for JSON params request
	@PostMapping("/login1")
	public String process(@RequestBody Map<String, Object> payload) throws Exception {

		String username = (String) payload.get("username");
		String password = (String) payload.get("password");

		String token = userService.signin(username, password);

		return token;
	}
}
