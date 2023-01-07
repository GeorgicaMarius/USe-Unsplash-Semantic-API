package org.example.converters;

import com.opencsv.bean.AbstractBeanField;

public class CustomSuggestedByUserConverter extends AbstractBeanField {
    @Override
    protected Object convert(String s) {
        return s.equals("t");
    }
}