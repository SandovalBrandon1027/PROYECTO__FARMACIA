import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;

public class registro_productos extends JFrame {
    PreparedStatement ps;
    DefaultListModel<String> mod = new DefaultListModel<>();
    Statement st;
    ResultSet r;
    private JPanel lista_productos;
    private JTextField IdText;
    private JTextField NombreText;
    private JTextField UnidadesText;
    private JTextField PrecioText;
    private JButton elminarButton;
    private JButton actualizarButton;
    private JButton guardarButton;
    private JButton mostrarButton;
    private JList<String> list1;
    private Connection con;

    public registro_productos() {
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setTitle("Farmacia su economia");
        setContentPane(lista_productos);
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Crear y configurar la JList con el modelo personalizado
        list1.setModel(mod);

        // Agregar el encabezado a la lista
        mod.addElement("Id        ║        Nombre         ║     Unidades      ║      Precio");

        // ...
    }

    public void Listar() throws SQLException {
        conectar();
        st = con.createStatement();
        r = st.executeQuery("SELECT id, nombre, cantidad, precio FROM productos");

        mod.removeAllElements();
        mod.addElement("Id         ║        Nombre         ║  Unidades  ║      Precio");

        while (r.next()) {
            mod.addElement(
                    r.getString(1) + "     ║     " + r.getString(2) + "      ║          " + r.getString(3) + "          ║    " + r.getString(4)
            );
        }
    }

    public void insertar() throws SQLException {
        conectar();
        ps = con.prepareStatement("INSERT INTO productos (id, nombre, cantidad, precio) VALUES (?,?,?,?)");

        int id = Integer.parseInt(IdText.getText());
        String nombre = NombreText.getText();
        int unidades = Integer.parseInt(UnidadesText.getText());
        BigDecimal precioDecimal = new BigDecimal(PrecioText.getText());

        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, unidades);
        ps.setBigDecimal(4, precioDecimal);

        if (ps.executeUpdate() > 0) {
            Listar(); // Actualizar la lista después de la inserción
            IdText.setText("");
            NombreText.setText("");
            UnidadesText.setText("");
            PrecioText.setText("");
        }

        // Cerrar recursos (PreparedStatement y Connection) aquí si es necesario
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registro_productos();
        });
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usuarios", "root", "");
            System.out.println("Conectado");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
