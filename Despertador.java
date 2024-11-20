// import java.awt.*;
// import java.awt.event.*;
import java.time.*;
import java.util.*;
import javax.sound.sampled.*;

public class Despertador {    
    public static void main(String[] str) {
        
        int horaAtual = LocalDateTime.now().getHour();
        int minutoAtual = LocalDateTime.now().getMinute();
        int segundoAtual = LocalDateTime.now().getSecond();

        int horaDespertar;
        int minutoDespertar;            

        boolean adiarDespertador = false;
        String nomeDespertador;        

        System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");

        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Digite abaixo a hora do despertador e tecle enter:");

        horaDespertar = Integer.valueOf(inputScanner.nextLine());

        System.out.println("Digite abaixo o minuto do despertador e tecle enter:");

        minutoDespertar = Integer.valueOf(inputScanner.nextLine());

        System.out.println("Deseja adiar o alarme?");
        System.out.println("Digite a opção abaixo e tecle enter:");
        System.out.println("[s] - Sim");
        System.out.println("[n] - Não");

        String respostaUsuario = inputScanner.nextLine();

        if(respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
            adiarDespertador = true;
        } else if (respostaUsuario.equals("n") || respostaUsuario.equals("N")){
            adiarDespertador = false;
        }

        System.out.println("Digite abaixo o nome do despertador e tecle enter:");

        nomeDespertador = inputScanner.nextLine();

        boolean encerrarDespertador = false;

        while (encerrarDespertador == false) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

            if(horaAtual == horaDespertar && minutoAtual == minutoDespertar){
                tocarSom();
                System.out.println("O despertador: " + nomeDespertador + "está ativo.");
                if (adiarDespertador == true) {
                    System.out.println("Adiar alarme?");
                    System.out.println("Digite abaixo a opção e tecle enter");
                    System.out.println("[5] - adiar 5 minutos");
                    System.out.println("[10] - adiar 10 minutos");
                    System.out.println("[s] - sair");
                    respostaUsuario = inputScanner.nextLine().trim();
                    if(respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
                        System.exit(0);
                    } else if (respostaUsuario.equals("5")) {
                        minutoDespertar += 5;
                    } else if (respostaUsuario.equals("10")) {
                        minutoDespertar += 10;
                    } else {
                        System.out.println("Opção inválida!");
                    }
                    if (minutoDespertar > 59) {
                        minutoDespertar -= 59;
                        horaDespertar++;
                        if(horaDespertar > 23) {
                            horaDespertar = 0;
                        }
                    }
                }

                encerrarDespertador = true;
            }

            System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");

            System.out.println("O alarme irá despertar às: " + horaDespertar + "h: " + minutoDespertar + "m");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Erro: " + e);
            }
        }
    }

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static synchronized void tocarSom() {
        new Thread(new Runnable(){
            public void run() {
                try{
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Despertador.class.getResourceAsStream("./musica.wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    
}