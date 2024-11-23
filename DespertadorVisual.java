import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.*;
import javax.sound.sampled.*;

public class DespertadorVisual extends JFrame {
    public static JLabel lblDespertar;
    public static JTextField txtHoraDespertar;
    public static JLabel lblSeparadorDoisPontos;
    public static String strDoisPontos = ":";
    public static JTextField txtMinutoDespertar;

    public static int horaAtual;
    public static int minutoAtual;
    public static int segundoAtual;
    public static JLabel lblHorarioAtual;

    public static JCheckBox chkAtivarSoneca;

    public static JCheckBox chkVolumeCrescente;

    public static JLabel lblTempoSoneca;
    public static JComboBox<String> cbxTempoSoneca;

    public static JLabel lblMensagemDespertador;
    public static JTextField txtMensagemDespertador;

    public static JLabel lblNotificacoes;

    public static GridBagLayout gbLayout;
    public static GridBagConstraints gbConstraints;

    public DespertadorVisual() {
        super("Despertador 1.0");
        gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        gbConstraints = new GridBagConstraints();

        lblDespertar = new JLabel("Despertar às:");
        addComponent(lblDespertar, 0, 0, 3, 1);

        txtHoraDespertar = new JTextField(10);
        addComponent(txtHoraDespertar, 0, 3, 2, 1);

        lblSeparadorDoisPontos = new JLabel(strDoisPontos);
        addComponent(lblSeparadorDoisPontos, 0, 5, 1, 1);

        txtMinutoDespertar = new JTextField(10);
        addComponent(txtMinutoDespertar, 0, 6, 2, 1);

        lblHorarioAtual = new JLabel("", SwingConstants.CENTER);
        addComponent(lblHorarioAtual, 1, 0, 8, 1);

        chkAtivarSoneca = new JCheckBox("Ativar Soneca?");
        addComponent(chkAtivarSoneca, 2, 0, 8, 1);

        chkVolumeCrescente = new JCheckBox("Volume Crescente?");
        addComponent(chkVolumeCrescente, 3, 0, 8, 1);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        while (true) {
            definirHorarioAtual();
            lblHorarioAtual.setText("Agora são: " + horaAtual + "h : " + minutoAtual + "m : " + segundoAtual + "s");
            dormir(1000);
        }
    }

    public static void dormir(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

    public static void definirHorarioAtual() {
        horaAtual = LocalDateTime.now().getHour();
        minutoAtual = LocalDateTime.now().getMinute();
        segundoAtual = LocalDateTime.now().getSecond();
    }

    public void addComponent(Component component, int row, int column, int width, int height) {
        if (height > 1 && width > 1) {
            gbConstraints.fill = GridBagConstraints.BOTH;
        } else if (height > 1) {
            gbConstraints.fill = GridBagConstraints.VERTICAL;
        } else {
            gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        }
        gbConstraints.gridy = row;
        gbConstraints.gridx = column;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbLayout.setConstraints(component, gbConstraints);
        add(component);
    }

    public static DespertadorVisual appDespertadorVisual;
    public static void main(String[] args) {
        appDespertadorVisual = new DespertadorVisual();
    }
}
