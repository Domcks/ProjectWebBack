package domck.systemweb.controlador;
import domck.systemweb.controlador.excepcion.RecursoNoEncontradoExcepcion;
import domck.systemweb.modelo.Producto;
import domck.systemweb.repositorio.ProductoRepositorio;
import domck.systemweb.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")

public class ProductoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoControlador.class);
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoServicio.listarProductos();
        logger.info("Productos obtenidos:");
        productos.forEach((producto -> logger.info(producto.toString())));
        return productos;
    }
    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Producto a agregar" + producto);
        return this.productoServicio.guardarProducto(producto);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(
            @PathVariable int id){
        Producto producto =
                this.productoServicio.buscarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }else
            throw  new RecursoNoEncontradoExcepcion("No se encontro el ID:"+ id);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el ID:"+ id);
        producto.setNombre_producto(productoRecibido.getNombre_producto());
        producto.setDescripcion_producto(productoRecibido.getDescripcion_producto());
        producto.setPrecio_producto(productoRecibido.getPrecio_producto());
        producto.setStock_producto(productoRecibido.getStock_producto());
        producto.setImagen_url(productoRecibido.getImagen_url());
        producto.setCategoria_id(productoRecibido.getCategoria_id());
        this.productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarProducto(@PathVariable int id){
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el ID:"+ id);

        this.productoServicio.eliminarProductoPorId(producto.getId_producto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
