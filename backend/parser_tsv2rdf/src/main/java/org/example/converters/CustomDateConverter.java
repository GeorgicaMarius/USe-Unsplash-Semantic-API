package org.example.converters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomDateConverter extends AbstractBeanField {
    @Override
    protected Object convert(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss[.[SSSSSSSSS][SSSSSSSS][SSSSSSS][SSSSSS][SSSSS][SSSS][SSS][SS][S]]"
        );
        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return Date.from(ldt.toInstant(ZoneOffset.UTC));
    }
}