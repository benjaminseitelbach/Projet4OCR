package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class FareCalculatorServiceTest {

	
    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
    	System.out.println("Test start");
        ticket = new Ticket();
    }
    
    @AfterEach
    private void cleanPerTest() {
    	ticket = null;
    	System.out.println("End test");
    }
    
    
    @Test
    public void calculateFareCarTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );//1 hour parking time 
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, 1);
    
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR - (0.5 * Fare.CAR_RATE_PER_HOUR));
    }

    @Test
    public void calculateFareBikeTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );//1 hour parking time
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, 1);

        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR - (0.5 * Fare.BIKE_RATE_PER_HOUR));
    }

    @Test
    public void calculateFareUnkownTypeTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );//1 hour parking time
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket,1));
    }

    
    @Test
    public void calculateFareBikeWithFutureInTimeTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );// 1 hour parking time
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket,1));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTimeTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 1/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, 1);
        assertEquals((0.25 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTimeTest(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 1/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, 1);
        assertEquals( (0.25 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    
    @Test
    public void calculateFareCarWithLessThanHalfAnHourParkingTimeTest() {
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  20 * 60 * 1000) );//20 minutes parking time should give 0 parking fare 
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, 1);
        
        assertEquals( 0 , ticket.getPrice());
    	
    }    
    
    @Test
    public void calculateFareCarWithSeveralDaysParkingTimeTest() {
    	Date inTime = new Date(2020, 1, 1);
    	inTime.setHours(23);
    	Date outTime = new Date(2020, 1, 5);
    	outTime.setHours(3);
    	System.out.println("in:" + inTime);
    	System.out.println("out:" + outTime);
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
    	
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	fareCalculatorService.calculateFare(ticket, 1);
    	assertEquals( (3 * 24 * Fare.CAR_RATE_PER_HOUR) + (4 * Fare.CAR_RATE_PER_HOUR) - (0.5 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());  
    }
    
    @Test
    public void calculateFareCarWhenSeveralMonthsWithFebruaryMonthAndChangingHourAndChanginMinutesTest() {
    	Date inTime = new Date(2020, 0, 30);
    	inTime.setHours(23);
    	Date outTime = new Date(2020, 2, 25);
    	outTime.setHours(21);
    	outTime.setMinutes(30);
    	System.out.println("in:" + inTime);
    	System.out.println("out:" + outTime);
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
    	
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	fareCalculatorService.calculateFare(ticket,1);
    	assertEquals( (60 * 24 * Fare.CAR_RATE_PER_HOUR) - (5 * 24 * Fare.CAR_RATE_PER_HOUR) - (1.5 * Fare.CAR_RATE_PER_HOUR )- (0.5 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());   	
    }
    
    
    @Test
    public void calculateFareCarWhenChangingYearSeveralMonthWithInMonthInferiorOutMonthAndInMonthDayInferiorOutMonthDayTest() {
    	Date inTime = new Date(2020, 3, 29);
    	inTime.setHours(23);
    	Date outTime = new Date(2021, 4, 30);
    	outTime.setHours(23);
    	outTime.setMinutes(0);
    	System.out.println("in:" + inTime);
    	System.out.println("out:" + outTime);
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
    	
    	ticket.setInTime(inTime); 
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	fareCalculatorService.calculateFare(ticket, 1);
    	assertEquals( (396 * 24 * Fare.CAR_RATE_PER_HOUR) - (0.5 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());   	
    }
    
}
