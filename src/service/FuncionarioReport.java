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

public class FuncionarioReport {

    private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat formatterNumero;

    static {
        formatterNumero = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatterNumero.setMinimumFractionDigits(2);
        formatterNumero.setMaximumFractionDigits(2);
    }

    public static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("\n===== LISTA DE FUNCIONARIOS =====");

        for (Funcionario f : funcionarios) {
            imprimirFuncionario(f);
        }
    }

    public static void imprimirAgrupados(List<Funcionario> funcionarios) {
        System.out.println("\n===== FUNCIONARIOS AGRUPADOS POR FUNCAO =====");

        Map<String, List<Funcionario>> map =
            funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        for (Map.Entry<String, List<Funcionario>> entry : map.entrySet()) {

            System.out.println("Funcao: " + entry.getKey());

            for (Funcionario f : entry.getValue()) {
                imprimirFuncionarioSimples(f);
            }

            System.out.println("==================================");
        }
    }

    public static void imprimirAniversariantes(List<Funcionario> funcionarios) {
        System.out.println("\n===== ANIVERSARIANTES (OUTUBRO E DEZEMBRO) =====");

        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();

            if (mes == 10 || mes == 12) {
                imprimirFuncionario(f);
            }
        }
    }

    public static void imprimirMaisVelho(List<Funcionario> funcionarios) {
        System.out.println("\n===== FUNCIONARIO MAIS VELHO =====");

        Funcionario maisVelho = funcionarios.get(0);

        for (Funcionario f : funcionarios) {
            if (f.getDataNascimento().isBefore(maisVelho.getDataNascimento())) {
                maisVelho = f;
            }
        }

        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();

        System.out.println("Nome: " + maisVelho.getNome());
        System.out.println("Idade: " + idade);
    }

    public static void imprimirOrdenado(List<Funcionario> funcionarios) {
        System.out.println("\n===== FUNCIONARIOS EM ORDEM ALFABETICA =====");

        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        for (Funcionario f : funcionarios) {
            imprimirFuncionario(f);
        }
    }

    public static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        System.out.println("\n===== TOTAL DOS SALARIOS =====");

        BigDecimal total = BigDecimal.ZERO;

        for (Funcionario f : funcionarios) {
            total = total.add(f.getSalario());
        }

        System.out.println("Total: " + formatterNumero.format(total));
    }

    public static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        System.out.println("\n===== SALARIOS MINIMOS POR FUNCIONARIO =====");

        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        for (Funcionario f : funcionarios) {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);

            System.out.println("Nome: " + f.getNome());
            System.out.println("Salarios Minimos: " + formatterNumero.format(qtd));
            System.out.println("----------------------------------");
        }
    }

    private static void imprimirFuncionario(Funcionario f) {
        String data = f.getDataNascimento().format(formatterData);
        String salario = formatterNumero.format(f.getSalario());

        System.out.println("Nome: " + f.getNome());
        System.out.println("Data Nascimento: " + data);
        System.out.println("Salario: " + salario);
        System.out.println("Funcao: " + f.getFuncao());
        System.out.println("----------------------------------");
    }

    private static void imprimirFuncionarioSimples(Funcionario f) {
        String data = f.getDataNascimento().format(formatterData);
        String salario = formatterNumero.format(f.getSalario());

        System.out.println("Nome: " + f.getNome());
        System.out.println("Data: " + data);
        System.out.println("Salario: " + salario);
        System.out.println("----------------------");
    }
}