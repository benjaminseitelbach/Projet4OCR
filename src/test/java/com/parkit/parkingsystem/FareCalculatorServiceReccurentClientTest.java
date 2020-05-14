package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceReccurentClientTest {

	private static FareCalculatorService fareCalculatorService;
	
	@Mock
	private static TicketDAO ticketDAO;

	/*
	@Test
	public void calculaterFareReccurentClientTest() {
		Ticket ticket = new Ticket();
		String vehicleRegNumber = "ABCDEF";
		//when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
		//when(ticketDAO.getVehicleRegNumberCount(any(String.class))).thenReturn(2);
		
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));// 1 hour parking time
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		//int vehicleRegNumberCount = ticketDAO.getVehicleRegNumberCount(vehicleRegNumber);
		fareCalculatorService.calculateFare(ticket, 2);
		//fareCalculatorService.calculateFareReccurentClient(ticketDAO, ticket);
		double expectedPrice = Fare.CAR_RATE_PER_HOUR - (0.5 * Fare.CAR_RATE_PER_HOUR); 
		expectedPrice = expectedPrice - (0.05 * expectedPrice);
		System.out.println(expectedPrice);
		assertEquals(ticket.getPrice(), expectedPrice);

	}
	*/
}
