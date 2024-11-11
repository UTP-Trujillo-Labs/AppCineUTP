/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import static pe.edu.utp.poo.application.common.Constant.DEFAULT_LOCALDATETIME_FORMAT;
import static pe.edu.utp.poo.application.common.Constant.SHORT_LOCALDATETIME_FORMAT;

/**
 *
 * @author manuelguarniz
 */
public class Util {
    public static String dateToString(LocalDateTime datetime, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return format.format(datetime);
    }
    public static String dateToString(LocalDateTime datetime) {
        return dateToString(datetime, DEFAULT_LOCALDATETIME_FORMAT);
    }
    
    public static LocalDate parseDate(String date, String pattern) {
        date = date.substring(0, 10);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, format);
    }
    public static LocalDate parseDate(String date) {
        return parseDate(date, SHORT_LOCALDATETIME_FORMAT);
    }
    
    public static LocalDateTime parseDatetime(String datetime, String pattern) {
        datetime = datetime.substring(0, 19);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(datetime, format);
    }
    public static LocalDateTime parseDatetime(String datetime) {
        return parseDatetime(datetime, DEFAULT_LOCALDATETIME_FORMAT);
    }
    
    public static Date localDateTimeToDate(LocalDateTime datetime) {
        return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
    }
  
    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static String generateShortUUID() {
        return UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }
    
    public static String generateUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }
    
    public static boolean isNullOrEmpty(String value) {
        return value == null || "".equalsIgnoreCase(value.trim());
    }
}
