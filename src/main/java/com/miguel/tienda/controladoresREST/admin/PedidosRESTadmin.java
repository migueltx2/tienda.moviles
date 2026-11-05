package com.miguel.tienda.controladoresREST.admin;


import com.miguel.tienda.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/restPedido/")
public class PedidosRESTadmin {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("actualizarEstadoPedido")
        public String actualizarEstadoPedido(@RequestParam("id") int id, String estado){
            servicioPedidos.actualizarEstadoPedido(id, estado);
            return "ok";
        }
    }
