package org.zzz.jt;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserPhone;
import org.zzz.jt.security.JwtTokenFilter;
import org.zzz.jt.security.JwtTokenUtil;
import org.zzz.jt.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerMvcMockTest {

	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Test
	public void testCRUD() throws Exception {
		int userId = 6;

		JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
		when(jwtTokenUtil.validateToken(Mockito.any())).thenReturn(true);
		when(jwtTokenUtil.getUsername(Mockito.any())).thenReturn(userId);

		jwtTokenFilter.setJwtTokenUtil(jwtTokenUtil);
		
		
		String firstPhone = "12345678900" ;
		mockMvc.perform(
				post("/phone").param("phone", firstPhone).header(JwtTokenFilter.AUTH_HEADER, "Bearer " + "zzz"))
				.andExpect(status().isOk());

		User user = userService.getByIdEager(userId);
		
		Set<UserPhone> phones = user.getPhones();
		

		assertThat("user contain new phone",
				user.getPhones().stream().map(em -> em.getPhone()).collect(Collectors.toList()), hasItem(firstPhone));
		
		
		
		mockMvc.perform(
				delete("/phone").param("phone", firstPhone).header(JwtTokenFilter.AUTH_HEADER, "Bearer " + "zzz"))
				.andExpect(status().isOk());
		
		user = userService.getByIdEager(userId);

		assertThat("user contain new phone",
				user.getPhones().stream().map(em -> em.getPhone()).collect(Collectors.toList()),not( hasItem(firstPhone)));
		
		String secondPhone = "111111111112";
	}
	
}
