package service;

import model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FuncionarioService {

    private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat formatterNumero;

    static {
        formatterNumero = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatterNumero.setMinimumFractionDigits(2);
        formatterNumero.setMaximumFractionDigits(2);
    }

    public static void removerFuncionarioPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    public static void aplicarAumento(List<Funcionario> funcionarios) {
        BigDecimal aumento = new BigDecimal("1.10");

        for (Funcionario f : funcionarios) {
            f.setSalario(
                f.getSalario()
                 .multiply(aumento)
                 .setScale(2, RoundingMode.HALF_UP)
            );
        }
    }
}