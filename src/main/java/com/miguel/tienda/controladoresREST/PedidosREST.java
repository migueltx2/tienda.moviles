package com.miguel.tienda.controladoresREST;


import com.miguel.tienda.model.ResumenPedido;
import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioPedidos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("restPedido/")

public class PedidosREST {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("procesarPaso1")
    public String paso1(String nombre, String direccion, String provincia, String codigoPostal, String ciudad, String correo_electronico, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");

        servicioPedidos.procesarPaso1(nombre, direccion, provincia, codigoPostal, ciudad, correo_electronico, u.getId());
        return "Ok";
    }

    @RequestMapping("procesarPaso2")
    public ResumenPedido paso2(String tarjeta, String numero, String titular, String cvv, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso2(titular, numero, tarjeta, cvv, u.getId());
        return servicioPedidos.obtenerResumenPedido(u.getId());
    }

    @RequestMapping("procesarPaso3")
    public String procesarPaso3(String eleccion, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso3(eleccion, u.getId());
        return "ok";
    }

    @RequestMapping("procesarPaso4")
    public String procesarPaso4(HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.confirmarPedido(u.getId());
        return "ok";
    }





}
