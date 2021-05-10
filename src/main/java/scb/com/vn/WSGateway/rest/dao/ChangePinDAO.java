package scb.com.vn.WSGateway.rest.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import my.com.webservices.PINSelectionRespBean;
import scb.com.vn.WSGateway.DBConnection;
import scb.com.vn.WSGateway.WSClient;
import scb.com.vn.WSGateway.rest.model.CardInfo;
import scb.com.vn.WSGateway.rest.model.WSResponse;

@Repository
public class ChangePinDAO {
	@Value("${url.ws}")
	private String addressWS;
	
	@Value("${spring.datasource.url}")
	private String domanin; 

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	
	private DBConnection  conn = new DBConnection();
	private WSClient wsClient = new WSClient();
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePinDAO.class);
	
	public WSResponse getChangePin(String seqNo, String idTxn, String pinEncrypt, String phone) throws SQLException {
		WSResponse wsResp = new WSResponse("0", "0");
		conn.setDomanin(domanin);
		conn.setUsername(username);
		conn.setPassword(password);
		
		CardInfo cardInfo = conn.getInfoCardById(idTxn);
		//WSResponse wsResp = new WSResponse("000", "Success");
		if (cardInfo != null) {
			PINSelectionRespBean pinResp = wsClient.peformChangePin(addressWS, seqNo, cardInfo.getLoc(), cardInfo.getLastFourDigits(), pinEncrypt, phone);
	        if (pinResp != null) { //ko time out
	        	if (pinResp.getResponseCode().equals("000")) { //doi pin thanh cong
	        		//update lai trang thai da doi pin
	        		int indexUpdate = conn.updateDBPinChange(idTxn);
	        		if (indexUpdate == 1) { //update thanh cong
		        		wsResp.setRespCode(pinResp.getResponseCode());
		        		wsResp.setRespDesc("SUCCESS");
		        		LOGGER.info("Change pin and updated status successful for ID: " + idTxn+ ", LOC: " + cardInfo.getLoc() + ", Last4Digits: " + cardInfo.getLastFourDigits() + ", Phone: " + phone);
	        		} else { //update that bai
	        			wsResp.setRespCode("UUU");
		        		wsResp.setRespDesc("Update failed");
		        		LOGGER.error("Change pin successful and updated status failed for for ID: " + idTxn+ ", LOC: " + cardInfo.getLoc() + ", Last4Digits: " + cardInfo.getLastFourDigits() + ", Phone: " + phone);
	        		}
	        	} else {// doi pin ko thanh cong
	        		wsResp.setRespCode(pinResp.getResponseCode());
	        		wsResp.setRespDesc(pinResp.getResponseDescription());
	        		LOGGER.error("Response: " + wsResp.getRespCode() + " " + wsResp.getRespDesc() + " - Change pin failed for ID: " + idTxn+ ", LOC: " + cardInfo.getLoc() + ", Last4Digits: " + cardInfo.getLastFourDigits() + ", Phone: " + phone);
	        	}
	        } else {// bi time out
	        	wsResp.setRespCode("TTT");
        		wsResp.setRespDesc("Time out");
        		LOGGER.error("Change pin timeout for ID: " + idTxn+ ", LOC: " + cardInfo.getLoc() + ", Last4Digits: " + cardInfo.getLastFourDigits() + ", Phone: " + phone);
	        }
		}
		else {
			LOGGER.error("Not found card information PIN ID: " + idTxn);
		}
		return wsResp;
	}
}
