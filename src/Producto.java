/*
    Universidad de las Americas (UDLA) - Campus Park
    Carrera: Ingenieria en Ciberseguridad
    Materia: Programacion III
    Estudiantes: Juan Daniel Acosta, Daniel Argoti, Ignacio Eguez y Bernardo Utreras.
    Proyecto: Taller Semana 6 Implementacion de Metodos de Busqueda

*/

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int[] ventasMensuales; // Arreglo para almacenar los ultimos 3 meses

    // Constructor: Inicializa el producto con los datos proporcionados
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.ventasMensuales = new int[3]; // Inicializa las ventas en 0 para 3 meses
    }

    // Metodos Getters para obtener los valores protegidos
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    // Metodo Setter para actualizar el precio
    public void setPrecio(double precio) { this.precio = precio; }

    // Metodo para sumar ventas a un mes en especifico (1, 2 o 3)
    public void registrarVenta(int mes, int cantidad) {
        if (mes >= 1 && mes <= 3) {
            // Se resta 1 al mes porque los arreglos en Java empiezan en el indice 0
            this.ventasMensuales[mes - 1] += cantidad;
            System.out.println("Venta registrada: " + cantidad + " unidades en el mes " + mes);
        } else {
            System.out.println("Error: Mes invalido. Debe ser 1, 2 o 3.");
        }
    }

    // Metodo para imprimir la informacion formateada del producto
    public void mostrarInfo() {
        System.out.println("ID: " + id + " | Nombre: " + nombre + " | Precio: $" + precio +
                " | Ventas: [Mes 1: " + ventasMensuales[0] + ", Mes 2: " +
                ventasMensuales[1] + ", Mes 3: " + ventasMensuales[2] + "]");
    }
}