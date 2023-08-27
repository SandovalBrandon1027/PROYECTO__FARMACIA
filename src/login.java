import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel login;
    private JComboBox Tipo_usuario;
    private JTextField usuario;
    private JPasswordField contraseña;
    private JButton ingresarButton;
    private JFrame frame;

    public login() {
        frame = new JFrame("iinterfaz_1");

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) Tipo_usuario.getSelectedItem();
                String usuarioText = usuario.getText();
                String contraseñaText = new String(contraseña.getPassword());

                // Aquí deberías realizar la autenticación y verificar las credenciales
                // Luego, dependiendo del tipo de usuario, abres la ventana correspondiente

                if (selectedTipo.equals("Administrador")) {
                    abrirAdministradorForm();
                } else if (selectedTipo.equals("Cajero")) {
                    abrirCajeroForm();
                }

                // Cerrar la ventana de inicio de sesión
                frame.dispose();
            }
        });

        frame.setContentPane(login);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirCajeroForm() {
        cajero cajeroForm = new cajero();
    }

    private void abrirAdministradorForm() {
        seleccionar_accion adminForm = new seleccionar_accion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new login();
            }
        });
    }
}
