package org.zzz.jt;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserPhone;
import org.zzz.jt.repository.UserRepository;
import org.zzz.jt.service.AccountService;
import org.zzz.jt.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
public class AccountServiceTest {
	

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	
	
	
	@Test
	public void testTransfer() {
		
		int userFromId = 1;
		int userToId = 4;
		
		BigDecimal value = new BigDecimal(-1);
		
		User userFrom = userService.getByIdEager(userFromId);
		
		
		
		//User userFrom = userService.getById(userFromId);
		
		Set<UserPhone> phones = userFrom.getPhones();
		
		
		BigDecimal fromBefore = userFrom.getAccount().getBalance();		
		
		accountService.transfer(userFromId, userToId, value);		

		userFrom = userService.getByIdEager(userFromId);
		BigDecimal fromAfter = userFrom.getAccount().getBalance();
		
		assertThat("balance changed correctly", fromBefore.subtract(value).equals(fromAfter));
		
		System.out.println("zzz");
		
		
	}

}
