package com.vaadin.samples.crud;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A converter that formats currencies with two decimal places.
 */
public class PriceConverter extends StringToBigDecimalConverter {

    public PriceConverter() {
        super("Cannot convert value to a number");
    }

    @Override
    public Result<BigDecimal> convertToModel(String value,
            ValueContext context) {
        value = value.trim();
        if ("".equals(value)) {
            value = "0";
        }
        return super.convertToModel(value, context);
    }

    @Override
    protected NumberFormat getFormat(Locale locale) {
        // Always display currency with two decimals
        NumberFormat format = super.getFormat(locale);
        if (format instanceof DecimalFormat) {
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
        }
        return format;
    }
}
