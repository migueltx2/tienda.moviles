package com.miguel.tienda.controllers;

import com.miguel.tienda.constantesPedidos.EstadosPedido;
import com.miguel.tienda.model.Pedido;
import com.miguel.tienda.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/")
public class ControladorPedidos {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("gestionarPedidos")
    public String gestionarPedidos(Model model){
        model.addAttribute("pedidos", servicioPedidos.obtenerPedidos());
        return "admin/gestionar_pedidos";
    }

    @RequestMapping("verDetallesPedido")
    public String verDetallesPedidos(@RequestParam("id") Long id, Model model){
        Pedido p = servicioPedidos.obtenerPedidoPorId(id);
        model.addAttribute("pedido", p);

        //Vamos a meter los estados en model para formar un desplegable en pedidos_detalle.html

        Map<String, String> estados = new HashMap<String, String>();
        estados.put(EstadosPedido.Completado.name(), "Completado por el usuario");
        estados.put(EstadosPedido.Enviado.name(), "Enviado a la empresa de mensajeria");
        estados.put(EstadosPedido.Incidencia.name(), "Incidencia en el pedido");
        model.addAttribute("estados", estados);

        return "admin/pedidos_detalle";
    }





}
