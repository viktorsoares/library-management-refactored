package com.example.library.infrastructure.util;

public class ValueFormatter {

    public static double safeDouble(Double value) {
        return value != null ? value : 0.0;
    }

    public static String formatCurrency(Double value) {
        return String.format("R$ %.2f", safeDouble(value));
    }
}
