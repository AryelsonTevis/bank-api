package api.bank.bankapi.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalMoneyResponse {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    protected static String formatDecimal(BigDecimal decimal) {

       return "R$ "+df.format(decimal);
    }
}
