import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    private JPanel login;
    private JComboBox Tipo_usuario;
    private JTextField usuario;
    private JPasswordField contraseña;
    private JButton ingresarButton;
    private JLabel user;
    private JLabel pass;
    private JFrame frame;
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM USERADMIN";

    public login() {
        frame = new JFrame("Farmacia Estelar");

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) Tipo_usuario.getSelectedItem();

                // Solo realizamos la autenticación si se selecciona "Administrador"
                if (selectedTipo.equals("Administrador")) {
                    String usuarioText = usuario.getText();
                    String contraseñaText = new String(contraseña.getPassword());

                    if (autenticarUsuario(usuarioText, contraseñaText)) {
                        abrirAdministradorForm();
                        frame.dispose(); // Cerrar la ventana de inicio de sesión
                    } else {
                        JOptionPane.showMessageDialog(null, "Autenticación fallida");
                    }
                } else if (selectedTipo.equals("Cajero")) {
                    abrirCajeroForm();
                    frame.dispose(); // Cerrar la ventana de inicio de sesión
                }
            }
        });

        frame.setContentPane(login);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Tipo_usuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) Tipo_usuario.getSelectedItem();
                if (selectedRole.equals("Cajero")) {
                    usuario.setVisible(false);
                    contraseña.setVisible(false);
                    user.setVisible(false);
                    pass.setVisible(false);
                } else {
                    usuario.setVisible(true);
                    contraseña.setVisible(true);
                    user.setVisible(true);
                    pass.setVisible(true);
                }
            }
        });
    }

    private boolean autenticarUsuario(String usuario, String contraseña) {
        try {
            Connection conexion = DriverManager.getConnection(DB_URL, USER, PASS);
            String consulta = "SELECT * FROM USERADMIN WHERE Usuario = ? AND Contraseña = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contraseña);
            ResultSet resultado = preparedStatement.executeQuery();
            return resultado.next(); // Devuelve true si el usuario está autenticado correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error de base de datos o autenticación fallida
        }
    }

    private void abrirCajeroForm() {
        cajero cajero12 = new cajero();
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
