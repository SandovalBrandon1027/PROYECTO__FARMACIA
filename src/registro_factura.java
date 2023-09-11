import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.*;



public class registro_factura extends  JFrame{
    private JPanel factura_cajero;
    private JTextField CodigoText;
    private JTextField DNIText;
    private JTextField NombreText;
    private JTextField DireccionText;
    private JTextField EmailText;
    private JTextField TelefonoText;
    private JButton mostrarButton;
    private JButton guardarButton;
    private JButton actualizarButton;
    private JButton facturaButton;
    private JTable table1;
    private JButton atrasbutton;
    private JTextField ApellidoText;
    private int filaSeleccionada = -1;
    DefaultTableModel mod=new DefaultTableModel();
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM USUARIOS";

    public registro_factura(){


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
        facturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                generarFacturaPDF();
                dispose();
                cajero probando = new cajero();

            }
        });


        table1.getSelectionModel().addListSelectionListener(e -> {
            filaSeleccionada = table1.getSelectedRow();

            // Verificar si se ha seleccionado una fila
            if (filaSeleccionada >= 0) {
                // Obtener los valores de la fila seleccionada DNI, Nombre, Apellido, Direccion, Email, Telefono

                String DNI = table1.getValueAt(filaSeleccionada, 0).toString();
                String Nombre = table1.getValueAt(filaSeleccionada, 1).toString();
                String Apellido = table1.getValueAt(filaSeleccionada, 2).toString();
                String Direccion = table1.getValueAt(filaSeleccionada, 3).toString();
                String Email = table1.getValueAt(filaSeleccionada, 4).toString();
                String Telefono = table1.getValueAt(filaSeleccionada, 5).toString();

                // Establecer los valores en los JTextField
                DNIText.setText(DNI);
                NombreText.setText(Nombre);
                ApellidoText.setText(Apellido);
                DireccionText.setText(Direccion);
                EmailText.setText(Email);
                TelefonoText.setText(Telefono);
            }
        });






        setTitle("Farmacia Estelar");
        setContentPane(factura_cajero);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        atrasbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                cajero facturara = new cajero();
            }
        });
        table1.addComponentListener(new ComponentAdapter() {
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtén los valores de tus campos de entrada
                String dni = DNIText.getText();
                String nombre = NombreText.getText();
                String apellido = ApellidoText.getText();
                String direccion = DireccionText.getText();
                String email = EmailText.getText();
                String telefono =  TelefonoText.getText();

                // Llama a un método para guardar los datos en la base de datos
                boolean resultado = guardarDatosEnBaseDeDatos(dni, nombre, apellido, direccion, email, telefono);

                // Muestra un mensaje de confirmación
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario guardado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar el usuario.");
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtén el DNI del usuario a actualizar
                String dni = DNIText.getText();

                // Llama a un método para actualizar los datos en la base de datos
                boolean resultado = actualizarDatosEnBaseDeDatos(dni);

                // Muestra un mensaje de confirmación
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el usuario o el usuario no existe.");
                }
            }
        });
    }
    private boolean actualizarDatosEnBaseDeDatos(String dni) {
        try {
            // Establece la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FARMACIA", "root", "");

            // Crea la sentencia SQL para actualizar el registro en la tabla USUARIOS
            String sql = "UPDATE USUARIOS SET Nombre=?, Apellido=?, Direccion=?, Email=?, Telefono=? WHERE DNI=?";

            // Prepara la sentencia SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, NombreText.getText());
            pstmt.setString(2, ApellidoText.getText());
            pstmt.setString(3, DireccionText.getText());
            pstmt.setString(4, EmailText.getText());
            pstmt.setString(5, TelefonoText.getText());
            pstmt.setString(6, dni);

            // Ejecuta la actualización
            int rowsAffected = pstmt.executeUpdate();

            // Cierra la conexión y el PreparedStatement
            pstmt.close();
            conn.close();

            // Comprueba si se actualizó el usuario correctamente
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    private boolean guardarDatosEnBaseDeDatos(String dni, String nombre, String apellido, String direccion, String email, String telefono) {
        try {
            // Establece la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FARMACIA", "root", "");

            // Crea la sentencia SQL para insertar un nuevo registro en la tabla USUARIOS
            String sql = "INSERT INTO USUARIOS (DNI, Nombre, Apellido, Direccion, Email, Telefono) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepara la sentencia SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, direccion);
            pstmt.setString(5, email);
            pstmt.setString(6, telefono);

            // Ejecuta la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Cierra la conexión y el PreparedStatement
            pstmt.close();
            conn.close();

            // Comprueba si se guardó el usuario correctamente
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void Mostrar(){
        //genera columnas de la tabla DNI, Nombre, Apellido, Direccion, Email, Telefono
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Apellidotexto");
        model.addColumn("Direccion");
        model.addColumn("Email");
        model.addColumn("Telefono");

        // Poner las columnas en el modelo hecho en el Jtable
        table1.setModel(model);

        //arreglo que almnacena datos
        String [] informacion=new String[7];//especifico el numero de columnas

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
    public void generarFacturaPDF() {
        // Obtén los datos de la factura
        String nombreCliente = NombreText.getText();
        String direccionCliente = DireccionText.getText();
        String emailCliente = EmailText.getText();
        String telefonoCliente = TelefonoText.getText();
        String ID = DNIText.getText(); // Agrega aquí el ID del producto
        String nombreProducto = CodigoText.getText(); // Agrega aquí el nombre del producto




        cajero cajeroInstance = new cajero(); // Asume que tienes una instancia de la clase cajero
        double totalCompras = cajeroInstance.calcularTotal();
        // Crea un documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("factura3.pdf"));
            document.open();

            // Configuración de fuente y estilos
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font fontEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Título de la factura
            Paragraph titulo = new Paragraph("FACTURA DE COMPRA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Datos del cliente y detalles
            PdfPTable datosTabla = new PdfPTable(2);
            datosTabla.setWidthPercentage(100);
            datosTabla.setWidths(new float[]{1, 3}); // Configura la proporción de columnas

            // Encabezado de la tabla
            PdfPCell cellEncabezado = new PdfPCell(new Phrase("Cliente", fontEncabezado));
            cellEncabezado.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            datosTabla.addCell(cellEncabezado);

            cellEncabezado = new PdfPCell(new Phrase("Detalle", fontEncabezado));
            cellEncabezado.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            datosTabla.addCell(cellEncabezado);

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{1, 3}); // Configura la proporción de columnas

            PdfPCell cellTotal = new PdfPCell(new Phrase("Total", fontEncabezado));
            cellTotal.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalTable.addCell(cellTotal);

            cellTotal = new PdfPCell(new Phrase(Double.toString(totalCompras), fontNormal)); // Agrega el total como texto
            cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(cellTotal);

            document.add(totalTable);

            // Datos del cliente y detalles
            datosTabla.addCell(new Phrase("Nombre del Cliente:", fontNormal));
            datosTabla.addCell(new Phrase(nombreCliente, fontNormal));
            datosTabla.addCell(new Phrase("Dirección del Cliente:", fontNormal));
            datosTabla.addCell(new Phrase(direccionCliente, fontNormal));
            datosTabla.addCell(new Phrase("Email del Cliente:", fontNormal));
            datosTabla.addCell(new Phrase(emailCliente, fontNormal));
            datosTabla.addCell(new Phrase("Teléfono del Cliente:", fontNormal));
            datosTabla.addCell(new Phrase(telefonoCliente, fontNormal));
            datosTabla.addCell(new Phrase("ID del Producto:", fontNormal));
            datosTabla.addCell(new Phrase(ID, fontNormal));
            datosTabla.addCell(new Phrase("Nombre del Producto:", fontNormal));
            datosTabla.addCell(new Phrase(nombreProducto, fontNormal));

            document.add(datosTabla);

            // Cierra el documento
            document.close();

            JOptionPane.showMessageDialog(null, "Factura generada con éxito.");

        } catch (DocumentException | FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar la factura.");
        }
    }



    public class ContadorFacturas {
        private static final String archivoFactura = "numero_factura.txt";

        // Método para obtener el número de factura actual
        public static int obtenerNumeroFactura() {
            int numeroFactura = leerNumeroFactura(archivoFactura);
            return numeroFactura;
        }

        // Método para incrementar y guardar el número de factura
        public static void incrementarNumeroFactura() {
            int numeroFactura = leerNumeroFactura(archivoFactura);
            numeroFactura++;
            guardarNumeroFactura(archivoFactura, numeroFactura);
        }

        // Método para leer el número de factura actual desde el archivo
        private static int leerNumeroFactura(String archivo) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea = br.readLine();
                br.close();
                if (linea != null) {
                    return Integer.parseInt(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Si el archivo no existe o hay un error, devuelve 0
            return 0;
        }

        // Método para guardar el número de factura actual en el archivo
        private static void guardarNumeroFactura(String archivo, int numeroFactura) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(String.valueOf(numeroFactura));
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registro_factura();
        });

    }
}
