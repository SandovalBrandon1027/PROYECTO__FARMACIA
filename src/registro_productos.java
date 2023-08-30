import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
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
    private JTable table1;
    private Connection con;

    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM productos"; // Cambia "tabla_nombre"

    public registro_productos() {
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mostrar();

            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setTitle("Farmacia su economia");
        setContentPane(lista_productos);
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public void Mostrar(){
        //genera columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre Producto");
        model.addColumn("Unidades");
        model.addColumn("Precio");


        // Poner las columnas en el modelo hecho en el Jtable
        table1.setModel(model);

        //arreglo que almnacena datos
        String [] informacion=new String[4];//especifico el numero de columnas

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()){
                //detallo la posicion de dato almacenado en arreglo, con la columna en la quebe ir
                informacion[0]=rs.getString(1);//num de columna
                informacion[1]=rs.getString(2);
                informacion[2]=rs.getString(3);
                informacion[3]=rs.getString(4);


                // genera una fila por cada ingistro
                model.addRow(informacion);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }

    public void insertar() throws SQLException {


        // Cerrar recursos (PreparedStatement y Connection) aquí si es necesario
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registro_productos();
        });
    }


}
