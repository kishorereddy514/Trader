package com.qa.action;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.bean.Users;
import com.qa.client.RestClient;

public class Login extends TestBase{
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	
	public CloseableHttpResponse login(String url, Users users) throws ClientProtocolException, IOException {
	// add Headers
	HashMap<String, String> headerMap = new HashMap<String, String>();
	headerMap.put("Content-Type", "application/json");
	
	restClient = new RestClient();
	// Jackson API
	ObjectMapper objectMapper = new ObjectMapper();
	String entityString = objectMapper.writeValueAsString(users);
	System.out.println(entityString);
	httpResponse= restClient.post(url, entityString, headerMap);
	return httpResponse;
	
	}
}
