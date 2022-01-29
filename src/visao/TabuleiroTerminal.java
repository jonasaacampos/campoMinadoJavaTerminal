package visao;

import excessao.ExplosaoException;
import excessao.SairException;
import modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroTerminal {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroTerminal(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar){
                mecanicaDoJogo();
                System.out.println("Iniciar Jogo? ( S / n ): ");
                String resposta = entrada.nextLine();

                if("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                    System.out.println("Obrigado pelo seu tempo comigo ;D");
                }
            }

        }catch (SairException exception) {
            System.out.println("Até a próxima");
        } finally {
            entrada.close();
        }
    }

    private void mecanicaDoJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);
                String digitado = capturarValorDigitado("Digite (x,y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();

                digitado = capturarValorDigitado("1 - Abrir campo | 2 - (des)Marcar campo: ");

                if("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equals(digitado)){
                    tabuleiro.alterarMarcacao(xy.next(), xy.next());
                }

            }
            System.out.println(tabuleiro);
            System.out.println("Você venceu!");
        }catch (ExplosaoException exception) {
            System.out.println("BUM!");
            System.out.println(tabuleiro);
            System.out.println("Você perdeu");
        }
    }

    private String capturarValorDigitado(String instrucao) {
        System.out.print(instrucao);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }

}
