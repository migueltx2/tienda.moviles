package com.miguel.tienda.controladoresREST;


import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioCarrito;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restCarrito/")
public class CarritoREST {

    @Autowired
    private ServicioCarrito servicioCarrito;

    @RequestMapping("agregarProducto")
    public String agregarProducto(String id, @RequestParam("cantidad") String cantidad, HttpServletRequest request){
        String respuesta = "";
        //Comprobar que el usuario esta identificado...
        //La informacion del usuario esta metida en una variable de sesion -> Consultar UsuariosREST/identificarUsuario
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        respuesta = "producto agregado: id producto (consultar h2): " + id + " cantidad: " + cantidad + " al usuario de id: " + u.getId();
        servicioCarrito.agregarProducto(u.getId(), Long.parseLong(id), Integer.parseInt(cantidad));
        return respuesta;
    }

    @RequestMapping("obtenerProductos")
    public List<Map<String,Object>> obtenerProductos(HttpServletRequest request){
        return servicioCarrito.obtenerProductosCarrito(
                ((Usuario)request.getSession().getAttribute("usuario_identificado")).getId()
        );
    }

    @RequestMapping("borrarProducto")
    public String borrarProducto(@RequestParam("id") long idProducto, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioCarrito.eliminarProducto(idProducto, usuario.getId());
        return "ok";
    }



}
