package scb.com.vn.WSGateway.rest.model;

public class InputChangePin {
	private String idTxn;
	private String seqNo;
	private String pinEncrypt;
	private String phone;
		
	public InputChangePin() {
		
	}

	public InputChangePin(String idTxn, String pinEncrypt, String phone, String seqNo) {
		super();
		this.idTxn = idTxn;
		this.seqNo = seqNo;
		this.pinEncrypt = pinEncrypt;
		this.phone = phone;
	}

	public String getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(String idTxn) {
		this.idTxn = idTxn;
	}

	public String getPinEncrypt() {
		return pinEncrypt;
	}

	public void setPinEncrypt(String pinEncrypt) {
		this.pinEncrypt = pinEncrypt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	
}
