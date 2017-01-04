package com.thetonrifles.stackoverflow.util;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

	public static Date buildDate(@IntRange(from = 1, to = 31) int dayOfMonth) {
		return buildDate(dayOfMonth, getCurrentMonth());
	}

	private static Date buildDate(
			@IntRange(from = 1, to = 31) int dayOfMonth,
			@IntRange(from = 0, to = 11) int month) {
		return buildDate(dayOfMonth, month, getCurrentYear());
	}

	private static Date buildDate(
			@IntRange(from = 1, to = 31) int dayOfMonth,
			@IntRange(from = 0, to = 11) int month,
			@IntRange(from = 0) int year) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String formatDate(@NonNull Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
		return df.format(date);
	}

	@IntRange(from = 0, to = 11)
	private static int getCurrentMonth() {
		Date now = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(now);
		return calendar.get(Calendar.MONTH);
	}

	@IntRange(from = 0)
	private static int getCurrentYear() {
		Date now = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(now);
		return calendar.get(Calendar.YEAR);
	}

}
