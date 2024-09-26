import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        // Exibe uma caixa de diálogo de confirmação com as opções Sim e Não
        int resposta = JOptionPane.showConfirmDialog(null, "Vamos criar sua conta bancária?", "Confirmação", JOptionPane.YES_NO_OPTION);

        // Verifica a resposta do usuário
        if (resposta == JOptionPane.YES_OPTION) {
            String nomeCliente = "";
            while (true) {
                // Solicita o nome do cliente
                nomeCliente = JOptionPane.showInputDialog(null, "Por favor, insira seu nome:");

                // Verifica se o nome contém apenas letras e tem no máximo 35 caracteres
                if (nomeCliente.matches("[a-zA-Z\\s]+") && nomeCliente.length() <= 35) {
                    nomeCliente = capitalizarNome(nomeCliente);  // Capitaliza o nome
                    break;  // Nome válido, sai do loop
                } else {
                    JOptionPane.showMessageDialog(null, "Nome inválido. Por favor, insira um nome com até 35 caracteres, contendo apenas letras.");
                }
            }

            int escolhaBanco;
            while (true) {
                // Escolha do banco
                String banco = JOptionPane.showInputDialog(null, 
                    "Escolha o banco:" +
                    "\nDigite 1 para Banco Laranja" +
                    "\nDigite 2 para Banco Roxo" +
                    "\nDigite 3 para Banco Azul");

                escolhaBanco = Integer.parseInt(banco);  // Converte a escolha para um número inteiro

                // Verifica a escolha do banco
                if (escolhaBanco == 1 || escolhaBanco == 2 || escolhaBanco == 3) {
                    break;  // Se a escolha for válida, sai do loop
                } else {
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha um banco válido.");
                }
            }

            // Gera a conta bancária após a escolha válida do banco
            criarContaBancaria(nomeCliente);

        } else if (resposta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Obrigado!");
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }

    // Método para capitalizar o nome do cliente (primeira letra maiúscula em cada palavra)
    private static String capitalizarNome(String nome) {
        String[] palavras = nome.toLowerCase().split(" ");
        StringBuilder nomeCapitalizado = new StringBuilder();

        for (String palavra : palavras) {
            if (palavra.length() > 0) {
                nomeCapitalizado.append(Character.toUpperCase(palavra.charAt(0)))
                                .append(palavra.substring(1))
                                .append(" ");
            }
        }
        return nomeCapitalizado.toString().trim();
    }

    // Método para gerar uma conta bancária
    private static void criarContaBancaria(String nomeCliente) {
        // Gera número de agência e número de conta
        String numeroAgencia = gerarNumeroAgencia();
        String numeroConta = gerarNumeroConta();

        // Solicita ao usuário o tipo de conta
        String[] tiposConta = {"Conta Corrente", "Conta Poupança", "Conta Salário"};
        String tipoConta = (String) JOptionPane.showInputDialog(null, 
            "Escolha o tipo de conta:", 
            "Tipo de Conta", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            tiposConta, 
            tiposConta[0]);

        // Solicita o saldo inicial
        String saldoInicialStr = JOptionPane.showInputDialog(null, "Insira o saldo inicial da conta:");
        double saldo = Double.parseDouble(saldoInicialStr);  // Converte o saldo inicial para double

        // Formata o saldo para duas casas decimais
        DecimalFormat df = new DecimalFormat("0.00");
        
        if (tipoConta != null) {
            // Exibe os detalhes da conta
            String mensagem = "Conta criada com sucesso!" +
                              "\nNome do Cliente: " + nomeCliente +
                              "\nNúmero da Agência: " + numeroAgencia +
                              "\nNúmero da Conta: " + numeroConta +
                              "\nTipo de Conta: " + tipoConta +
                              "\nSaldo Inicial: R$ " + df.format(saldo);
            JOptionPane.showMessageDialog(null, mensagem);

            // Loop para opções de saque e depósito
            while (true) {
                String[] opcoes = {"Depósito", "Saque", "Sair"};
                String opcao = (String) JOptionPane.showInputDialog(null, 
                    "Escolha uma operação:", 
                    "Operação Bancária", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opcoes, 
                    opcoes[0]);

                if (opcao.equals("Depósito")) {
                    String depositoStr = JOptionPane.showInputDialog(null, "Insira o valor do depósito:");
                    double deposito = Double.parseDouble(depositoStr);
                    saldo += deposito;
                    JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso! \n Saldo atual: R$ " + df.format(saldo));
                } else if (opcao.equals("Saque")) {
                    String saqueStr = JOptionPane.showInputDialog(null, "Insira o valor do saque:");
                    double saque = Double.parseDouble(saqueStr);
                    if (saque <= saldo) {
                        saldo -= saque;
                        JOptionPane.showMessageDialog(null, "Saque realizado com sucesso! Saldo atual: R$ " + df.format(saldo));
                    } else {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente! Saldo atual: R$ " + df.format(saldo));
                    }
                } else if (opcao.equals("Sair")) {
                    JOptionPane.showMessageDialog(null, "Obrigado por utilizar os nossos serviços!");
                    break;
                }

                // Verifica se o saldo é zero e encerra a operação
                if (saldo == 0) {
                    JOptionPane.showMessageDialog(null, "Operação encerrada.");
                    break;
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
        }
    }

    // Método para gerar número da agência (4 dígitos)
    private static String gerarNumeroAgencia() {
        Random random = new Random();
        int numero = random.nextInt(9000) + 1000;  // Gera um número entre 1000 e 9999
        return String.valueOf(numero);
    }

    // Método para gerar número da conta (6 dígitos)
    private static String gerarNumeroConta() {
        Random random = new Random();
        int numero = random.nextInt(900000) + 100000;  // Gera um número entre 100000 e 999999
        return String.valueOf(numero);
    }
}
