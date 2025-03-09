package domck.systemweb.servicio;


import domck.systemweb.modelo.Producto;
import domck.systemweb.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoServicio implements IProductoServicio{

    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Override
    public List<Producto> listarProductos() {
       return this.productoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer id_producto) {
        Producto producto =
                this.productoRepositorio.findById(id_producto).orElse(null);
        return producto;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        if (producto.getId_producto() == null) {
            return this.productoRepositorio.save(producto);
        } else {
            Producto productoExistente = this.productoRepositorio.findById(producto.getId_producto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            productoExistente.setNombre_producto(producto.getNombre_producto());
            productoExistente.setDescripcion_producto(producto.getDescripcion_producto());
            productoExistente.setPrecio_producto(producto.getPrecio_producto());
            productoExistente.setStock_producto(producto.getStock_producto());
            productoExistente.setImagen_url(producto.getImagen_url());

            return this.productoRepositorio.save(productoExistente);
        }
    }

    @Override
    public void eliminarProductoPorId(Integer id_producto) {
    this.productoRepositorio.deleteById(id_producto);
    }
}
