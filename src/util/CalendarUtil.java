package util;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A class to handle some common Calendar checking/manipulating methods.
 **/
public class CalendarUtil {

	/**
	 * Checks whether the given date is in the past.
	 * 
	 * @param date the date to check
	 * @return whether the date is in the past (true) or not (false)
	 **/
	public static boolean isInPast(Calendar date) {
		Calendar now = Calendar.getInstance();
		if (date.before(now)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether the given date is in the future.
	 * 
	 * @param date the date to check
	 * @return whether the date is in the future (true) or not (false)
	 **/
	public static boolean isInFuture(Calendar date) {
		Calendar now = Calendar.getInstance();
		if (date.after(now)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Formats the given date to a simple string of the form "dd/MM/yy HH:mm"
	 * (see SimpleDateFormat for pattern letter definitions)
	 * 
	 * @param date the date to be formatted
	 * @return a string representation of the given date
	 **/
	public static String format(Calendar date) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		return simpleFormat.format(date.getTime());
	}
	
	/**
	 * Parses the given formatted calendar string of the form "dd/MM/yy HH:mm"
	 * (see SimpleDateFormat for pattern letter definitions)
	 * 
	 * @param dateStr the string to be parsed
	 * @return the Calendar represented by the given string
	 */
	public static Calendar parse(String dateStr) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		Calendar date = Calendar.getInstance();
		
		try {
			date.setTime(simpleFormat.parse(dateStr));
		} catch (ParseException e) {
			System.out.println("Could not parse date string: " + dateStr);
			System.out.println("format is dd/MM/yy HH:mm");
			e.printStackTrace();
		}
		return date;
	}
}