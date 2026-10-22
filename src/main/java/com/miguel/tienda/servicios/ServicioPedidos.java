package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Pedido;
import com.miguel.tienda.model.ResumenPedido;

import java.util.List;

public interface ServicioPedidos {
    //Gestion en administracion
    List<Pedido> obtenerPedidos();
    Pedido obtenerPedidoPorId(long id);
    void actualizarEstadoPedido(long id, String estado);


    //Operaciones para la parte publica - ajax/api rest
    void procesarPaso1(String nombreCompleto, String direccion, String provincia, long idUsuario);
    void procesarPaso2(String titular, String numero, String tipoTarjeta, long idUsuario);
    ResumenPedido obtenerResumenPedido(long idUsuario);
    void confirmarPedido(long idUsuario);



}
