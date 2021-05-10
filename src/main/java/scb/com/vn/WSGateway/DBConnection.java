package scb.com.vn.WSGateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import scb.com.vn.WSGateway.rest.model.CardInfo;

public class DBConnection {
	private String domanin;
	private String username;
	private String password;
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);
	
	public DBConnection() {
		super();
	}
	
	public DBConnection(String domanin, String username, String password) {
		super();
		this.domanin = domanin;
		this.username = username;
		this.password = password;
	}
	
	public String getDomanin() {
		return domanin;
	}
	
	public void setDomanin(String domanin) {
		this.domanin = domanin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int updateDBPinChange(String idTxn) throws SQLException {
		PreparedStatement preStatement = null;
        ResultSet rs = null;
        Connection conn = null;
        int index = 0;
        try {
			conn = DriverManager.getConnection(domanin, username, password);
			if (conn != null) {
				String query = "UPDATE pin_change set STATUS_IND = 'Y' WHERE ID_PIN = ?";
				
				conn.setAutoCommit(false);
	            preStatement = conn.prepareStatement(query);
	            preStatement.setString(1, idTxn);
	            //rs = preStatement.executeQuery();
	            index = preStatement.executeUpdate();
	            if (index == 1) {
                    conn.commit();
                } else {
                	conn.rollback();
                }
	        } else {
                LOGGER.error("Check connection. Cannot connect to this connection.");
                
            }
		}catch (SQLException e) {
			conn.rollback();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            if(rs != null) {
            	rs.close();
            }
            if(preStatement != null) {
                preStatement.close();
            }
			if(conn != null) {
            	conn.close();
            }
        }
        
        return index;
	}
	
	public CardInfo getInfoCardById(String idTxn) throws SQLException {
		CardInfo cardInfo = null;
		PreparedStatement preStatement = null;
        ResultSet rs = null;
        Connection conn = null;
		try {
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.49.15:3020/DW.WORLD", "ccps", "CcpS12#$");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(domanin, username, password);
			if (conn != null) {
				String query = "SELECT LOC, LAST_PAN FROM PIN_CHANGE WHERE ID_PIN = ?";
				preStatement = conn.prepareStatement(query);
				preStatement.setString(1, idTxn);
				rs = preStatement.executeQuery();
				while (rs.next()) {
					String loc = rs.getString("LOC");
					String lastFourDigits = rs.getString("LAST_PAN");
					cardInfo = new CardInfo(loc, lastFourDigits);
				}
			} else {
				LOGGER.error("Check connection. Cannot connect to this connection.");
			}
		}catch (SQLException e) {
            LOGGER.error("State: " + e.getSQLState());
            LOGGER.error("Message: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
		finally {
            if(rs != null) {
                rs.close();
            }
            if(preStatement != null) {
                preStatement.close();
            }
			if(conn != null) {
            	conn.close();
            }
        }
		
		return cardInfo;
	}
}

/*
public static void excuteQuery(Connection connection, String query, List<String> params) throws SQLException {
    PreparedStatement preStatement = null;
    ResultSet rs = null;
    try {
        preStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.size(); i++) {
            preStatement.setString((i+1), params.get(i));
        }
        rs = preStatement.executeQuery();
        int i = 0;
        while (rs.next()) {
            System.out.println("*************************** CARD " + ++i + " ***************************");
            System.out.println(rs.getString("CMND"));
            System.out.println(rs.getString("PAN_MASK"));
            System.out.println(rs.getString("LOC"));
            System.out.println(rs.getString("ACTIVE_DATE"));
            System.out.println(rs.getString("CUS_NAME"));
            System.out.println(rs.getString("CIF_NO"));
            System.out.println(rs.getString("CARD_TYPE"));
            System.out.println(rs.getString("LOC_LIMIT"));
            System.out.println(rs.getString("BRCH_CODE"));
            System.out.println(rs.getString("CARD_BRCH"));
            System.out.println(rs.getString("PAN_ENC"));
            System.out.println(rs.getString("KHOA_THE"));
        }
    } catch (SQLException ex) {
        System.err.println(ex);
    } finally {
        if(rs != null) {
            rs.close();
        }
        if(preStatement != null) {
            preStatement.close();
        }
		if ()
    }
}
*/





















