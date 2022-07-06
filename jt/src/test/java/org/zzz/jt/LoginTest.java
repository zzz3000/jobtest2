package org.zzz.jt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.zzz.jt.security.JwtTokenFilter;

@Disabled
public class LoginTest {
	
public String host = "http://localhost:8080";
	
	public static void main(String[] args) {
		
	}
	
	//@Test
	@Disabled
	public void run() {
		try {
		
			String href = host +"/users/signin";//?username=sanek&password=12345";
			
			/*
			Document doc = Jsoup.connect(href).data("username", "sanek").data("password","12345").post();
			
			
			
			Element root =doc.body();
			
			Elements elements = root.getElementsByTag("body");
			
			Element e1 = elements.get(0);
			*/
			
			/*
			HttpRequest request2 = HttpRequest.newBuilder()
					  .uri(new URI(href))
					  
					  .header("username", "sanek")
					  .header("password", "12345")
					  .POST()
					  .build();
			
			
			HttpResponse<String> response = HttpClient.newBuilder()
					  .build()
					  .send(request2, BodyHandlers.ofString());
			
			response.getClass();
			*/
			
			
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost(href);

		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("username", "sanek"));
		    params.add(new BasicNameValuePair("password", "12345"));
		    httpPost.setEntity(new UrlEncodedFormEntity(params));

		    CloseableHttpResponse response = client.execute(httpPost);
		    Header[] headers = response.getAllHeaders();
		    
		    for (Header header : headers) {
				System.out.println(header);
			}
		    
		   // response.getEntity().
		    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		    client.close();
		    
		    String token = response.getFirstHeader("Authorization").getValue();
		    
		    /*
			String href1 = host +"/users/createEmail";//?username=sanek&password=12345";
			
		    Document doc = Jsoup.connect(href1).ignoreContentType(true). data("email", "newEmail@mail.ru").header(JwtTokenFilter.AUTH_HEADER, "Bearer " +token).post();
			*/
		    
		    String href1 = host +"/users/find";//?username=sanek&password=12345";
		    Document doc = Jsoup.connect(href1).ignoreContentType(true). data("name", "mich").header(JwtTokenFilter.AUTH_HEADER, "Bearer " +token).get();
			
			
			Element root =doc.body();
			
			System.out.println(root);
			
			//doc = Jsoup.connect(href).timeout(BookLoader.CONNECT_TIMEOUT).userAgent(userAgentIPhone).get();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
