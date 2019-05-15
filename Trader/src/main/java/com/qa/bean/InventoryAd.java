package com.qa.bean;

public class InventoryAd {

	String id;
	String vin;
	String stockNumber;
	String adState;
	String adId;
	boolean sucess;
	String legacyAdId;
	String newAdId;
	int status;
	int commandType;

	public String getNewAdId() {
		return newAdId;
	}

	public void setNewAdId(String newAdId) {
		this.newAdId = newAdId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCommandType() {
		return commandType;
	}

	public void setCommandType(int commandType) {
		this.commandType = commandType;
	}

	public String getLegacyAdId() {
		return legacyAdId;
	}

	public void setLegacyAdId(String legacyAdId) {
		this.legacyAdId = legacyAdId;
	}

	public InventoryAd() {
		
	}

	public InventoryAd(String id, String vin, String stockNumber, String adState) {
		this.id = id;
		this.vin = vin;
		this.stockNumber = stockNumber;
		this.adState = adState;
	}
	
	public InventoryAd(int commandType, String vin, String stockNumber, int status) {
		this.commandType = commandType;
		this.vin = vin;
		this.stockNumber = stockNumber;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getAdState() {
		return adState;
	}

	public void setAdState(String adState) {
		this.adState = adState;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

}
