import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;

public class mostrar_factura extends JFrame {
    private JPanel mostrar_factura;
    private JTable table1;
    private JButton button1;
    private JTextArea textArea;
    private JButton revisarVentasButton;
    private JButton facturaButton;


    private int filaSeleccionada = -1;


    DefaultTableModel mod=new DefaultTableModel();
    private static final String DB_URL = "jdbc:mysql://localhost/FARMACIA";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String QUERY = "SELECT * FROM FACTURAS"; // Cambia "tabla_nombre"



    public mostrar_factura(){

        Mostrar();
        setTitle("Farmacia Estelar");
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
        });// Agregar el controlador de eventos de selección a la tabla
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener los datos de la fila seleccionada
                    StringBuilder rowData = new StringBuilder();
                    for (int i = 0; i < table1.getColumnCount(); i++) {
                        rowData.append(table1.getColumnName(i)).append(": ").append(table1.getValueAt(selectedRow, i)).append("\n");
                    }
                    // Actualizar el JTextArea con los datos de la fila seleccionada
                    textArea.setText(rowData.toString());
                }
            }
        });



        facturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFacturaPDF1();
            }
        });
    }

    public void Mostrar(){
        //genera columnas de la tabla //Codigo, DNI, Nombre, Apellido, Direccion, Email, Telefono, ID, NombreProd, Unidades, Precio
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Direccion");
        model.addColumn("Apellido");
        model.addColumn("Email");
        model.addColumn("Telefono");
        model.addColumn("ID");
        model.addColumn("NombreProd");
        model.addColumn("Unidades");
        model.addColumn("Precio");


        // Poner las columnas en el modelo hecho en el Jtable
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //arreglo que almnacena datos
        String [] informacion=new String[10];//especifico el numero de columnas

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
                informacion[6]=rs.getString(7);
                informacion[7]=rs.getString(8);
                informacion[8]=rs.getString(9);
                informacion[9]=rs.getString(10);




                // genera una fila por cada ingistro
                model.addRow(informacion);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }
    public void generarFacturaPDF1() {
        // Crear un documento PDF
        Document document = new Document();
        try {
            // Nombre del archivo PDF (puedes cambiarlo según tus necesidades)
            String nombreFactura = "factura.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(nombreFactura));
            document.open();

            // Agregar título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("FACTURA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

// Agregar espacio en blanco
            Paragraph espacioEnBlanco = new Paragraph(" "); // Párrafo vacío
            document.add(espacioEnBlanco);

// Agregar tabla para los datos de la factura
            PdfPTable tablaFactura = new PdfPTable(table1.getColumnCount());
            tablaFactura.setWidthPercentage(100);


            // Agregar encabezados de columna a la tabla
            for (int i = 0; i < table1.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(table1.getColumnName(i)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaFactura.addCell(cell);
            }

            // Agregar datos de la tabla al PDF
            for (int row = 0; row < table1.getRowCount(); row++) {
                for (int col = 0; col < table1.getColumnCount(); col++) {
                    tablaFactura.addCell(table1.getValueAt(row, col).toString());
                }
            }

            document.add(tablaFactura);

            // Agregar fecha y hora al final de la factura
            Paragraph fechaHora = new Paragraph("Fecha y hora de la factura: " + new Date().toString());
            fechaHora.setAlignment(Element.ALIGN_RIGHT);
            document.add(fechaHora);

            document.close();

            JOptionPane.showMessageDialog(null, "Factura generada con éxito: " + nombreFactura);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar la factura.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new mostrar_factura();
        });
    }
}
