package scb.com.vn.WSGateway.rest.model;

public class CardInfo {
	private String loc;
	private String lastFourDigits;
	
	public CardInfo() {
		super();
	}

	public CardInfo(String loc, String lastFourDigits) {
		super();
		this.loc = loc;
		this.lastFourDigits = lastFourDigits;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}
	
	
}
