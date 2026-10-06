package com.miguel.tienda.controladoresREST;

import com.miguel.tienda.model.Movil;
import com.miguel.tienda.servicios.ServicioMoviles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Un servicio rest se le puede llamar REST


//@RestController no devuelven vistas/html sino json
@RestController
@RequestMapping("restMoviles/")
public class MovilesREST {

    @Autowired
    private ServicioMoviles servicioMoviles;

    @RequestMapping("listar")
    public List<Movil> listar(){
        return servicioMoviles.obtenerMoviles();
    }

    //Para testear: localhost:8080/restMoviles/listar

    @RequestMapping("obtener-movil")
    public Movil obtenerMovil(@RequestParam("id") Long id){
        //estos return de un @RestCOntroller devuelven un json
        return servicioMoviles.obtenerMovilPorId(id);
    }


}
