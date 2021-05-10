package scb.com.vn.WSGateway.rest.controller;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scb.com.vn.WSGateway.rest.dao.ChangePinDAO;
import scb.com.vn.WSGateway.rest.model.InputChangePin;
import scb.com.vn.WSGateway.rest.model.WSResponse;

//https://howtodoinjava.com/spring-boot2/rest-api-example/
@RestController
@RequestMapping(path = "/changepin")
public class WSController {
	private static final Logger LOGGER = LogManager.getLogger(WSController.class);
	
	@Autowired
	private ChangePinDAO changePinDAO;
	
	@GetMapping("/")
    public String index() {
		System.out.println("Running test change pin ");
		LOGGER.info("Running test change pin");
		
        return "Running";
    }
	
	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public WSResponse getWSResponse(@RequestBody InputChangePin pin) throws SQLException {
		WSResponse wsResp = changePinDAO.getChangePin(pin.getSeqNo(), pin.getIdTxn(), pin.getPinEncrypt(), pin.getPhone());
		return wsResp;
	}
	
}
