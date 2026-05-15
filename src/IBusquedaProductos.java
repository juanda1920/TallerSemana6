/*
    Universidad de las Americas (UDLA) - Campus Park
    Carrera: Ingenieria en Ciberseguridad
    Materia: Programacion III
    Estudiantes: Juan Daniel Acosta, Daniel Argoti, Ignacio Eguez y Bernardo Utreras.
    Proyecto: Taller Semana 6 Implementacion de Metodos de Busqueda

*/

// Sirve como un plano que obliga a implementar los metodos de busqueda
interface IBusquedaProductos {
    int buscarPorId(Producto[] inventario, int idBuscado);
    int buscarPorNombre(Producto[] inventario, String nombreBuscado);
}