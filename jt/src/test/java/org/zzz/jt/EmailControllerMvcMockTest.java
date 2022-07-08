package org.zzz.jt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.zzz.jt.data.User;
import org.zzz.jt.security.JwtTokenFilter;
import org.zzz.jt.security.JwtTokenUtil;
import org.zzz.jt.service.UserService;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerMvcMockTest {

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

		String firstEmail = "zzz17@email.com";
		mockMvc.perform(
				post("/email/create").param("email", firstEmail).header(JwtTokenFilter.AUTH_HEADER, "Bearer " + "zzz"))
				.andExpect(status().isOk());

		User user = userService.getByIdEager(userId);

		assertThat("user contain new email",
				user.getEmails().stream().map(em -> em.getEmail()).collect(Collectors.toList()), hasItem(firstEmail));
		
		String secondEmail = "zzz18@email.com";
		mockMvc.perform(
				post("/email/update").param("oldEmail", firstEmail).param("newEmail", secondEmail).header(JwtTokenFilter.AUTH_HEADER, "Bearer " + "zzz"))
				.andExpect(status().isOk());
		
		user = userService.getByIdEager(userId);
		
		assertThat("user contain secondEmail",
				user.getEmails().stream().map(em -> em.getEmail()).collect(Collectors.toList()), hasItem(secondEmail));
		
		assertThat("user not contain firstEmail",
				user.getEmails().stream().map(em -> em.getEmail()).collect(Collectors.toList()), not(hasItem(firstEmail)));
		
		mockMvc.perform(
				post("/email/delete").param("email", secondEmail).header(JwtTokenFilter.AUTH_HEADER, "Bearer " + "zzz"))
				.andExpect(status().isOk());
		
		user = userService.getByIdEager(userId);
		
		assertThat("user not contain secondEmail",
				user.getEmails().stream().map(em -> em.getEmail()).collect(Collectors.toList()), not(hasItem(secondEmail)));
		
		

	}
}
