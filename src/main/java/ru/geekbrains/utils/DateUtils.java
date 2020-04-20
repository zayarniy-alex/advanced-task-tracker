package ru.geekbrains.utils;


import java.time.*;
import java.util.Date;


public class DateUtils
{

  public static ZoneId systemTimezone = ZoneId.systemDefault();


  public static LocalDate convertToLocalDate(Date date)
  {
	return date.toInstant()
			   .atZone(systemTimezone)
			   .toLocalDate();
  }


  public static LocalDateTime convertToLocalDateTime(Date date)
  {
	return date.toInstant()
			   .atZone(systemTimezone)
			   .toLocalDateTime();
  }


  public static Date convertToDate(LocalDate date)
  {
	Instant inst = date.atStartOfDay()
					   .atZone(systemTimezone)
					   .toInstant();

	return Date.from(inst);
  }


  public static Date convertToDate(LocalDateTime dateTime)
  {
	Instant inst = dateTime.atZone(systemTimezone).toInstant();
	return Date.from(inst);
  }

}