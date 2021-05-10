package scb.com.vn.WSGateway;

import my.com.webservices.CardworksServiceLocator;
import my.com.webservices.Cardworks_PortType;
import my.com.webservices.PINSelectionReqBean;
import my.com.webservices.PINSelectionRespBean;

public class WSClient {
	private Cardworks_PortType cw_port = null;
	
	//private final static String MERCHANT_ID = "000000120000011";
    //private final static String MERCHANT_ID_DD = "1111111116";
        
    //private final static String TERMINAL_ID = "01000006";
    //private final static String TID_DD = "10001998";
    
    //private final static String OFFICER_ID = "00402";
    private final static String FI = "970429";
    private final static String ACT_IND = "3";
    //private final static String CURRENCY_CODE = "704";
    
    //private static final Logger LOGGER = LogManager.getLogger(WSClient.class);
    
    public PINSelectionRespBean peformChangePin(String addressWS, String seqNo,
    		String loc, String lastFourDigits, String pinEnc, String phone) {
    	
    	PINSelectionRespBean resp = null;
        try {
        	CardworksServiceLocator locator = new CardworksServiceLocator();
            locator.setMaintainSession(true);
            locator.setCardworksEndpointAddress(addressWS);//--set url WS
            cw_port = locator.getCardworks();
                        
            PINSelectionReqBean pinSelectionReq = new PINSelectionReqBean();
        	pinSelectionReq.setSequenceNo(seqNo);
        	pinSelectionReq.setFi(FI);
        	
        	pinSelectionReq.setPan(loc + lastFourDigits);
        	pinSelectionReq.setActInd(ACT_IND);
        	pinSelectionReq.setMobileNo(phone);
        	pinSelectionReq.setNewPIN(pinEnc); //E153EA428B22EF79 = 111111
        	
        	resp = cw_port.PINSelection(pinSelectionReq); 
        	
        } catch (Exception e) {
            e.printStackTrace();
			//LOGGER.error("Hold amount error: " + e);
			return resp;
        }
        
        return resp;
    }
    
}
