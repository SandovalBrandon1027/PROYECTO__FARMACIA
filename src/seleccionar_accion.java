import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionar_accion extends JFrame {
    private JPanel interfaz_administrador;
    private JButton ingresarProductoButton;
    private JButton agregarUsuarioButton;
    private JButton revisarVentasButton;
    private JButton regresarButton;


    public seleccionar_accion() {
        setTitle("Farmacia su economia");
        setContentPane(interfaz_administrador);
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);







        ingresarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();// Cerrar el formulario actual
                registro_productos productos = new registro_productos();
            }
        });

        agregarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();// Cerrar el formulario actual
                registro Registro32 = new registro();


            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                login iinterfaz1 = new login();


            }
        });

        setVisible(true); // Mostrar el formularioç


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new seleccionar_accion();
        });
    }

}
