// Sirve como un plano que obliga a implementar los metodos de busqueda
interface IBusquedaProductos {
    int buscarPorId(Producto[] inventario, int idBuscado);
    int buscarPorNombre(Producto[] inventario, String nombreBuscado);
}