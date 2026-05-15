/*
    Universidad de las Americas (UDLA) - Campus Park
    Carrera: Ingenieria en Ciberseguridad
    Materia: Programacion III
    Estudiantes: Juan Daniel Acosta, Daniel Argoti, Ignacio Eguez y Bernardo Utreras.
    Proyecto: Taller Semana 6 Implementacion de Metodos de Busqueda

*/

public class ServicioBusqueda implements IBusquedaProductos {
    // Implementacion de la Busqueda Interpolada (requiere arreglo ordenado)
    @Override
    public int buscarPorId(Producto[] array, int idBuscado) {
        int inicio = 0;
        int fin = array.length - 1;

        // Condicion del ciclo: el inicio no puede superar el fin y el valor buscado
        // debe estar dentro del rango de los IDs actuales
        while (inicio <= fin && idBuscado >= array[inicio].getId() && idBuscado <= array[fin].getId()) {

            // Prevencion de division por cero si los limites son iguales
            if (inicio == fin) {
                if (array[inicio].getId() == idBuscado) return inicio;
                return -1;
            }

            // Formula de la busqueda interpolada adaptada a los metodos getId()
            int posicion = inicio + ((idBuscado - array[inicio].getId()) * (fin - inicio)) /
                    (array[fin].getId() - array[inicio].getId());

            if (array[posicion].getId() == idBuscado) {
                return posicion; // Se encontro el valor, retorna su indice
            } else if (array[posicion].getId() < idBuscado) {
                inicio = posicion + 1; // El valor buscado es mayor, ajusta el limite inferior
            } else {
                fin = posicion - 1; // El valor buscado es menor, ajusta el limite superior
            }
        }
        return -1; // Retorna -1 si no se encuentra el ID
    }

    // Implementacion de la Busqueda Lineal para cadenas de texto
    @Override
    public int buscarPorNombre(Producto[] array, String nombreBuscado) {
        for (int i = 0; i < array.length; i++) {
            // Compara ignorando mayusculas y minusculas
            if (array[i].getNombre().equalsIgnoreCase(nombreBuscado)) {
                return i; // Retorna el indice si hay coincidencia
            }
        }
        return -1; // Retorna -1 si termina de recorrer y no lo encuentra
    }
}