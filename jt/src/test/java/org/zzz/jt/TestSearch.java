package org.zzz.jt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserService;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestSearch {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserController controller;

	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		
		//User[] users 
		

		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("email", "email");
		
		//String s2 = restTemplate.postForObject("http://localhost:" + port + "/users/createEmail",params1,String.class);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "sanek");
		params.put("password", "12345");
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject personJsonObject = new JSONObject();
	    personJsonObject.put("username", "sanek");
	    personJsonObject.put("password", "12345");
		
		HttpEntity<String> request = 
			      new HttpEntity<String>(personJsonObject.toString(), headers);
		
		//String s = restTemplate.postForObject("http://localhost:" + port + "/users/signin1",request,String.class);
		
		ResponseEntity<String> re = restTemplate.postForEntity("http://localhost:" + port + "/users/signin1", request, String.class);
		
		String token  = re.getBody();
		
		
		headers = new HttpHeaders();
		headers.add("Authorization","Bearer "+ token);
		request = 
			      new HttpEntity<String>(null, headers);
		
		ResponseEntity<User[]> result = restTemplate.exchange("http://localhost:" + port + "/users/find?name=mich",
				HttpMethod.GET, request, User[].class);
		//
		
		User[]  users = result.getBody();
		
		System.out.println(users[0]);
		//ResponseEntity<User[]> response
		//String s1 = restTemplate.getForObject("http://localhost:" + port + "/users/find?name=mich",	String.class);
		
		
		System.out.println(result);

	}
	
	@Test
	@Disabled
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		
		List<User> list = controller.findByName("mich");
		
		System.out.println(list);
	}

}
