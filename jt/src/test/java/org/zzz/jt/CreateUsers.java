package org.zzz.jt;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zzz.jt.service.UserService;

@SpringBootTest
public class CreateUsers {
	
	@Autowired
	private UserService userService;
	
	
	
	@Test
	public void createUsers() throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		Date testDate = sdf.parse("11.11.1996");
		
		userService.createUser("sanek", "12345", testDate, new String[] {"79414112211"}, new String[] {"sanek@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael1", "12345", testDate, new String[] {"70001111111"}, new String[] {"michael1@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael2", "12345", testDate, new String[] {"70001111112"}, new String[] {"michael22@mail.ru","michael21@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael3", "12345", testDate, new String[] {"70001111113"}, new String[] {"michael3@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael4", "12345", testDate, new String[] {"70001111114"}, new String[] {"michael4@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael5", "12345", testDate, new String[] {"70001111115"}, new String[] {"michael5@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael6", "12345", testDate, new String[] {"70001111116"}, new String[] {"michael6@mail.ru"},  BigDecimal.valueOf(100));
		
		userService.createUser("michael7", "12345", testDate, new String[] {"70001111117"}, new String[] {"michael7@mail.ru"},  BigDecimal.valueOf(100));
		
	}

}
