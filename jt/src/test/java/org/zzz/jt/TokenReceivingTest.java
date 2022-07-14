package org.zzz.jt;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.zzz.jt.controller.PaginatedResponse;
import org.zzz.jt.data.User;
import org.zzz.jt.security.JwtTokenFilter;

import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@Disabled
public class TokenReceivingTest {
	
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	public String getToken() throws Exception{
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject personJsonObject = new JSONObject();
	    personJsonObject.put("username", "sanek@mail.ru");
	    personJsonObject.put("password", "12345");
		
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);		
		
		ResponseEntity<String> re = restTemplate.postForEntity("http://localhost:" + port + "/auth/login1", request, String.class);
		
		String token  = re.getBody();
		
		return token;
	}
	
	@Test
	public void testTokenReceiving() throws Exception {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject personJsonObject = new JSONObject();
	    personJsonObject.put("username", "sanek@mail.ru");
	    personJsonObject.put("password", "12345");
	    
	    HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);		
		
		ResponseEntity<String> re = restTemplate.postForEntity("http://localhost:" + port + "/auth/login1", request, String.class);
		
		String token  = re.getBody();	    
		
		assertThat("Status code 200", re.getStatusCodeValue(), is(200));
		
	}
	
	
	@Test
	//@Disabled
	public void testInvalidToken() throws Exception {

		String token = getToken();
		HttpHeaders headers = new HttpHeaders();
		headers = new HttpHeaders();
		headers.add(JwtTokenFilter.AUTH_HEADER,"Bearer "+ token +"1");
		
		HttpEntity<String> request =  new HttpEntity<String>(null, headers);
		
		ResponseEntity<User[]> result = restTemplate.exchange("http://localhost:" + port + "/users/find?name=mich&pageNum=0&pageSize=5",
				HttpMethod.GET, request, User[].class);
		
		assertThat("Status code 401", result.getStatusCodeValue(), is(401));
		
	}

	@Test
	public void testApiWithToken() throws Exception {
		String token = getToken();		
		
		HttpHeaders headers = new HttpHeaders();
		headers = new HttpHeaders();
		headers.add(JwtTokenFilter.AUTH_HEADER,"Bearer "+ token);
		HttpEntity<String> request =  new HttpEntity<String>(null, headers);
		
		
		ParameterizedTypeReference<PaginatedResponse<User>> responseType = 
				new ParameterizedTypeReference<PaginatedResponse<User>>() { };
		
				
				
		ResponseEntity<PaginatedResponse<User>> result 
		= restTemplate.exchange("http://localhost:" + port + "/users?name=mich&pageNum=0&pageSize=5", HttpMethod.GET, request, responseType);
				
		//ResponseEntity<Page> result = restTemplate.exchange("http://localhost:" + port + "/users?name=mich&pageNum=0&pageSize=5",
		//		HttpMethod.GET, request, Page.class);
	
		List<User>  users = result.getBody().getContent();
		
		int totalPages = result.getBody().getTotalPages();
		
		System.out.println("zzz");
		assertThat("users size = 5",users.size() == 5);
		

	}
	


}
