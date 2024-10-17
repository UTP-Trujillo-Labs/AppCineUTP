/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import static pe.edu.utp.poo.application.common.Constant.DEFAULT_LOCALDATETIME_FORMAT;

/**
 *
 * @author manuelguarniz
 */
public class Util {
    public static String parseDate(LocalDateTime datetime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DEFAULT_LOCALDATETIME_FORMAT);
        return format.format(datetime);
    }
    public static String parseDate(LocalDateTime datetime, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return format.format(datetime);
    }
    public static String getID() {
        return UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }
}
