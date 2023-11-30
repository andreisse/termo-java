import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDePalavras {

    //  arquivo txt
    private static final String PALAVRAS_FILE_PATH = "palavras.txt";

    // definindo o limite máximo de tentativas para adivinhar a palavra
    private static final int MAX_TENTATIVAS = 5;

    // metodo principal para iniciar o jogo
    public static void main(String[] args) {
        jogar();
    }

    // metodo para controlar o fluxo de jogo
    private static void jogar() {
        limparConsole();
        String palavraSecreta = escolherPalavra();
        int tamanhoPalavra = palavraSecreta.length();
        Scanner scanner = new Scanner(System.in);
        String palpite;
        int tentativas = 0;
        int espaco = 0;

        // ver a palavra secreta no início do jogo
        System.out.println("Palavra secreta da rodada: " + palavraSecreta);

        System.out.println("Tente descobrir qual é a palavra secreta");
        System.out.println("Você tem " + MAX_TENTATIVAS + " chances de acertar a palavra da rodada, que contém " + tamanhoPalavra + " letras.");

        // principal loop do jogo, que vai até o usuário esgotar as tentativas ou acertar a palavra
        while (tentativas < MAX_TENTATIVAS) {
            System.out.println();
            System.out.print("Digite para tentar adivinha a palavra:");
            palpite = scanner.nextLine().toLowerCase();

            // verificando se a palavra da tentativa tem o tamanho certo
            if (palpite.length() != tamanhoPalavra) {
                System.out.println("A palavra que você digitou não é válida, somente palavras com " + tamanhoPalavra + " letras. Tente novamente.");
                continue;
            }

            tentativas++; // aumenta a quantidade de tentativas

            if (palpite.equals(palavraSecreta)) {
                System.out.println("Parabéns!!!! Você acertouuu!!!!!");
                scanner.close();
                return;
            } else {
                fornecerDica(palavraSecreta, palpite, tamanhoPalavra);
            }

            // mostra ao jogador o número de tentativas restantes
            System.out.println("Tentativas restantes: " + (MAX_TENTATIVAS - tentativas));
        }

        // mensagem quando o usuário esgota todas as tentativas
        System.out.println("Suas tentativas acabaram! A palavra era: " + palavraSecreta);
        scanner.close();
    }

    // metodo para escolher uma palavra aleatória
    private static String escolherPalavra() {
            ArrayList<String> palavras = lerPalavrasDoArquivo();
            Random random = new Random();
            return palavras.get(random.nextInt(palavras.size()));
        }

        private static ArrayList<String> lerPalavrasDoArquivo() {
            ArrayList<String> palavras = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(PALAVRAS_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    palavras.add(line.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return palavras;
        }


    // metodo para fornecer dica ao jogador com base no palpite.
    private static void fornecerDica(String palavraSecreta, String palpite, int tamanhoPalavra) {
        StringBuilder palavraMisteriosa = new StringBuilder("-".repeat(tamanhoPalavra));
        StringBuilder letrasCorretasPosicaoCorreta = new StringBuilder();
        StringBuilder letrasCorretasPosicaoErrada = new StringBuilder();

        for (int i = 0; i < tamanhoPalavra; i++) {
            char letraPalpite = palpite.charAt(i);
            if (letraPalpite == palavraSecreta.charAt(i)) {
                palavraMisteriosa.setCharAt(i, letraPalpite);
                letrasCorretasPosicaoCorreta.append(letraPalpite);
                if (i < tamanhoPalavra - 1) {
                    letrasCorretasPosicaoCorreta.append(",");
                }
            } else if (palavraSecreta.indexOf(letraPalpite) != -1 && letraPalpite != palpite.charAt(palavraSecreta.indexOf(letraPalpite))) {
                letrasCorretasPosicaoErrada.append(letraPalpite);
                if (i < tamanhoPalavra - 1) {
                    letrasCorretasPosicaoErrada.append(",");
                }
            }
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("Letra na posição correta: " + (letrasCorretasPosicaoCorreta.length() > 0 ? letrasCorretasPosicaoCorreta.toString() : "Nenhuma."));
        System.out.println("Letra que contém na palavra mas que está na posição incorreta: " + (letrasCorretasPosicaoErrada.length() > 0 ? letrasCorretasPosicaoErrada.toString() : "Nenhuma."));
        System.out.println("Palavra secreta: \"" + palavraMisteriosa + "\"");
        System.out.println("----------------------------------------------------------------");
    }

    // metodo para limpar o console usando caracteres de controle ANSI
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
