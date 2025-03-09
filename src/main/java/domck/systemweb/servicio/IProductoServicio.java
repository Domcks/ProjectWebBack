package domck.systemweb.servicio;

import domck.systemweb.modelo.Producto;
import java.util.List;

public interface IProductoServicio {
    public List<Producto> listarProductos();

    public Producto buscarProductoPorId(Integer id_producto);

    public Producto guardarProducto(Producto producto);

    public void eliminarProductoPorId(Integer id_producto);

}
