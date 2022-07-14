package org.zzz.jt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzz.jt.data.User;
import org.zzz.jt.service.UserService;

@RestController
//@RequestMapping("/users")
public class UserController {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");

	@Autowired
	private UserService userService;	
	
	@GetMapping(path = "/users")
	public Page<User> findByParams(String name,String email, String phone, String birthDate,int pageNum, int pageSize) throws Exception{
		
		Date birthD = null;
		if(!StringUtils.isEmpty(birthD)) {
			synchronized (sdf) {
				birthD = sdf.parse(birthDate);	
			}
		}
		
		return userService.findByParams(name, email, phone, birthD, pageNum, pageSize);
	}
	
	
	


	

}
