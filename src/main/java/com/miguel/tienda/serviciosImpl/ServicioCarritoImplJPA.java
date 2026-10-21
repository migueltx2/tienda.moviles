package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.constantesSQL.ConstantesSQL;
import com.miguel.tienda.model.Carrito;
import com.miguel.tienda.model.Movil;
import com.miguel.tienda.model.ProductoCarrito;
import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioCarrito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioCarritoImplJPA implements ServicioCarrito {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void agregarProducto(long idUsuario, long idProducto, int cantidad) {
        Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);
        //Esto da problemas
        //Carrito c = uBaseDatos.getCarrito();
        //Esto es mas seguro
        Carrito c = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        if ( c == null ){
            //Si el usuario no tiene un carrito asociado, lo creamos y lo guardamos en DB
            c = new Carrito();
            c.setUsuario(uBaseDatos);
            entityManager.persist(c);
        }
        //Ver si el producto que se quiere agregar ya esta en el carrito
        //Si lo esta, hay que incrementar su cantidad
        boolean productoEnCarrito = false;
        for(ProductoCarrito pc : c.getProductosCarrito()){
            if (pc.getMovil().getId() == idProducto){
                //El producto ya esta en el carrito
                productoEnCarrito = true;
                pc.setCantidad(pc.getCantidad() + cantidad);
                entityManager.persist(pc);
            }
        }
        //Si el producto no esta en el carrito, creamos un nuevo ProductoCarrito
        if ( ! productoEnCarrito ){
            ProductoCarrito pc = new ProductoCarrito();
            pc.setCarrito(c);
            pc.setMovil( entityManager.find(Movil.class, idProducto));
            pc.setCantidad(cantidad);
            entityManager.persist(pc);

        }
    }


    @Override
    public List<Map<String, Object>> obtenerProductosCarrito(long idUsuario) {
        //Sacar los productos del carrito a partir de la id de usuario
        //Usuario u = (Usuario) entityManager.find(Usuario.class, idUsario);
        //Carrito c = u.getCarrito();
        Carrito c = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        List<Map<String, Object>> res = null;
        if (c != null){
            Query query = entityManager.createNativeQuery(
                    ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO_JSON
            );
            query.setParameter("carrito_id", c.getId());

            NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
            nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            res = nativeQuery.getResultList();
        }


        return res;
    }

    @Override
    public void eliminarProducto(long idProducto, long idUsuario) {
        entityManager.createQuery(
                        "DELETE FROM ProductoCarrito pc " +
                                "WHERE pc.movil.id = :idProducto " +
                                "AND pc.carrito.id IN (" +
                                "SELECT c.id FROM Carrito c WHERE c.usuario.id = :idUsuario)")
                .setParameter("idProducto", idProducto)
                .setParameter("idUsuario", idUsuario)
                .executeUpdate();
    }
}
