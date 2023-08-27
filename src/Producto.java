
import java.math.BigDecimal;
public class Producto {
    private int id;
    private String nombre;
    private int unidades;
    private BigDecimal precio;

    public Producto(int id, String nombre, int unidades, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.unidades = unidades;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
