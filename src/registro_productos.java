import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


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
    private JButton regresarButton;
    private Connection con;
    private int filaSeleccionada = -1;

    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM PRODUCTOS"; // Cambia "tabla_nombre"

    public registro_productos() {
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mostrar();

            }
        });

        setTitle("Farmacia Estelar");
        setContentPane(lista_productos);
        setMinimumSize(new Dimension(650, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                seleccionar_accion seleccionar2 = new seleccionar_accion();
            }
        });

        table1.getSelectionModel().addListSelectionListener(e -> {
            filaSeleccionada = table1.getSelectedRow();

            // Verificar si se ha seleccionado una fila
            if (filaSeleccionada >= 0) {
                // Obtener los valores de la fila seleccionada
                String Id = table1.getValueAt(filaSeleccionada, 0).toString();
                String Nombre = table1.getValueAt(filaSeleccionada, 1).toString();
                String Unidades = table1.getValueAt(filaSeleccionada, 2).toString();
                String Precio = table1.getValueAt(filaSeleccionada, 3).toString();


                // Establecer los valores en los JTextField
                IdText.setText(Id);
                NombreText.setText(Nombre);
                UnidadesText.setText(Unidades);
                PrecioText.setText(Precio);

            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establecer la conexión a la base de datos
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FARMACIA", "root", "");

                    // Preparar la sentencia SQL para la inserción de datos
                    String sql = "INSERT INTO PRODUCTOS (ID, NombreProd, Unidades, Precio) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    // Obtener los valores de los campos de texto
                    int id = Integer.parseInt(IdText.getText());
                    String nombrePro = NombreText.getText();
                    int unidades = Integer.parseInt(UnidadesText.getText());
                    double precio = Double.parseDouble(PrecioText.getText());

                    // Establecer los valores en la sentencia SQL
                    pstmt.setInt(1, id);
                    pstmt.setString(2, nombrePro);
                    pstmt.setInt(3, unidades);
                    pstmt.setDouble(4, precio);

                    // Ejecutar la inserción de datos
                    pstmt.executeUpdate();

                    // Cerrar la conexión y liberar recursos
                    pstmt.close();
                    conn.close();

                    // Mostrar un mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establecer la conexión a la base de datos
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FARMACIA", "root", "");

                    // Preparar la sentencia SQL para la actualización de datos
                    String sql = "UPDATE PRODUCTOS SET NombreProd = ?, Unidades = ?, Precio = ? WHERE ID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    // Obtener los valores de los campos de texto
                    int id = Integer.parseInt(IdText.getText());
                    String nombrePro = NombreText.getText();
                    int unidades = Integer.parseInt(UnidadesText.getText());
                    double precio = Double.parseDouble(PrecioText.getText());

                    // Establecer los valores en la sentencia SQL
                    pstmt.setString(1, nombrePro);
                    pstmt.setInt(2, unidades);
                    pstmt.setDouble(3, precio);
                    pstmt.setInt(4, id);

                    // Ejecutar la actualización de datos
                    pstmt.executeUpdate();

                    // Cerrar la conexión y liberar recursos
                    pstmt.close();
                    conn.close();

                    // Mostrar un mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                }
            }
        });

        // Agregar DocumentListener a los campos de texto para detectar cambios en tiempo real
        IdText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }
        });

        NombreText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }
        });

        UnidadesText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }
        });

        PrecioText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarButton.setEnabled(true);
            }
        });
        elminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establecer la conexión a la base de datos
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FARMACIA", "root", "");

                    // Obtener el valor de ID desde el campo de texto
                    int id = Integer.parseInt(IdText.getText());

                    // Preparar la sentencia SQL para eliminar la fila según la ID
                    String sql = "DELETE FROM PRODUCTOS WHERE ID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);

                    // Ejecutar la eliminación de la fila
                    int rowsDeleted = pstmt.executeUpdate();

                    // Cerrar la conexión y liberar recursos
                    pstmt.close();
                    conn.close();

                    if (rowsDeleted > 0) {
                        // Si se eliminó al menos una fila
                        JOptionPane.showMessageDialog(null, "Fila eliminada correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró ninguna fila con la ID especificada");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar la fila");
                }
            }
        });
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
