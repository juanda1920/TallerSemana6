/*
    Universidad de las Americas (UDLA) - Campus Park
    Carrera: Ingenieria en Ciberseguridad
    Materia: Programacion III
    Estudiantes: Juan Daniel Acosta, Daniel Argoti, Ignacio Eguez y Bernardo Utreras.
    Proyecto: Taller Semana 6 Implementacion de Metodos de Busqueda

*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Producto[] inventario = new Producto[3]; // Arreglo para almacenar 3 productos
        IBusquedaProductos buscador = new ServicioBusqueda(); // Instancia del buscador

        System.out.println("----- CONFIGURACION INICIAL DE LA TIENDA -----");
        System.out.println("Ingrese los datos de 3 productos.\n");

        // Ciclo para solicitar los datos al usuario
        for (int i = 0; i < inventario.length; i++) {
            System.out.println("--- Registro del Producto " + (i + 1) + " ---");

            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese su precio: ");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el salto de linea que deja nextDouble()

            // ASIGNACION DE ID
            // Al ser i=0, los IDs seran i+1, garantizando el orden.
            int idGenerado = i + 1;

            // Creacion y almacenamiento del objeto Producto en el arreglo
            inventario[i] = new Producto(idGenerado, nombre, precio);
            System.out.println("Producto registrado con exito. ID asignado: " + idGenerado + "\n");
        }

        int opcion = 0;

        // Menu principal
        do {
            System.out.println("\n----- MENU DE LA TIENDA EN LINEA -----");
            System.out.println("1. Mostrar inventario completo");
            System.out.println("2. Buscar producto por ID (Interpolada)");
            System.out.println("3. Buscar producto por Nombre (Lineal)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- INVENTARIO ACTUAL ---");
                    for (int i = 0; i < inventario.length; i++) {
                        inventario[i].mostrarInfo();
                    }
                    break;

                case 2:
                    System.out.print("\nIngrese el ID del producto a buscar: ");
                    int idBuscado = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    // Ejecuta la busqueda interpolada a traves de la interfaz
                    int indiceId = buscador.buscarPorId(inventario, idBuscado);

                    if (indiceId != -1) {
                        // Se guarda la referencia al producto encontrado para facilitar su uso
                        Producto prod = inventario[indiceId];
                        System.out.println("\nProducto encontrado: " + prod.getNombre());

                        // Submenu de acciones para el producto especifico
                        System.out.println("Seleccione una accion para el producto encontrado:");
                        System.out.println("1. Actualizar Precio");
                        System.out.println("2. Registrar Venta Mensual");
                        System.out.println("3. Volver al menu principal");
                        System.out.print("Opcion: ");

                        int subOpcion = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer

                        if (subOpcion == 1) {
                            System.out.print("Ingrese el nuevo precio: $");
                            double nuevoPrecio = scanner.nextDouble();
                            prod.setPrecio(nuevoPrecio); // Actualizacion de precio
                            System.out.println("Precio actualizado.");
                        } else if (subOpcion == 2) {
                            System.out.print("Ingrese el mes (1, 2 o 3): ");
                            int mes = scanner.nextInt();
                            System.out.print("Ingrese la cantidad vendida: ");
                            int cantidad = scanner.nextInt();
                            prod.registrarVenta(mes, cantidad); // Registro de ventas
                        }
                    } else {
                        System.out.println("\nNo se encontro ningun producto con el ID " + idBuscado);
                    }
                    break;

                case 3:
                    System.out.print("\nIngrese el nombre del producto a buscar: ");
                    String nombreBuscado = scanner.nextLine();

                    // Ejecuta la busqueda lineal
                    int indiceNombre = buscador.buscarPorNombre(inventario, nombreBuscado);

                    if (indiceNombre != -1) {
                        System.out.println("\nProducto encontrado:");
                        inventario[indiceNombre].mostrarInfo();
                    } else {
                        System.out.println("\nNo se encontro ningun producto con el nombre '" + nombreBuscado + "'");
                    }
                    break;

                case 4:
                    break;

                default:
                    System.out.println("\nOpcion no valida.");
            }
        } while (opcion != 4);

        scanner.close(); // Se cierra el escaner para liberar recursos
    }
}