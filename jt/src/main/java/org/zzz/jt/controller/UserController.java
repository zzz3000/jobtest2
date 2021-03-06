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
public class UserController {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");

	@Autowired
	private UserService userService;	
	
	@GetMapping(path = "/users")
	public Page<User> findByParams(String name,String email, String phone, String birthDate,int pageNum, int pageSize) {
		
		Date birthD = null;
		try {
		if(!StringUtils.isEmpty(birthD)) {
			synchronized (sdf) {
				birthD = sdf.parse(birthDate);	
			}
		}
		}catch (Exception e) {
			throw new ApiException("Bad date format for " + birthDate);
		}
		
		return userService.findByParams(name, email, phone, birthD, pageNum, pageSize);
	}
	
	
	


	

}
