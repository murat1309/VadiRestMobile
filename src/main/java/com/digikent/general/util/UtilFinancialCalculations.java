package com.digikent.general.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class UtilFinancialCalculations {

    private static final Locale TURKISH_LOCALE = new Locale("tr", "TR");

    public static String getMoneyAmountInTurkishCurrencyFormat(BigDecimal moneyAmount) {
        Locale turkishLocale = getTurkishLocale();
        return NumberFormat.getCurrencyInstance(turkishLocale).format(moneyAmount);
    }

    public static Locale getTurkishLocale() {
        return TURKISH_LOCALE;
    }
}
