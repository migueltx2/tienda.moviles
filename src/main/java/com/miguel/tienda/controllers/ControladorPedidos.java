package com.miguel.tienda.controllers;

import com.miguel.tienda.model.Pedido;
import com.miguel.tienda.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "admin/pedidos_detalle";
    }



}
