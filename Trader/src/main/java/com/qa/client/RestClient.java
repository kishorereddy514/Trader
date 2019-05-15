package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	//GET method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient= HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); // htttp get request
		return httpclient.execute(httpGet); // hit GET url
	}
	
	//GET with Header method
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient= HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); // htttp get request
		for(Map.Entry<String, String> entry:headerMap.entrySet()) {
			httpGet.addHeader(entry.getKey(),entry.getValue());
		}
		
		return httpclient.execute(httpGet); // hit GET url
	}
	
	//POST method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);//http post request
		httpPost.setEntity(new StringEntity(entityString));//for payload
		
		//for headers
		for(Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(),entry.getValue());
		}
		
		
		return httpClient.execute(httpPost); // hit post url and return
		
	}
	
	//PUT method
		public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(url);//http put request
			httpPut.setEntity(new StringEntity(entityString));//for payload
			
			//for headers
			for(Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPut.addHeader(entry.getKey(),entry.getValue());
			}
			
			
			return httpClient.execute(httpPut); // hit put url and return
			
		}
	//Delete method
	/*public CloseableHttpResponse delete(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);//http delete request
		
		//for headers
		for(Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpDelete.addHeader(entry.getKey(),entry.getValue());
		}
		
		
		return httpClient.execute(httpDelete); // hit delete url and return
		
	}*/
}
