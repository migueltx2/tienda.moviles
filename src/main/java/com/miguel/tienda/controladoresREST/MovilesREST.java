package com.miguel.tienda.controladoresREST;

import com.miguel.tienda.model.Movil;
import com.miguel.tienda.servicios.ServicioMoviles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//Un servicio rest se le puede llamar REST


//@RestController no devuelven vistas/html sino json
@RestController
@RequestMapping("restMoviles/")
public class MovilesREST {

    @Autowired
    private ServicioMoviles servicioMoviles;

    //@RequestMapping("listar")
    //public List<Movil> listar(){
    //    return servicioMoviles.obtenerMoviles();
    //}

    @RequestMapping("listar")
    public List<Map<String,Object>> listar(){
        return servicioMoviles.obtenerJSONmoviles();
    }

    //Ahora que se ha complicado un poco, vamos a tomar mas control en deovlver el json con el listado de videojuegos


    //Para testear: localhost:8080/restMoviles/listar

    @RequestMapping("obtener-movil")
    public Map<String,Object> obtenerMovil(@RequestParam("id") Long id){
        //estos return de un @RestCOntroller devuelven un json
        return servicioMoviles.obtenerJSONMovilPorId(id);
    }


}
