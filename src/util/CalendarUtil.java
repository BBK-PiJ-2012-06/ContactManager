package util;

import java.util.Calendar;
import java.util.Comparator;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import main.Meeting;

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
	 * Formats the given date to a simple string of the form "dd/MM/yyyy HH:mm"
	 * (see SimpleDateFormat for pattern letter definitions)
	 * 
	 * @param date the date to be formatted
	 * @return a string representation of the given date
	 **/
	public static String format(Calendar date) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return simpleFormat.format(date.getTime());
	}
	
	/**
	 * Parses the given formatted calendar string of the form "dd/MM/yyyy HH:mm"
	 * (see SimpleDateFormat for pattern letter definitions)
	 * 
	 * @param dateStr the string to be parsed
	 * @return the Calendar represented by the given string
	 */
	public static Calendar parse(String dateStr) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar date = Calendar.getInstance();
		
		try {
			date.setTime(simpleFormat.parse(dateStr));
		} catch (ParseException e) {
			System.out.println("Could not parse date string: " + dateStr);
			System.out.println("format is dd/MM/yyyy HH:mm");
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Returns a comparator used to keep sets of meetings sorted chronologically.
	 * (Written with help from http://java2novice.com/java-collections-and-util/treeset/comparator-object/
	 * and http://docs.oracle.com/javase/6/docs/api/).
	 * 
	 * @return a comparator object for sorting meetings by date
	 */
	public static Comparator<Meeting> getMeetingComparator() {
		return new Comparator<Meeting>() {
			@Override
			public int compare(Meeting o1, Meeting o2) {
				int diff = o1.getDate().compareTo(o2.getDate());
				// Ordering must be consistent with equals: 
				// -- ie., diff should equal 0 iff o1.equals(o2) is true
				// -- so if o1 and o2 occur at exactly the same time, use IDs to compare
				if(diff == 0) {
					return o1.getID() - o2.getID();
				}
				return diff;
			}
		};
	}

	/**
	 * Returns a calendar with the same date (day, month, year) as the given calendar, 
	 * with all time of day fields set to zero (midnight).
	 * 
	 * @param date the date to trim the time from
	 * @return a calendar set to midnight on the same date as the given calendar
	 */
	public static Calendar trimTime(Calendar date) {
		Calendar trimmedDate = Calendar.getInstance();
		trimmedDate.clear();
		trimmedDate.set(Calendar.YEAR, date.get(Calendar.YEAR));
		trimmedDate.set(Calendar.MONTH, date.get(Calendar.MONTH));
		trimmedDate.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		
		trimmedDate.set(Calendar.HOUR_OF_DAY, 0);
		trimmedDate.set(Calendar.MINUTE, 0);
		trimmedDate.set(Calendar.SECOND, 0);
		trimmedDate.set(Calendar.MILLISECOND, 0);
		return trimmedDate;
	}
}