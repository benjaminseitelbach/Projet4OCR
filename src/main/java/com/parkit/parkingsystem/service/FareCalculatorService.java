package com.parkit.parkingsystem.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateUtil;

public class FareCalculatorService {

	private static final Logger logger = LogManager.getLogger("FareCalculatorService");
	//private boolean reccurentClient = false;
	//private TicketDAO ticketDAO;
	
	/*
	public FareCalculatorService() {
		
	}
	
	public FareCalculatorService(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}
	*/
	public void calculateFare(Ticket ticket, int vehicleRegNumberCount) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		Date inDate = ticket.getInTime();
		Date outDate = ticket.getOutTime();
		double durationHours = DateUtil.getDurationHours(inDate, outDate);
		
		//FREE 30 FIRSTS MINUTES PARKING
		if (durationHours > 0.5) {
			durationHours -= 0.5;
		} else {
			durationHours = 0;
		}
		System.out.println("duration hours after 30 minutes free parking:" + durationHours);
		
		double price = 0;
		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			price = durationHours * Fare.CAR_RATE_PER_HOUR;
			break;
		}
		case BIKE: {
			price = durationHours * Fare.BIKE_RATE_PER_HOUR;
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		System.out.println("Price before 5% reduction:" + price);
		if(vehicleRegNumberCount > 1) {
			price = price - (price * 0.05);
		}
		
		//ticketDAO = new TicketDAO();
		//System.out.println(ticketDAO.updateTicket(ticket));
		//ticketDAO = new TicketDAO();
		
		ticket.setPrice(price);
	}
	
	/*
	public boolean isReccurentClient(TicketDAO ticketDAO, Ticket ticket) {
		String vehicleRegNumber = ticket.getVehicleRegNumber();
		System.out.println("reg number:" + ticket.getVehicleRegNumber());
		boolean reccurentClient = false;
		//double price = ticket.getPrice();
		try {
			System.out.println(ticketDAO.getCountVehicleRegNumber(vehicleRegNumber));
			
			int numberOfSameVehicleRegNumber = ticketDAO.getCountVehicleRegNumber(vehicleRegNumber);
			System.out.println("number of same vehicles:" + numberOfSameVehicleRegNumber);
			//5% REDUCTION FOR RECURRENT CLIENT
			if(numberOfSameVehicleRegNumber > 1) {
				reccurentClient = true;
			}
		} catch(Exception e) {
			logger.error("Unable to get vehicle reg number count",e);
		}
		return reccurentClient;
		
	}
	*/

}