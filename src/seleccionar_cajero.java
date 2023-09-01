import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionar_cajero extends JFrame{
    private JButton ventasButton;
    private JPanel panel1;
    private JButton agregarUsuarioButton;
    private JFrame frame;


public seleccionar_cajero() {
    ventasButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            cajero cajero12 = new cajero();

        }
    });
    agregarUsuarioButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            Registro1 usuarios = new Registro1();

        }
    });
    frame.setContentPane(panel1);
    frame.setMinimumSize(new Dimension(700, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);


}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new seleccionar_cajero();
            }
        });
    }





}
