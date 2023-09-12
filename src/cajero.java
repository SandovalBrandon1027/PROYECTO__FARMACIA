import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;

public class cajero extends JFrame {
    private JPanel interfaz_cajero;
    private JTextField NombreText;
    private JTextField CantidadText;
    private JTextField PrecioText;
    private JButton volverAComprarButton;
    private JButton pagarButton;
    private JButton regresarButton;
    private JTable table1;
    private JButton buscarButton;
    private JButton mostrarButton;
    private JButton calcularPrecioButton;
    private JTextField ID1Text;
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM PRODUCTOS";
    private int filaSeleccionada = -1;


    public cajero() {
        Mostrar();
        setTitle("Farmacia Estelar");
        setContentPane(interfaz_cajero);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);



        // Aquí puedes agregar más componentes y configuraciones específicas para el formulario del administrador

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la instancia anterior
                login login40 = new login();
            }
        });


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreProducto = NombreText.getText().trim();

                if (!nombreProducto.isEmpty()) {
                    buscarProducto(nombreProducto);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingresa el nombre del producto a buscar.");
                }
            }
        });
        volverAComprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NombreText.setText(""); // Limpia el primer campo de texto
                CantidadText.setText("");
                PrecioText.setText("");


            }
        });
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mostrar();
            }
        });
        calcularPrecioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String cantidadStr = CantidadText.getText().trim();
                    if (!cantidadStr.isEmpty()) {
                        try {
                            double cantidad = Double.parseDouble(cantidadStr);
                            double precio = Double.parseDouble(table1.getValueAt(filaSeleccionada, 3).toString());
                            double total = cantidad * precio;

                            // Formatear el precio a dos cifras decimales
                            DecimalFormat df = new DecimalFormat("#.##");
                            String precioFormateado = df.format(total);

                            PrecioText.setText(precioFormateado);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ingresa la cantidad.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto de la tabla.");
                }
            }
        });



        // ...

        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    try {
                        int idProducto = Integer.parseInt(table1.getValueAt(filaSeleccionada, 0).toString());
                        int cantidadComprada = Integer.parseInt(CantidadText.getText().trim());

                        // Llama al método para actualizar las unidades
                        actualizarUnidades(idProducto, cantidadComprada);


                        // Muestra la ventana de factura

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto de la tabla.");
                }
                String Precio = PrecioText.getText();
                String Id = ID1Text.getText();
                String NombreProd = NombreText.getText();
                String Cantidad = CantidadText.getText();
                dispose();
                registro_factura factura = new registro_factura(Precio, Id, NombreProd, Cantidad );

            }
        });

        table1.getSelectionModel().addListSelectionListener(e -> {
            filaSeleccionada = table1.getSelectedRow();

            // Verificar si se ha seleccionado una fila
            if (filaSeleccionada >= 0) {
                // Obtener los valores de la fila seleccionada
                String Id = table1.getValueAt(filaSeleccionada, 0).toString();
                String Nombre = table1.getValueAt(filaSeleccionada, 1).toString();


                // Establecer los valores en los JTextField
                ID1Text.setText(Id);
                NombreText.setText(Nombre);

            }
        });

    }


    public void actualizarUnidades(int idProducto, int cantidadComprada) {
        String updateQuery = "UPDATE productos SET Unidades = Unidades - ? WHERE ID = ?";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, cantidadComprada);
            pstmt.setInt(2, idProducto);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Compra exitosa. Unidades actualizadas.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la cantidad de unidades.");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar las unidades: " + ex.toString());
        }
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
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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


    private void buscarProducto(String nombreProducto) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Limpia la tabla

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT * FROM productos WHERE NombreProd LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + nombreProducto + "%");
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "El producto no existe.");
            }

            while (rs.next()) {
                String[] informacion = {
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                };
                model.addRow(informacion);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        }
    }

    //añadir total
    public double totalCompras;

    public void agregarCompra(double montoCompra) {
        totalCompras += montoCompra;
    }
    public double calcularTotal() {
        return totalCompras;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new cajero();
        });
    }

}
