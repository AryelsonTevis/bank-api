package api.bank.bankapi.service;

public class PhoneFormat {
    public static String formatPhone(String tel) {
        if (tel.length() == 11) {
            return tel.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        return tel.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }
}
