package com.qa.test;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.action.Login;
import com.qa.base.TestBase;
import com.qa.bean.Users;
import com.qa.client.RestClient;

public class LoginAPITest extends TestBase {

	RestClient restClient;

	CloseableHttpResponse httpResponse;
	Login login;

	public LoginAPITest() {
		super();
	}

	public int getSystemLoginStatus(String systemloginURL, Boolean system) throws ClientProtocolException, IOException {

		Users users = new Users(system);
		try {
			httpResponse = login.login(systemloginURL, users);
		} catch (SocketException e) { // retry for socket exception
			httpResponse = login.login(systemloginURL, users);
		}
		// Status Code
		return httpResponse.getStatusLine().getStatusCode();
	}

	@Test //Verify new system login
	public void newSystermlogin() throws ClientProtocolException, IOException {
		Assert.assertEquals(getSystemLoginStatus(NEW_SYSTEM_LOGIN_URL, false), TestBase.RESPONSE_STATUS_CODE_200);
	}

	@Test //Verify new system login
	public void legacySystermlogin() throws ClientProtocolException, IOException {
		Assert.assertEquals(getSystemLoginStatus(LEGACY_SYSTEM_LOGIN_URL, true), TestBase.RESPONSE_STATUS_CODE_200);
	}

}
