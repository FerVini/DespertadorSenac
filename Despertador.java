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

        int horaDespertar = -1;
        int minutoDespertar = -1;

        int horaRestante;
        int minutoRestante;
        int segundoRestante;

        boolean adiarDespertador = false;
        boolean encerrarDespertador = false;
        boolean volumeCrescente = false;
        boolean respostaErrada = true;

        String nomeDespertador;        
        String respostaUsuario;

        Scanner inputScanner = new Scanner(System.in);

        while (respostaErrada == true) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

            System.out.println("Agora são: " + horaAtual + "h:" + minutoAtual + "m:" + segundoAtual + "s");
    
            System.out.println("Digite abaixo SOMENTE NÚMERO INTEIRO (de 0 a 23) a hora do despertador e tecle enter:");
            respostaUsuario = inputScanner.nextLine();
            
            try {   
                horaDespertar = Integer.valueOf(respostaUsuario);
                respostaErrada = false;
            } catch (Exception e) {
                // System.err.println("Erro: " + e);
                System.out.println("Resposta inváida! Verifique sua digitação e tente novamente.");
                try {
                    Thread.sleep(5000);
                } catch (Exception exception) {
                    System.err.println("Erro: " + exception);
                }
            }
        }
        
        System.out.println("Digite abaixo SOMENTE o minuto do despertador e tecle enter:");
        respostaUsuario = inputScanner.nextLine();
        minutoDespertar = Integer.valueOf(respostaUsuario);
        

        System.out.println("Deseja adiar o alarme quando tocar?");
        System.out.println("Digite a opção abaixo e tecle enter:");
        System.out.println("[s] - Sim");
        System.out.println("[n] - Não");

        respostaUsuario = inputScanner.nextLine();

        if(respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
            adiarDespertador = true;
        } else if (respostaUsuario.equals("n") || respostaUsuario.equals("N")){
            adiarDespertador = false;
        }

        System.out.println("Digite abaixo o nome do despertador e tecle enter:");

        respostaUsuario = inputScanner.nextLine();
        nomeDespertador = respostaUsuario;
        
        System.out.println("Deseja o volume crescente?");
        System.out.println("Digite abaixo a opção desejada e tecle Enter:");
        System.out.println("[s] - Sim");
        System.out.println("[n] - Não");
        
        respostaUsuario = inputScanner.nextLine();

        if(respostaUsuario.equals("s") || respostaUsuario.equals("S")) {
            volumeCrescente = true;
        } else if (respostaUsuario.equals("n") || respostaUsuario.equals("N")){
            volumeCrescente = false;
        }

        while (encerrarDespertador == false) {
            clearScreen();

            horaAtual = LocalDateTime.now().getHour();
            minutoAtual = LocalDateTime.now().getMinute();
            segundoAtual = LocalDateTime.now().getSecond();

            horaRestante = horaDespertar - horaAtual;
            minutoRestante = (minutoDespertar - minutoAtual) - 1;
            segundoRestante = 59 - segundoAtual;

            if (horaRestante > 0 && minutoDespertar <= minutoAtual) {
                horaRestante--;
            }
    
            if (minutoRestante < 0) {
                minutoRestante = ((59 - minutoAtual) + minutoDespertar) - 1;
            }
    
            if (segundoRestante > 59) {
                segundoRestante = 59;
            }

            if(horaAtual == horaDespertar && minutoAtual == minutoDespertar){
                tocarSom(volumeCrescente);
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

            System.out.println("Faltam: " + horaRestante + " hora(s), " + minutoRestante + " minuto(s), " + segundoRestante + " segundos para o próximo alarme.");

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

    public static synchronized void tocarSom(boolean volumeCrescente) {
        new Thread(new Runnable(){
            public void run() {
                try{
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Despertador.class.getResourceAsStream("./musica.wav"));
                    clip.open(inputStream);

                    if(volumeCrescente == true){
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        float volumeMinimo = -30.0f; 
                        float volumeMaximo = 0.0f; 
                        float voluemAumentar = 6.0f; 
                        gainControl.setValue(volumeMinimo);
                        boolean aumentarVolume = true;
                        long clipTime;
                        
                        while (aumentarVolume == true) {
                            clip.start();
                            if(gainControl.getValue() >= volumeMinimo && gainControl.getValue() <= volumeMaximo) {
                                gainControl.setValue(gainControl.getValue() + voluemAumentar);
                                clipTime = clip.getMicrosecondPosition();

                                try {
                                    Thread.sleep(1000);
                                } catch(Exception e) {
                                    System.err.println("Erro: " + e);
                                }

                                clip.stop();
                                clip.setMicrosecondPosition(clipTime);
                            } else {
                                aumentarVolume = false;
                            }
                        }
                    } else {
                        clip.start();
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    
}