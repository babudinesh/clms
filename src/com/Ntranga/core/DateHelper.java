package com.Ntranga.core;

import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateHelper {
	
	public static String DATEFORMAT = "dd-MM-yyyy";
	public static String SQL_DATEFORMAT = "yyyy-MM-dd";
	private static String TIMEFORMAT12HOUR = "hh:mm:ss a";
	private static String TIMEFORMAT24HOUR = "hh:mm:ss k";
	public static String SQL_DATETIMEFORMAT = "yyyy-MM-dd hh:mm:ss";
	
	private static Logger logger = Logger.getLogger(DateHelper.class);

	/*
	 * This method is used to convert string to java.sql Timestamp format(punchTimeStamp ).
	 */
	public static java.sql.Time getConvertedTime(String timeInput)
	{
		logger.info("Entered into getConvertedTime() - param: " + timeInput);
			//java.util.Date udate=dateTimeInput;
			//java.sql.Timestamp createdOn =new java.sql.Timestamp(udate.getTime());
		Time timeStamp = java.sql.Time.valueOf(timeInput);
		logger.info("TimeStamp :  "+timeStamp);
		logger.info("Exiting from method: getConvertedTime().");
	    return timeStamp;
			
	}
	
	public static java.util.Date convertStringToDate(String stringDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date utilDate = new java.util.Date();
		if (stringDate != null)
			try {
				utilDate = sdf.parse(stringDate);
			} catch (ParseException e) {
				logger.error("Parser Exception .................", e);
				return null;
			}
		return utilDate;
	}
	
	public static void main(String args[]){
		DateHelper dateHelper = new DateHelper();
		int s = dateHelper.compareDateWithCurrentDate(DateHelper.convertDate(convertStringToDateWithFormat("2016-12-19", SQL_DATEFORMAT), SQL_DATEFORMAT));
		System.out.println(s+"::s");
	}
	
	public static int compareDateWithCurrentDate(java.util.Date date1){
		return DateHelper.convertDate(date1, SQL_DATEFORMAT).compareTo(DateHelper.convertDate(new Date(), SQL_DATEFORMAT));
	}

	public static String nullSubstitute(Date date, String substitute) {
		return date == null ? substitute : date.toString();
	}

	public static java.util.Date convertDate(Date date, String format) {
		System.out.println(convertDateToString(date, format));
		return convertStringToDate(convertDateToString(date, format), format);
	}

	public static java.util.Date convertDate(Date date) {
		return convertStringToDate(convertDateToString(date));
	}

	/*
	 * public static java.util.Date convertDate(Date date, String format) throws
	 * ParseException { SimpleDateFormat sdf = new SimpleDateFormat(format);
	 * String dateString = ""; if (date == null) return null; dateString =
	 * sdf.format(date); java.util.Date utilDate = new java.util.Date(); try {
	 * utilDate = sdf.parse(dateString); } catch (ParseException ex) { throw ex;
	 * } return utilDate; }
	 */

	public static java.sql.Date convertDateToSQLDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		}
		return null;

	}

	public static java.util.Date convertStringToDate(String stringDate) {
		return convertStringToDate(stringDate, DATEFORMAT);
	}

	public static java.util.Date convertStringToDateWithFormat(String stringDate, String format) {
		return convertStringToDate(stringDate, format);
	}
	
	public static Calendar convertStringToCalendar(String stringDate) {
		Calendar cal = Calendar.getInstance();
		if (stringDate != null) {
			cal.setTime(convertStringToDate(stringDate, DATEFORMAT));
			return cal;
		}
		return null;
	}

	public static Calendar convertStringToCalendar(String stringDate, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertStringToDate(stringDate, format));
		return cal;
	}

	public static java.sql.Date convertStringToSQLDate(String stringDate, String format) {
		java.util.Date utilDate = convertStringToDate(stringDate, format);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	public static java.sql.Date isValidSQLDate(String stringDate, String format) {
		java.util.Date utilDate = convertStringToDate(stringDate, format);
		try {
			return new java.sql.Date(utilDate.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static java.sql.Date convertStringToSQLDate(String stringDate) {
		return convertStringToSQLDate(stringDate, SQL_DATEFORMAT);
	}

	public static java.sql.Date convertStringToSQLDateTime(String stringDate, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = convertStringToDate(stringDate, DATEFORMAT);
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return new java.sql.Date(cal.getTime().getTime());
	}

	public static String convertSQLDateToString(java.sql.Date date, String format) {
		if (date == null)
			return "";
		java.util.Date tempDate = new java.util.Date();
		tempDate.setTime(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateString = "";
		dateString = sdf.format(tempDate);
		return dateString;
	}
	
	public static String convertDateToSQLString(java.util.Date date) {
		if (date == null)
			return "";
		java.util.Date tempDate = new java.util.Date();
		tempDate.setTime(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATEFORMAT);
		String dateString = "";
		dateString = sdf.format(tempDate);
		return dateString;
	}
	
	public static String convertDateTimeToSQLString(java.util.Date date) {
		if (date == null)
			return "";
		java.util.Date tempDate = new java.util.Date();
		tempDate.setTime(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATETIMEFORMAT);
		String dateString = "";
		dateString = sdf.format(tempDate);
		return dateString;
	}
	

	public static Date convertSQLDateToDate(java.sql.Date date) {
		if (date != null)
			return convertStringToDate(convertSQLDateToString(date, DATEFORMAT));
		return null;
	}

	public static String convertDateToString(Date date) {
		return convertDateToString(date, DATEFORMAT);
	}

	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateString = "";
		if (date == null)
			return "";
		dateString = sdf.format(date);
		// logger.info("------------------:"+dateString);
		return dateString;
	}

	public static String convertNullSQLDateToString(java.sql.Date date) {
		if (date == null)
			return "";
		return convertSQLDateToString(date, DATEFORMAT);
	}

	public static String convertSQLDateToString(java.sql.Date date) {
		if (date == null)
			return "";
		return convertSQLDateToString(date, DATEFORMAT);
	}

	public static String convertCalendarToString(Calendar cal, String format) {
		if (cal == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	public static java.sql.Date convertCalendarToSQLDate(Calendar cal) {
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		return date;
	}

	public static String convertDayToFullName(int day) {
		switch (day % 7) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";

		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		default:
			return "";
		}
	}

	public static String convertMonthToFullName(int month) {
		switch (month % 12) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";
		default:
			return "";
		}
	}

	public static java.sql.Date checkAndConvertStringToSQLDate(String stringDate) {
		return checkAndConvertStringToSQLDate(stringDate, DateHelper.DATEFORMAT);
	}

	public static java.sql.Date checkAndConvertStringToSQLDate(String stringDate, String format) {
		if (stringDate != null) {
			if (stringDate.length() > 0) {
				java.util.Date utilDate = convertStringToDate(stringDate, format);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				return sqlDate;
			}
		}
		return null;
	}

	public static java.sql.Date normaliseToSQLDate(String stringDate) {
		if(StringHelper.isBlank(stringDate))
			return null;
		if(stringDate == null)
			return null;
		return convertStringToSQLDate(stringDate, DATEFORMAT);
	}

	public static int differenceBetweenDates(Date startDate, Date endDate) {
		long diff = startDate.getTime() - endDate.getTime();
		int days = (int) (diff / (24 * 1000 * 60 * 60));
		return Math.abs(days);
	}

	public static List<Date> getDatesBetweenDates(Date startdate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		try {
			do {
				dateList.add(startdate);
				Calendar c = Calendar.getInstance();
				c.setTime(startdate);
				c.add(Calendar.DATE, 1);
				startdate = c.getTime();
			} while (!startdate.after(endDate));
		} catch (Exception e) {
			logger.error("Exception .................", e);
		}
		return dateList;

	}
	
	public static int daysInMonth(int month,int year){
		Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static String getTitle(int month,int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.YEAR, year);
		return DateHelper.convertCalendarToString(cal, "MMM-yyyy");
	}
 
	
	public static Map<Integer, String> getMonths(){
		Map<Integer, String> months = new LinkedHashMap<Integer, String>();
		String[] month = new DateFormatSymbols().getMonths();
		for (int i = 0; i < month.length - 1; i++) {
				months.put(i + 1 , month[i]);
		}
		return months;
	}

	public static java.sql.Date convertStringToSQLDateIfNotreturnNull(String stringDate) {

		try {
			return convertStringToSQLDate(stringDate, DATEFORMAT);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getTwelveHoursTimeFormat(Date date) {

		try {
			return new SimpleDateFormat(TIMEFORMAT12HOUR).format(date); // 9:00
		} catch (Exception e) {
		}
		return null;
	}

	public static String getTwentyFourHoursTimeFormat(Date date) {

		try {
			return new SimpleDateFormat(TIMEFORMAT24HOUR).format(date); // 9:00
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getDatesForRange(DateRange dateRange){
		String dates = "";
		SimpleDateFormat fmt = new SimpleDateFormat(DATEFORMAT);
		Calendar c = Calendar.getInstance();
		Date date =  new Date();
		fmt.format(date);
		if(dateRange==DateRange.TODAY){
			dates = new Date().toString();
		}if(dateRange==DateRange.WEEK){
		    c.add(Calendar.DATE,- 7);
		}
		if(dateRange == DateRange.MONTH){
			c.add(Calendar.MONTH,- 1);
		}if(dateRange == DateRange.QUARTERLY){
			c.add(Calendar.MONTH,- 3);
		}if(dateRange== DateRange.HALFYEARLY){
			c.add(Calendar.MONTH,- 6);
		}if(dateRange == DateRange.YEARLY){
			c.add(Calendar.YEAR,- 1);
		}
		dates=convertDateToString(c.getTime(),DATEFORMAT);
		dates+="_"+convertDateToString(date,DATEFORMAT);
		return dates;
	}
	
	public static Date getDateObject(String date, String month, String year) {
        try {
              Calendar cal = Calendar.getInstance();
              cal.set(NumberHelper.integerValue(year),NumberHelper.integerValue(month), NumberHelper.integerValue(date));
              return cal.getTime();
        } catch (Exception e) {
			logger.error("Exception .................", e);
              return null;
        }
  }   
	
	public static int getMonth(String monthCode){
		String month = monthCode.toUpperCase();
		//System.out.println(month);
		int monthNum = 0;
		switch(month){
			case "JAN"  :  monthNum = 1;
							break;
			case "FEB"  :  monthNum = 2;
							break;
			case "MAR"  :  monthNum = 3;
							break;
			case "APR"  :  monthNum = 4;
							break;
			case "MAY"  :  monthNum = 5;
							break;
			case "JUN"  :  monthNum = 6;
							break;
			case "JUL"  :  monthNum = 7;
							break;
			case "AUG"  :  monthNum = 8;
							break;
			case "SEP"  :  monthNum = 9;
							break;
			case "OCT"  :  monthNum = 10;
							break;
			case "NOV"  :  monthNum = 11;
							break;
			case "DEC"  :  monthNum = 12;
							break;
			default     :  monthNum = 0;
		}
		//System.out.println(monthNum);
		return monthNum;
	}

}