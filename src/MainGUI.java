/*
    Universidad de las Americas (UDLA) - Campus Park
    Carrera: Ingenieria en Ciberseguridad
    Materia: Programacion III
    Estudiantes: Juan Daniel Acosta, Daniel Argoti, Ignacio Eguez y Bernardo Utreras.
    Proyecto: Taller Semana 6 Implementacion de Metodos de Busqueda

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
public class MainGUI {
    private static Producto[] inventario = new Producto[3]; // Arreglo para 3 productos
    private static IBusquedaProductos buscador = new ServicioBusqueda(); // Instancia del buscador original
    private static JTextArea txtOutput; // Consola visual interna
    public static void main(String[] args) {
        // 1. CONFIGURACIÓN INICIAL MEDIANTE VENTANAS FLOTANTES
        JOptionPane.showMessageDialog(null,
                "----- CONFIGURACION INICIAL DE LA TIENDA -----\nIngrese los datos de 3 productos.",
                "Configuración Inicial", JOptionPane.INFORMATION_MESSAGE);
        for (int i = 0; i < inventario.length; i++) {
            String nombre = "";
            while (nombre == null || nombre.trim().isEmpty()) {
                nombre = JOptionPane.showInputDialog(null, "--- Producto " + (i + 1) + " ---\nIngrese el nombre del producto:");
                if (nombre == null) System.exit(0); // Si presiona cancelar, cierra de forma segura
            }
            double precio = 0;
            boolean precioValido = false;
            while (!precioValido) {
                try {
                    String precioStr = JOptionPane.showInputDialog(null, "--- Producto " + (i + 1) + " (" + nombre + ") ---\nIngrese su precio:");
                    if (precioStr == null) System.exit(0);
                    precio = Double.parseDouble(precioStr);
                    precioValido = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese un valor numérico válido para el precio.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
            int idGenerado = i + 1;
            inventario[i] = new Producto(idGenerado, nombre, precio);
        }
        // 2. CONSTRUCCIÓN DE LA INTERFAZ GRÁFICA PRINCIPAL (SWING)
        JFrame frame = new JFrame("Tienda en Línea - Panel de Control GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        // Panel de Controles (Superior)
        JPanel panelControles = new JPanel(new GridLayout(3, 1, 8, 8));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Fila 1: Botón Mostrar Inventario
        JButton btnMostrar = new JButton("1. Mostrar Inventario Completo");
        panelControles.add(btnMostrar);
        // Fila 2: Búsqueda por ID (Interpolada)
        JPanel panelId = new JPanel(new BorderLayout(5, 5));
        JTextField txtId = new JTextField();
        JButton btnBuscarId = new JButton("Buscar por ID (Interpolada)");
        panelId.add(new JLabel("ID a buscar: "), BorderLayout.WEST);
        panelId.add(txtId, BorderLayout.CENTER);
        panelId.add(btnBuscarId, BorderLayout.EAST);
        panelControles.add(panelId);
        // Fila 3: Búsqueda por Nombre (Lineal)
        JPanel panelNombre = new JPanel(new BorderLayout(5, 5));
        JTextField txtNombre = new JTextField();
        JButton btnBuscarNombre = new JButton("Buscar por Nombre (Lineal)");
        panelNombre.add(new JLabel("Nombre a buscar: "), BorderLayout.WEST);
        panelNombre.add(txtNombre, BorderLayout.CENTER);
        panelNombre.add(btnBuscarNombre, BorderLayout.EAST);
        panelControles.add(panelNombre);
        // Panel de Consola Visual (Central)
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setBackground(new Color(245, 245, 245));
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Consola de Resultados e Historial"));
        frame.add(panelControles, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        // 3. TRUCO DE MAGIA: Redirigir el System.out de las viejas clases al JTextArea
        PrintStream printStream = new PrintStream(new InterceptorConsola(txtOutput));
        System.setOut(printStream);
        System.setErr(printStream);
        // 4. LÓGICA DE EVENTOS (LISTENERS)
        // Acción: Mostrar Inventario Completo
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText(""); // Limpiar pantalla anterior
                System.out.println("--- INVENTARIO ACTUAL ---");
                for (int i = 0; i < inventario.length; i++) {
                    inventario[i].mostrarInfo(); // Llama al método original que usa System.out
                }
            }
        });
        // Acción: Buscar por ID (Interpolada) + Submenú
        btnBuscarId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                try {
                    int idBuscado = Integer.parseInt(txtId.getText().trim());
                    int indiceId = buscador.buscarPorId(inventario, idBuscado);
                    if (indiceId != -1) {
                        Producto prod = inventario[indiceId];
                        System.out.println("Producto encontrado: " + prod.getNombre());
                        // Simulación del Submenú interactivo usando un cuadro de opciones gráfico
                        String[] opciones = {"1. Actualizar Precio", "2. Registrar Venta Mensual", "3. Volver"};
                        int seleccion = JOptionPane.showOptionDialog(frame,
                                "Seleccione una acción para el producto: " + prod.getNombre(),
                                "Submenú Operativo - Acción Requerida",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, opciones, opciones[0]);
                        if (seleccion == 0) { // Actualizar precio
                            String nuevoPrecioStr = JOptionPane.showInputDialog(frame, "Ingrese el nuevo precio para " + prod.getNombre() + ":");
                            if (nuevoPrecioStr != null) {
                                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                                prod.setPrecio(nuevoPrecio);
                                System.out.println("Precio actualizado con éxito.");
                            }
                        } else if (seleccion == 1) { // Registrar venta
                            String mesStr = JOptionPane.showInputDialog(frame, "Ingrese el mes (1, 2 o 3):");
                            String cantStr = JOptionPane.showInputDialog(frame, "Ingrese la cantidad vendida:");
                            if (mesStr != null && cantStr != null) {
                                int mes = Integer.parseInt(mesStr);
                                int cantidad = Integer.parseInt(cantStr);
                                prod.registrarVenta(mes, cantidad); // Llama al método original
                            }
                        }
                    } else {
                        System.out.println("No se encontro ningun producto con el ID " + idBuscado);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número de ID válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Acción: Buscar por Nombre (Lineal)
        btnBuscarNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                String nombreBuscado = txtNombre.getText().trim();
                if (nombreBuscado.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, digite un nombre en el campo de texto.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int indiceNombre = buscador.buscarPorNombre(inventario, nombreBuscado);
                if (indiceNombre != -1) {
                    System.out.println("Producto encontrado:");
                    inventario[indiceNombre].mostrarInfo(); // Llama al método original
                } else {
                    System.out.println("No se encontro ningun producto con el nombre '" + nombreBuscado + "'");
                }
            }
        });
        // Mostrar la ventana flotante principal
        frame.setVisible(true);
    }
    // Clase auxiliar interna para capturar los flujos de System.out.println
    private static class InterceptorConsola extends OutputStream {
        private JTextArea textArea;
        public InterceptorConsola(JTextArea textArea) { this.textArea = textArea; }
        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll hacia abajo
        }
    }
}