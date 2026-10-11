package com.miguel.tienda.controllers;

import com.miguel.tienda.model.Categoria;
import com.miguel.tienda.servicios.ServicioCategorias;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/")
public class ControladorCategorias {

    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarCategorias")
    public String gestionarCategorias(Model model){
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/gestionar_categorias";
    }

    @RequestMapping("registrarCategoria")
    public String registrarCategoria (Model model){
        Categoria nuevo = new Categoria();
        model.addAttribute("nuevo", nuevo);

        return "admin/registro_categoria";
    }

    @RequestMapping("guardarCategoria")
    public String guardarCategoria (@ModelAttribute("nuevo")
                                @Valid
                                Categoria nuevo,
                                BindingResult resultadoValidacion,
                                Model model){
        System.out.println("Categoria recibida para registrar");
        if ( resultadoValidacion.hasErrors()){
            //Si la categoria tiene errores, mandamos al usuario el formulario de nuevo
            return "admin/registro_categoria";
        }

        servicioCategorias.registrarCategoria(nuevo);
        return "admin/registro_categoria_ok";
    }

    @RequestMapping("borrarCategoria")
    public String borrarCategoria(@RequestParam("id") Long id, Model model) throws Exception{
        //Repaso: Model model es una variable donde meto lo que quiera que le llegue a la lista indicada en el return

        System.out.println("ID recibida para borrar registro" + id);
        servicioCategorias.borrarCategorias(id);
        return gestionarCategorias(model);
    }

    @RequestMapping("editarCategoria")
    public String editarCategoria(@RequestParam("id") Long id, Model model){
        model.addAttribute("categoriaEditar",
                servicioCategorias.obtenerCategoriaPorId(id));
        return "admin/editar_categoria";
    }

    //el metodo va a atender la ruta guardarCambiosVideojuego
    //a esa ruta se llega al hacer click en el GUARDAR CAMBIOS del form de edicion

    @RequestMapping("guardarCambiosCategoria")
    public String guardarCambiosCategoria(
            @ModelAttribute("categoriaEditar") //th:object del form
            @Valid //para validar las reglas puestas con @ en Movil.java
            Categoria categoriaEditar, //objeto que contiene lo introducido en el form
            BindingResult resultadoValidacion,
            Model model) throws Exception{ //model en estos metodos, es para meterle lo que queramos que le llegue a la vista de vuelta en el return
        if ( resultadoValidacion.hasErrors()){
            return "admin/editar_categoria";
        } else {
            servicioCategorias.guardarCambiosCategoria(categoriaEditar);
            return gestionarCategorias(model);
        }
    }



}
