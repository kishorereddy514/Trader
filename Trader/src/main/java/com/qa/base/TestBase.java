package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

	public static Properties prop;
	public static int RESPONSE_STATUS_CODE_200 = 200;
	public static int RESPONSE_STATUS_CODE_201 = 201;
	public static int RESPONSE_STATUS_CODE_400 = 400;
	public static int RESPONSE_STATUS_CODE_401 = 401;
	public static int RESPONSE_STATUS_CODE_500 = 500;

	public static String NEW_SYSTEM_URL  = "http://www.trustedads.com";
	public static String LEGACY_SYSTEM_URL  = "http://www.legacytrustedads.com";
	public static String LOGIN_END_POINT  = "/login";
	public static String INVENTORY_AD  = "/Inventory/Ad";
	public static String LEGACY_INVENTORY_AD  = "/inventory";
	public static String NEW_SYSTEM_USER_NAME  = "admin";
	public static String NEW_SYSTEM_PASSWORD  = "12345678";
	public static String LEGACY_SYSTEM_USER_NAME  = "administrator";
	public static String LEGACY_SYSTEM_PASSWORD  = "password";
	public static String NEW_SYSTEM_LOGIN_URL  = NEW_SYSTEM_URL + LOGIN_END_POINT;
	public static String LEGACY_SYSTEM_LOGIN_URL  = LEGACY_SYSTEM_URL + LOGIN_END_POINT;

	public TestBase() {
		prop = new Properties();
		try {
			FileInputStream inStream = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties");
			prop.load(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

}
