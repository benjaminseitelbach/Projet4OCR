package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class DataBasePrepareService {

    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }


    public int getTicketsCount() {
    	Connection connection = null;
    	int count = 0;
    	try {
    		connection = dataBaseTestConfig.getConnection();
    		
	        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS rowcount FROM ticket");
	
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        count = rs.getInt("rowcount");
    	}catch (Exception e){
             e.printStackTrace();
        }finally {
        	 dataBaseTestConfig.closeConnection(connection);
        	 return count;
        }

    }
    
    public boolean getParkingAvailability() {
    	Connection connection = null;
    	boolean parkingAvailability = true;
    	try {
    		connection = dataBaseTestConfig.getConnection();
    		
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM PARKING WHERE PARKING_NUMBER = '1'");
	
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        parkingAvailability = rs.getBoolean(2);
	        //System.out.println(rs.getInt(1));
	        
    	}catch (Exception e){
             e.printStackTrace();
        }finally {
        	 dataBaseTestConfig.closeConnection(connection);
        	 //System.out.println(parkingAvailability);
        	 return parkingAvailability;
        }

    }
    
    public double getFare() {
    	Connection connection = null;
    	double fare = 0;
    	try {
    		connection = dataBaseTestConfig.getConnection();
    		
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM TICKET WHERE ID = '1'");
	
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        fare = rs.getDouble(4);
	        //System.out.println(rs.getInt(1));
	        
    	}catch (Exception e){
             e.printStackTrace();
        }finally {
        	 dataBaseTestConfig.closeConnection(connection);
        	 //System.out.println(parkingAvailability);
        	 return fare;
        }

    }
    
    public Date getOutTime() {
    	Connection connection = null;
    	Date outTime = new Date();
    	try {
    		connection = dataBaseTestConfig.getConnection();
    		
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM TICKET WHERE ID = '1'");
	
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        outTime = rs.getDate(6);
	        //System.out.println(rs.getInt(1));
	        
    	}catch (Exception e){
             e.printStackTrace();
        }finally {
        	 dataBaseTestConfig.closeConnection(connection);
        	 //System.out.println(parkingAvailability);
        	 return outTime;
        }

    }
}
