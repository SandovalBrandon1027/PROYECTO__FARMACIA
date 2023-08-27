import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class registro extends JFrame{
    PreparedStatement ps;
    DefaultListModel mod=new DefaultListModel();
    Statement st;
    ResultSet r;
    private JPanel panel;
    private JTextField DNItext;
    private JTextField NombreText;
    private JTextField ApellidoText;
    private JTextField DireccionText;
    private JTextField EmailText;
    private JTextField TelefonoText;
    private JList list1;
    private JButton mostrarButton;
    private JButton guardarButton;
    private JButton elminarButton;
    private JButton actualizarButton;
    private Connection con;




    public registro(){
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Listar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    insertar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });


        setTitle("Farmacia su economia");
        setContentPane(panel);
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void Listar() throws SQLException {
        conectar();
        list1.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("SELECT DNI, Nombre, Apelldo, Direccion, Email, Telefono FROM registro");

        mod.removeAllElements();
        while (r.next()){

            mod.addElement(r.getString(1)+"  ║  "+r.getString(2)+"  ║  "+r.getString(3)+"  ║  "+r.getString(4)+"  ║  "+r.getString(5)+"  ║  "+r.getString(6));

        }

    }


    public void insertar()throws SQLException{
        conectar();
        ps = con.prepareStatement("INSERT INTO registro (DNI, Nombre, Apelldo, Direccion, Email, Telefono) VALUES (?,?,?,?,?,?)");

        ps.setString(1,DNItext.getText());
        ps.setString(2,NombreText.getText());
        ps.setString(3,ApellidoText.getText());
        ps.setString(4,DireccionText.getText());
        ps.setString(5,EmailText.getText());
        ps.setString(6,TelefonoText.getText());
        if(ps.executeUpdate()>0){
            list1.setModel(mod);
            mod.removeAllElements();
            mod.addElement("! Insercion exitosa !");
            DNItext.setText("");
            NombreText.setText("");
            ApellidoText.setText("");
            DireccionText.setText("");
            EmailText.setText("");
            TelefonoText.setText("");

        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registro();
        });

    }

    public void conectar(){
        try{



            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usuarios","root","");
            System.out.println("Conectado");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
