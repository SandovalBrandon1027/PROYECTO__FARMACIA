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
                CodigoText.setText("");
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
        String nombre = NombreText.getText(); // Agrega aquí el nombre del producto

        // Crea un documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("factura1.pdf"));
            document.open();

            // Agrega el contenido de la factura al documento
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("FACTURA DE COMPRA\n\n", font);
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
            new registro_factura();
        });

    }
}
