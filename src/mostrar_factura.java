import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class mostrar_factura extends JFrame {
    private JPanel mostrar_factura;
    private JTable table1;
    private JTable table2;
    private JButton button1;

    private int filaSeleccionada = -1;

    DefaultTableModel mod=new DefaultTableModel();
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM usuarios"; // Cambia "tabla_nombre"

    public mostrar_factura(){
        Mostrar();
        setTitle("Farmacia su economia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar este formulario
        setSize(900, 500); // Tamaño del formulario
        setLocationRelativeTo(null); // Centrar en la pantalla
        setContentPane(mostrar_factura); // Establecer el panel como contenido

        // Aquí puedes agregar más componentes y configuraciones específicas para el formulario del administrador

        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                seleccionar_accion regresar = new seleccionar_accion();
            }
        });
    }

    public void Mostrar(){
        //genera columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Direccion");
        model.addColumn("Email");
        model.addColumn("telefono");


        // Poner las columnas en el modelo hecho en el Jtable
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //arreglo que almnacena datos
        String [] informacion=new String[6];//especifico el numero de columnas

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
                informacion[4]=rs.getString(5);
                informacion[5]=rs.getString(6);



                // genera una fila por cada ingistro
                model.addRow(informacion);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new mostrar_factura();
        });
    }
}
