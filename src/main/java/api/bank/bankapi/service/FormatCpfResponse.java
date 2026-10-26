package api.bank.bankapi.service;

import static api.bank.bankapi.service.CpfFormat.formatCpf;

public class FormatCpfResponse {
    protected static String formatCpfResponse(String cpf) {

        return formatCpf(cpf).replaceFirst("\\d{3}", "***")
                .replaceAll("-\\d{2}", "-**");
    }
}
