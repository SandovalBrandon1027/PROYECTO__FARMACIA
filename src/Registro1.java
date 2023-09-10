import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Registro1 extends JFrame{
    PreparedStatement ps;

    Statement st;
    ResultSet r;
    private JPanel panel;
    private JTextField Nombretext;
    private JTextField ApellidoText;
    private JTextField DireccionText;
    private JTextField EmailText;
    private JTextField TelefonoText;
    private JButton mostrarButton;
    private JButton guardarButton;
    private JButton elminarButton;
    private JButton actualizarButton;
    private JTable table1;
    private JButton regresarButton;
    private JButton facturaButton;
    private JTextField DniText;
    private Connection con;
    private int filaSeleccionada = -1;

    DefaultTableModel mod=new DefaultTableModel();
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM usuarios";


    public Registro1() {


        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mostrar();

            }
        });


        table1.getSelectionModel().addListSelectionListener(e -> {
            filaSeleccionada = table1.getSelectedRow();

            // Verificar si se ha seleccionado una fila
            if (filaSeleccionada >= 0) {
                // Obtener los valores de la fila seleccionada
                String codigo = table1.getValueAt(filaSeleccionada, 0).toString();
                String idUsuario = table1.getValueAt(filaSeleccionada, 1).toString();
                String nombre = table1.getValueAt(filaSeleccionada, 2).toString();
                String direccion = table1.getValueAt(filaSeleccionada, 3).toString();
                String email = table1.getValueAt(filaSeleccionada, 4).toString();
                String telefono = table1.getValueAt(filaSeleccionada, 5).toString();

                // Establecer los valores en los JTextField
                DniText.setText(codigo);
                Nombretext.setText(idUsuario);
                ApellidoText.setText(nombre);
                DireccionText.setText(direccion);
                EmailText.setText(email);
                TelefonoText.setText(telefono);
            }
        });

        setTitle("Farmacia Estelar");
        setContentPane(panel);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                seleccionar_accion seleccionar = new seleccionar_accion();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los valores de los campos de texto o componentes de tu interfaz
                String dni = DniText.getText();
                String nombre = Nombretext.getText();
                String apellido = ApellidoText.getText();
                String direccion = DireccionText.getText();
                String email = EmailText.getText();
                String telefono = TelefonoText.getText();

                // Llama a un método para guardar los datos en la base de datos
                boolean resultado = guardarUsuario(dni, nombre, apellido, direccion, email, telefono);

                // Muestra un mensaje emergente con el resultado
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario insertado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo insertar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los valores de los campos de texto o componentes de tu interfaz
                String dni = DniText.getText();
                String nombre = Nombretext.getText();
                String apellido = ApellidoText.getText();
                String direccion = DireccionText.getText();
                String email = EmailText.getText();
                String telefono = TelefonoText.getText();

                // Llama a un método para actualizar los datos en la base de datos
                boolean resultado = actualizarUsuario(dni, nombre, apellido, direccion, email, telefono);

                // Muestra un mensaje emergente con el resultado
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        elminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene el DNI del usuario que deseas eliminar
                String dniEliminar = DniText.getText();

                // Llama a un método para eliminar el usuario de la base de datos
                boolean resultado = eliminarUsuario(dniEliminar);

                // Muestra un mensaje emergente con el resultado
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpia los campos de texto después de eliminar
                    Nombretext.setText("");
                    ApellidoText.setText("");
                    DireccionText.setText("");
                    EmailText.setText("");
                    TelefonoText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// Más código de configuración de tu interfaz y ventana Swing
    }

    // Método para eliminar un usuario de la base de datos
    private boolean eliminarUsuario(String dniEliminar) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "DELETE FROM usuarios WHERE DNI=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, dniEliminar);

            int rowsDeleted = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            return rowsDeleted > 0; // Retorna verdadero si se eliminó al menos una fila
        } catch (SQLException ex) {
            System.err.println("Error al eliminar el usuario: " + ex.getMessage());
            return false;
        }
    }

    // Método para actualizar un usuario en la base de datos
    private boolean actualizarUsuario(String dni, String nombre, String apellido, String direccion, String email, String telefono) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "UPDATE usuarios SET Nombre=?, Apellido=?, Direccion=?, Email=?, Telefono=? WHERE DNI=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telefono);
            preparedStatement.setString(6, dni);

            int rowsUpdated = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            return rowsUpdated > 0; // Retorna verdadero si se actualizó al menos una fila
        } catch (SQLException ex) {
            System.err.println("Error al actualizar el usuario: " + ex.getMessage());
            return false;
        }
    }
    private static boolean guardarUsuario(String dni, String nombre, String apellido, String direccion, String email, String telefono) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "INSERT INTO usuarios (DNI, Nombre, Apellido, Direccion, Email, Telefono) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, direccion);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, telefono);

            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            return rowsInserted > 0; // Retorna verdadero si se insertó al menos una fila
        } catch (SQLException ex) {
            System.err.println("Error al guardar el usuario: " + ex.getMessage());
            return false;
        }
    }



    public void Mostrar(){
        //genera columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Direccion");
        model.addColumn("Email");
        model.addColumn("Telefono");

        // Poner las columnas en el modelo hecho en el Jtable
        table1.setModel(model);

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
            new Registro1();
        });

    }



}