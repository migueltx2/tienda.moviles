package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Movil;

import java.util.List;
import java.util.Map;

public interface ServicioCarrito {
    void agregarProducto(long  idUsuario, long idProducto, int cantidad);
    void eliminarProducto(long idProducto, long idUsuario);

    //operaciones que devuelven JSON
    List<Map<String,Object>> obtenerProductosCarrito (long idUsario);

    


}
