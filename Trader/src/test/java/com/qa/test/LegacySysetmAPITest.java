/*package com.qa.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.bean.InventoryAd;
import com.qa.bean.InventoryAdList;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

import junit.framework.Assert;

public class LegacySysetmAPITest extends TestBase {

	public LegacySysetmAPITest() {
		super();
	}

	TestBase testBase;
	RestClient restClient;
	String url = prop.getProperty("legacySystermUrl") + prop.getProperty("legacyInventoryAd");
	CloseableHttpResponse httpResponse;
	String adid;

	@Before
	public void loginTest() throws ClientProtocolException, IOException {
		//  perform login for every event
		LoginAPITest loginAPITest = new LoginAPITest();
		loginAPITest.legacySystermlogin();
	}

	@Test(priority = 1)
	public void createNewAd() throws JsonGenerationException, JsonMappingException, IOException {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		restClient = new RestClient();

		// Jackson API -- marshalling
		ObjectMapper objectMapper = new ObjectMapper();
		InventoryAd inventoryAd = new InventoryAd(0, "Vin123", "SN123", 1);

		// Object to String
		String entityString = objectMapper.writeValueAsString(inventoryAd);
		httpResponse = restClient.put(url, entityString, headerMap);

		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, TestBase.RESPONSE_STATUS_CODE_200);

		// JSON String
		String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject jsonResponse = new JSONObject(response);

		InventoryAd inventoryAdResponse = objectMapper.readValue(response, InventoryAd.class); // unmarshalling

		Assert.assertTrue("POST operation returned false for sucess", inventoryAdResponse.isSucess());
		Assert.assertTrue("POST operation returned Adid value is empty", inventoryAdResponse.getAdId().isEmpty());
		
		adid = inventoryAdResponse.getAdId();
		verifyAdCreatedInNewSystem(prop.getProperty("newSystermUrl") + "/Inventory/Ad?ad=" + adid);
		verifyAdCreatedInLegacySystem(prop.getProperty("legacySystermUrl") + "/Inventory/Ad?ad=" + adid);
	}

	public void verifyAdCreatedInNewSystem(String newSystemGetURL) throws ClientProtocolException, IOException {
		List<InventoryAd> inventoryAdListResponse = verifyCreatedAd(newSystemGetURL);
		int listSize = inventoryAdListResponse.size();
		Assert.assertEquals("returned list size is not equal to 1", 1, listSize); // checks for duplicate if length is greater than 1

		String newAdId = inventoryAdListResponse.get(0).getNewAdId();
		Assert.assertEquals("legacy adid and new system adid are not equal", adid, newAdId);

	}

	public void verifyAdCreatedInLegacySystem(String legacySystemGetURL) throws ClientProtocolException, IOException {
		List<InventoryAd> inventoryAdListResponse = verifyCreatedAd(legacySystemGetURL);
		int listSize = inventoryAdListResponse.size();
		Assert.assertEquals("returned list size is not equal to 1", 1, listSize); // checks for duplicate if length is greater than 1

		String newAdId = inventoryAdListResponse.get(0).getNewAdId();
		Assert.assertEquals("legacy adid and new system adid are not equal", adid, newAdId);
		//TODO check status and other fields

	}

	public List<InventoryAd> verifyCreatedAd(String url) throws ClientProtocolException, IOException {
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
*/