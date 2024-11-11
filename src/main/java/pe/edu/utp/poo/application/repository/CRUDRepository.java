/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.db.annotation.ColumnName;

/**
 *
 * @author manuelguarniz
 */
public class CRUDRepository<T> {
    protected List<T> binding(List<Map<String, Object>> data, Class klass) {
        List<Object> outData = new ArrayList<>();
        
        for (Map map : data) {
            try {
                Object instance = klass.getDeclaredConstructor().newInstance();
                Method[] methods = klass.getDeclaredMethods();

                for (Method method : methods) {
                    method.setAccessible(true);
                    if (method.getName().startsWith("set")
                            && method.getParameterTypes().length == 1) {

                        try {
                            String firstChar = method.getName().substring(3, 4);
                            String cutFieldName = method.getName().substring(4);
                            String fieldName = firstChar.toLowerCase() + cutFieldName;
                            Class paramType = method.getParameterTypes()[0];

                            Field field = klass.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            if (field.isAnnotationPresent(ColumnName.class)) {
                                ColumnName annotation = field.getAnnotation(ColumnName.class);
                                Object value = map.get(annotation.value());

                                switch (paramType.getName()) {
                                    case "java.time.LocalDateTime" -> {
                                        LocalDateTime valueParsed = Util.parseDatetime(String.valueOf(value));
                                        method.invoke(instance, valueParsed);
                                    }
                                    case "java.lang.LocalDate" -> {
                                        LocalDate valueParsed = Util.parseDate(String.valueOf(value));
                                        method.invoke(instance, valueParsed);
                                    }
                                    case "java.lang.Double" -> {
                                        Double valueParsed = Double.valueOf(String.valueOf(value));
                                        method.invoke(instance, valueParsed);
                                    }
                                    case "java.lang.Integer" -> {
                                        Integer valueParsed = Integer.valueOf(String.valueOf(value));
                                        method.invoke(instance, valueParsed);
                                    }
                                    case "java.lang.String" -> {
                                        String valueParsed = String.valueOf(value);
                                        method.invoke(instance, valueParsed);
                                    }
                                    default -> {
                                        method.invoke(instance, value);
                                    }
                                }
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
                outData.add(instance);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return (List<T>) outData;
    }
}
