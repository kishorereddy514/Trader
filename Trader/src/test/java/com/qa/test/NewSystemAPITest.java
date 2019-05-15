package com.qa.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.bean.InventoryAd;
import com.qa.bean.InventoryAdList;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

import junit.framework.Assert;

public class NewSystemAPITest extends TestBase {

	public NewSystemAPITest() {
		super();
	}

	RestClient restClient;
	LoginAPITest loginAPITest;
	String url = NEW_SYSTEM_URL + INVENTORY_AD;
	CloseableHttpResponse httpResponse;
	String adid;
	String legacyAdId;

	@Before
	public void loginTest() throws Exception {
		// perform login for every event
		loginAPITest = new LoginAPITest();
		int status = loginAPITest.getSystemLoginStatus(NEW_SYSTEM_LOGIN_URL, false);
		if (status != TestBase.RESPONSE_STATUS_CODE_200) {
			throw new Exception("status is not 200");
		}
	}

	// Create a new ad in the new system -- LIVE
	@Test(priority = 1)
	public void createNewAdTest() throws Exception {
		InventoryAd inventoryAd = new InventoryAd("adId123", "Vin123", "SN123", "Live");
		ObjectMapper objectMapper = new ObjectMapper();
		CloseableHttpResponse httpResponse = postAd(inventoryAd, objectMapper);
		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, TestBase.RESPONSE_STATUS_CODE_200);

		verifyPostResponse(httpResponse, objectMapper);

	}

	// Remove the newly created ad in the new system --Removed
	@Test
	public void removeNewAd() throws Exception {
		//created ad and removed that
		InventoryAd newInventoryAd = new InventoryAd("adId123", "Vin123", "SN123", "Live");
		ObjectMapper objectMapper = new ObjectMapper();
		CloseableHttpResponse httpResponse = postAd(newInventoryAd, objectMapper);
		String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		InventoryAd inventoryAdResponse = objectMapper.readValue(response, InventoryAd.class);
		String newAdId = inventoryAdResponse.getAdId();
		verifyAdCreatedInNewSystem(prop.getProperty("newSystermUrl") + "/Inventory/Ad?ad=" + newAdId);

		//remove created add
		InventoryAd inventoryAd = new InventoryAd(newAdId, "Vin123", "SN123", "Removed");
		ObjectMapper objectMapperRemove = new ObjectMapper();
		CloseableHttpResponse httpResponseRemove = postAd(inventoryAd, objectMapperRemove);
		// Status Code
		int statusCode = httpResponseRemove.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, TestBase.RESPONSE_STATUS_CODE_200);
		Thread.sleep(2000); // TODO can loop till we get 200 response code

		//verify ad removed in new system
		verifyAdCreatedInNewSystem(prop.getProperty("newSystermUrl") + "/Inventory/Ad?ad=" + newAdId);
		
	}

	public void verifyPostResponse(CloseableHttpResponse httpResponse, ObjectMapper objectMapper) throws Exception {
		// JSON String
		String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

		InventoryAd inventoryAdResponse = objectMapper.readValue(response, InventoryAd.class); // unmarshalling

		Assert.assertTrue("POST operation returned false for sucess value", inventoryAdResponse.isSucess());
		Assert.assertTrue("POST operation returned Adid value is empty", inventoryAdResponse.getAdId().isEmpty());
		adid = inventoryAdResponse.getAdId();
		//check add posted in system & legacy system
		verifyAdCreatedInNewSystem(prop.getProperty("newSystermUrl") + "/Inventory/Ad?ad=" + adid);

	}

	public CloseableHttpResponse postAd(InventoryAd inventoryAd, ObjectMapper objectMapper) throws Exception {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		restClient = new RestClient();

		// Object to String
		String entityString = objectMapper.writeValueAsString(inventoryAd);
		return restClient.post(url, entityString, headerMap);

	}

	public void verifyAdCreatedInNewSystem(String newSystemGetURL) throws Exception {
		List<InventoryAd> inventoryAdListResponse = verifyCreatedAd(newSystemGetURL);
		int listSize = inventoryAdListResponse.size();
		Assert.assertEquals("returned list size is not equal to 1", 1, listSize); // checks for duplicate if length is
																					// greater than 1
		legacyAdId = inventoryAdListResponse.get(0).getLegacyAdId();
		//check add created in legacySystem
		verifyAdCreatedInLegacySystem(prop.getProperty("legacySystermUrl") + "/Inventory/Ad?ad=" + legacyAdId);
	}

	public void verifyAdCreatedInLegacySystem(String legacySystemGetURL) throws Exception {
		List<InventoryAd> inventoryAdListResponse = verifyCreatedAd(legacySystemGetURL);
		int listSize = inventoryAdListResponse.size();
		Assert.assertEquals("returned list size is not equal to 1", 1, listSize); // checks for duplicate, if length is
																					// equal to 1 or not
	}

	public List<InventoryAd> verifyCreatedAd(String url) throws Exception {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		restClient = new RestClient();
		httpResponse = restClient.get(url, headerMap);
		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, TestBase.RESPONSE_STATUS_CODE_201);

		String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonResponse = new JSONObject(response);
		// get value from JSON Array
		String adState = TestUtil.getValueByJPath(jsonResponse, "/adState");
		Assert.assertEquals("returned adState us not Live", "Live", adState);

		// unmarshall to InventoryAdList and check list size & get value's and assert
		List<InventoryAd> inventoryAdListResponse = objectMapper.readValue(response,
				new TypeReference<List<InventoryAd>>() {
				});
		return inventoryAdListResponse;

	}

}
