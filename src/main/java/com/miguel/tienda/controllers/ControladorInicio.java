package com.miguel.tienda.controllers;

import com.miguel.tienda.servicios.ServicioSetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorInicio {

    //Cuando se de la pagina de inicio
    //Llamamos al ServicioSetUp para que prepare los registros iniciales si es necesario
    @Autowired
    private ServicioSetUp servicioSetUp;

    @RequestMapping()
    public String inicio(){

        //vamos a llamar a un servicio para asegurar que tengamos unos productos y unas categorias en la base de datos
        servicioSetUp.prepararRegistrosIniciales();




        return "index";
    }

}
