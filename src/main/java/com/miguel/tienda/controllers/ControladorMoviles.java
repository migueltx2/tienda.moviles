package com.miguel.tienda.controllers;

import com.miguel.tienda.model.Movil;
import com.miguel.tienda.servicios.ServicioCategorias;
import com.miguel.tienda.servicios.ServicioMoviles;
//import com.miguel.tienda.serviciosImpl.ServicioMovilesImplJPA;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import java.sql.SQLOutput;

@Controller
@RequestMapping("admin/")
public class ControladorMoviles {

    //Pido a spring que me de el objeto que tiene de la unica clase que tiene el implements de ServicioMoviles
    //Dicho objeto, es el objeto de la clase que tiene @Service class ServicioMovilesImplJPA implements ServicioMoviles
    @Autowired
    private ServicioMoviles servicioMoviles;
    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarMoviles")
    public String gestionarMoviles(Model model){
        model.addAttribute("moviles", servicioMoviles.obtenerMoviles());
        return "admin/gestionar_productos";
    }

    @RequestMapping("registrarMovil")
    public String registrarMovil (Model model){
        Movil nuevo = new Movil();
        nuevo.setPrecio(1);
        model.addAttribute("nuevo", nuevo);

        //necesito obtener las categorias para meterlas en ese model de spring
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/registro_producto";
    }

    @RequestMapping("guardarMovil")
    public String guardarMovil (@ModelAttribute("nuevo")
                                    @Valid
                                    Movil nuevo,
                                    BindingResult resultadoValidacion,
                                    Model model){
        System.out.println("Movil recibido para registrar");
        if ( resultadoValidacion.hasErrors()){
            //Si el movil tiene errores, mandamos al usuario el formulario de nuevo
            return "admin/registro_producto";
        }

        servicioMoviles.registrarMovil(nuevo);
        return "admin/registro_producto_ok";
    }

    @RequestMapping("borrarMovil")
    public String borrarMovil(@RequestParam("id") Long id, Model model) throws Exception{
        //Repaso: Model model es una variable donde meto lo que quiera que le llegue a la lista indicada en el return

        System.out.println("id recibida para borrar registro" + id);
        servicioMoviles.borrarMovil(id);
        return gestionarMoviles(model);
    }

    @RequestMapping("editarMovil")
    public String editarMovil(@RequestParam("id") Long id, Model model){
        Movil movilEditar = servicioMoviles.obtenerMovilPorId(id);
        if (movilEditar.getCategoria() != null) {
            movilEditar.setIdCategoria(movilEditar.getCategoria().getId());
        }
        model.addAttribute("movilEditar", movilEditar);
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/editar_producto";
    }

    //el metodo va a atender la ruta guardarCambiosVideojuego
    //a esa ruta se llega al hacer click en el GUARDAR CAMBIOS del form de edicion

    @RequestMapping("guardarCambiosMovil")
    public String guardarCambiosMovil(
        @ModelAttribute("movilEditar") //th:object del form
        @Valid //para validar las reglas puestas con @ en Movil.java
        Movil movilEditar, //objeto que contiene lo introducido en el form
        BindingResult resultadoValidacion,
        Model model) throws Exception{ //model en estos metodos, es para meterle lo que queramos que le llegue a la vista de vuelta en el return
              if ( resultadoValidacion.hasErrors()){
                model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
                return "admin/editar_producto";
              } else {
                servicioMoviles.guardarCambiosMovil(movilEditar);
                return gestionarMoviles(model);
              }                          
         }


}//end class
