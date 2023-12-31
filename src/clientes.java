import java.util.ArrayList;
import java.util.List;
public class clientes {
    private String DNI;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Email;
    private String Telefono;
    private List<String> Clientes;

    public clientes(String DNI, String nombre, String apellido, String direccion, String email, String telefono) {
        this.DNI = DNI;
        Nombre = nombre;
        Apellido = apellido;
        Direccion = direccion;
        Email = email;
        Telefono = telefono;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
