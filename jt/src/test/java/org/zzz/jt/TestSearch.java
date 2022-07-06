package org.zzz.jt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserService;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestSearch {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		
		List<User> list = controller.findByName("mich");
		
		System.out.println(list);
	}

}
