package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.constantesPedidos.EstadosPedido;
import com.miguel.tienda.constantesSQL.ConstantesSQL;
import com.miguel.tienda.model.*;
import com.miguel.tienda.servicios.ServicioCarrito;
import com.miguel.tienda.servicios.ServicioPedidos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioPedidosImplJPA implements ServicioPedidos {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServicioCarrito servicioCarrito;

    @Override
    public List<Pedido> obtenerPedidos() {
        return List.of();
    }

    @Override
    public Pedido obtenerPedidoPorId(long id) {
        return null;
    }

    @Override
    public void actualizarEstadoPedido(long id, String estado) {

    }



    //Operaciones con la parte publica

    @Override
    public void procesarPaso1(String nombreCompleto, String direccion, String provincia, long idUsuario) {
        //Cada usuario podra tener como maximo un solo pedido en estado en proceso
        //Ese pedido es donde rellenaremos los datos de envio
        //Pago y demas, si hubiera.
        //Si el usuario finaliza el pedido, el estado del pedido pasa a ser completado.
        //Puede haber tantos pedidos completado como se quiera
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setNombreCompleto(nombreCompleto);
        p.setDireccion(direccion);
        p.setProvincia(provincia);
        entityManager.merge(p); //Actualiza o registra
    }



    @Override
    public void procesarPaso2(String titular, String numero, String tipoTarjeta, long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setTitularTarjeta(titular);
        p.setNumeroTarjeta(numero);
        p.setTipoTarjeta(tipoTarjeta);
        entityManager.merge(p);
    }

    @Override
    public ResumenPedido obtenerResumenPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        ResumenPedido resumen = new ResumenPedido();
        resumen.setNombreCompleto(p.getNombreCompleto());
        resumen.setDireccion(p.getDireccion());
        resumen.setProvincia(p.getProvincia());
        resumen.setTipoTarjeta(p.getTipoTarjeta());
        resumen.setTitularTarjeta(p.getTitularTarjeta());
        resumen.setNumeroTarjeta(p.getNumeroTarjeta());
        resumen.setMoviles(servicioCarrito.obtenerProductosCarrito(idUsuario));

        return null;
    }

    @Override
    public void confirmarPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        Carrito c = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        if (c != null && c.getProductosCarrito().size() > 0){
            for (ProductoCarrito pc : c.getProductosCarrito()){
                ProductoPedido pp = new ProductoPedido();
                pp.setCantidad(pc.getCantidad());
                pp.setMovil(pc.getMovil());
                p.getProductosPedido().add(pp);
                pp.setPedido(p);
                entityManager.persist(pp);
            }
        }
        //Borrar los productos del carrito del usuario
        entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO).setParameter("carrito_id", c.getId()).executeUpdate();
        p.setEstado(EstadosPedido.Completado.toString());


    }

    private Pedido obtenerPedidoActual(long idUsuario){

        //Vamos a pedir del usuario actual todos los pedidos
        Query consulta = entityManager.createQuery("select p from Pedido p where p.estado = :estado AND p.usuario.id = :usuario_id")
                .setParameter("estado", EstadosPedido.EnProceso.name())
                .setParameter("usuario_id", idUsuario);
        List<Pedido> resultadoConsulta = consulta.getResultList();
        if (resultadoConsulta.size() == 1) {
            return resultadoConsulta.get(0);
        }
        //Si el metodo sigue para adelante hay que crear un nuevo pedido con el estado en proceso
        Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);
        Pedido pedidoEnProceso = new Pedido();
        pedidoEnProceso.setUsuario(uBaseDatos);
        pedidoEnProceso.setEstado(EstadosPedido.EnProceso.toString());
        return pedidoEnProceso;
    }
}
