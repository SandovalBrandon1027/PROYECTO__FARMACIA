import javax.swing.*;

public class cajero extends JFrame {

    private JPanel interfaz_cajero;

    public
    cajero() {
        setTitle("Farmacia su economia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar este formulario
        setSize(550, 400); // Tamaño del formulario
        setLocationRelativeTo(null); // Centrar en la pantalla
        setContentPane(interfaz_cajero); // Establecer el panel como contenido

        // Aquí puedes agregar más componentes y configuraciones específicas para el formulario del administrador

        setVisible(true); // Mostrar el formulario
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new cajero();
        });
    }
}
