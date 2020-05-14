package com.parkit.parkingsystem.util;

import java.util.Date;

public class DateUtil {

	public static int getNumberOfDaysInMonth(int monthNumber, int year) {
		int numberOfDaysInMonth = 0;

		
		if (monthNumber == 1) {
			//February
			
			if (((year - 2020) % 4) == 0) {
				//Leap year
				
				numberOfDaysInMonth = 29;
				
			} else {
				numberOfDaysInMonth = 28;			
			}

		} else {
			
			if (monthNumber <= 6) {
				//January -> July
				
				if ((monthNumber % 2) == 0) {
					//January, March, May, July					
					numberOfDaysInMonth = 31;
				} else {
					//April, June
					numberOfDaysInMonth = 30;
				}
			} else {
				//August -> December
				
				if ((monthNumber % 2) == 0) {
					//September, November
					numberOfDaysInMonth = 30;
				} else {
					//August, October, December
					numberOfDaysInMonth = 31;
				}
			}

		}

		return numberOfDaysInMonth;

	}
	
	public static double getDurationHours(Date inDate, Date outDate) {
		System.out.println("in date:" + inDate);
		System.out.println("out date:" + outDate);
		int inYear = inDate.getYear();
		int outYear = outDate.getYear();
		
		int inMonth = inDate.getMonth();
		int outMonth = outDate.getMonth();
		
		int inMonthDay = inDate.getDate();
		int outMonthDay = outDate.getDate();
		
		int inHour = inDate.getHours();
		int outHour = outDate.getHours();
		
		double inMinute = inDate.getMinutes();
		double outMinute = outDate.getMinutes();

		int durationDays = 0;
		double durationHours = 0;
		double durationMinutes = 0;

		if (outYear > inYear) {
			// YEAR CHANGING
			int monthsNumber = 12 - inMonth + outMonth + 1;

			int numberOfDaysInMonth = 0;

			for (int monthNumber = inMonth; monthNumber <= 11; monthNumber++) {
				numberOfDaysInMonth = getNumberOfDaysInMonth(monthNumber, inYear);
				System.out.println("number of days in month " + monthNumber + " : " + numberOfDaysInMonth);
				if (monthNumber == inMonth) {
					durationDays = numberOfDaysInMonth - inMonthDay;
				} else {
					durationDays += numberOfDaysInMonth;

				}

			}
			
			for (int monthNumber = 0; monthNumber <= outMonth; monthNumber++) {
				numberOfDaysInMonth = getNumberOfDaysInMonth(monthNumber, outYear);
				System.out.println("number of days in month " + monthNumber + " : " + numberOfDaysInMonth);
				if (monthNumber == outMonth) {
					durationDays += outMonthDay;
				} else {
					durationDays += numberOfDaysInMonth;

				}

			}
			System.out.println("duration days:" + durationDays);
			durationHours = ((24 * durationDays) - inHour - (inMinute / 60)) + outHour + (outMinute / 60);
			System.out.println("duration hours:" + durationHours);

		} else {
			// SAME YEAR
			if (outMonth > inMonth) {
				// MONTH CHANGING
				int monthsNumber = outMonth - inMonth + 1;
				System.out.println("in month:" + inMonth);
				System.out.println("out month:" + outMonth);
				System.out.println("months number:" + monthsNumber);
				int[] tableau = new int[monthsNumber];
				int i = 0;
				// durationDays = 0;
				for (int monthNumber = inMonth; monthNumber <= outMonth; monthNumber++) {
					tableau[i] = getNumberOfDaysInMonth(monthNumber, inYear);
					System.out.println("number of days in month " + monthNumber + " : " + tableau[i]);
					if (i == 0) {
						durationDays = tableau[i] - inMonthDay;
					} else {
						if (i == (monthsNumber - 1)) {
							durationDays += outMonthDay;
						} else {
							durationDays += tableau[i];
						}

					}

					i++;
				}
				System.out.println("duration days:" + durationDays);

				durationHours = ((24 * durationDays) - inHour - (inMinute / 60)) + outHour + (outMinute / 60);
				System.out.println("duration hours:" + durationHours);

			} else {
				if (outMonthDay > inMonthDay) {
					// DAY CHANGING
					durationDays = outMonthDay - inMonthDay;
					durationHours = ((24 * durationDays) - inHour - (inMinute / 60)) + outHour + (outMinute / 60);

				} else {
					// SAME DAY
					durationHours = outHour - inHour;
					durationMinutes = ((60 * durationHours) - inMinute) + outMinute;
					durationHours = durationMinutes / 60;
				}
			}

		}
		
		System.out.println("duration hours before 30 minutes free parking:" + durationHours);
		return durationHours;
	}
}
