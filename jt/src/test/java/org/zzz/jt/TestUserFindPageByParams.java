package org.zzz.jt;

//import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserService;

@SpringBootTest
public class TestUserFindPageByParams {
	
	

	@Autowired
	private UserService userService;

	@Test
	public void testFindByParams() {
		Date testDate = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
			testDate = sdf.parse("11.11.1996");
		}catch (Exception e) {
			
		}
		
		List<User> users=  userService.findByParams(null, null, null, testDate, 0, 5);		
		assertThat("find many users by date",users.size() > 0);
		
		users =  userService.findByParams(null, null, null, testDate, 1, 5);	
		assertThat("still find many users by date",users.size() > 0);
		
		users =  userService.findByParams(null, "mich4@mail.ru", null, null, 0, 5);	
		assertThat("find one user by email",users.size() == 1);
		
		users =  userService.findByParams(null, null, "79414442211", null, 0, 5);	
		assertThat("find one user by phone",users.size() == 1);
		
		users =  userService.findByParams(null, null, "7941444wwww", null, 0, 5);	
		assertThat("find zero user by wrong phone",users.size() == 0);
		
		users =  userService.findByParams("mich", null, "79414442211", null, 0, 5);	
		assertThat("find one user by phone and login",users.size() == 1);
		
		users =  userService.findByParams("zzzmich", null, "79414442211", null, 0, 5);	
		assertThat("size zero", users.size() == 0);
		
	}

}
