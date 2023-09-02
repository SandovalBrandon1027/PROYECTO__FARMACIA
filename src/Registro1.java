import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;


public class Registro1 extends JFrame{
    PreparedStatement ps;

    Statement st;
    ResultSet r;
    private JPanel panel;
    private JTextField DNItext;
    private JTextField NombreText;
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
    private JTextField CodigoText;
    private Connection con;
    private int filaSeleccionada = -1;

    DefaultTableModel mod=new DefaultTableModel();
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM usuarios"; // Cambia "tabla_nombre"






    public Registro1(){


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
                CodigoText.setText(codigo);
                DNItext.setText(idUsuario);
                NombreText.setText(nombre);
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
        facturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFacturaPDF();
            }
        });
    }



    public void Mostrar(){
        //genera columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("IdUsuario");
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

    public void generarFacturaPDF() {
        // Obtén los datos de la factura
        String dniCliente = DNItext.getText();
        String nombreCliente = NombreText.getText();
        String direccionCliente = DireccionText.getText();
        String emailCliente = EmailText.getText();
        String telefonoCliente = TelefonoText.getText();
        String ID = ""; // Agrega aquí el ID del producto
        String nombre = ""; // Agrega aquí el nombre del producto
        String unidades = ""; // Agrega aquí la cantidad de unidades

        // Crea un documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("factura1.pdf"));
            document.open();

            // Agrega el contenido de la factura al documento
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Factura de Compra\n\n", font);
            document.add(chunk);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Cliente"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Detalle"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);


            table.addCell("DNI del Cliente:");
            table.addCell(dniCliente);
            table.addCell("Nombre del Cliente:");
            table.addCell(nombreCliente);
            table.addCell("Dirección del Cliente:");
            table.addCell(direccionCliente);
            table.addCell("Email del Cliente:");
            table.addCell(emailCliente);
            table.addCell("Teléfono del Cliente:");
            table.addCell(telefonoCliente);
            table.addCell("ID del Producto:");
            table.addCell(ID);
            table.addCell("Nombre del Producto:");
            table.addCell(nombre);
            table.addCell("Unidades Compradas:");
            table.addCell(unidades);

            document.add(table);

            // Cierra el documento
            document.close();

            JOptionPane.showMessageDialog(null, "Factura generada con éxito.");

        } catch (DocumentException | FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar la factura.");
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Registro1();
        });

    }



}