package org.zzz.jt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.zzz.jt.security.JwtTokenFilter;
import org.zzz.jt.security.JwtTokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerMVCTest {

	
	@Autowired
    private MockMvc mockMvc;
	
	
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	
	@Test
	@Disabled
	public void test()throws Exception {
		
		JwtTokenUtil jwtTokenUtil =mock(JwtTokenUtil.class);
		
		//when(jwtTokenUtil.createToken(Mockito.any(), Mockito.any())).then(null)
		
		when(jwtTokenUtil.validateToken(Mockito.any())).thenReturn(true);
		when(jwtTokenUtil.getUsername(Mockito.any())).thenReturn(1);
		
		
		jwtTokenFilter.setJwtTokenUtil(jwtTokenUtil);
		
		//jwtTokenFilter.;
		
		mockMvc.perform(get("/account/transfer?toUserId=4&value=10").header(JwtTokenFilter.AUTH_HEADER , "Bearer " +"zzz")).andExpect(status().isOk());
		
	}
}
