package com.miguel.tienda.controllers;

import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioUsuarios;
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
public class ControladorUsuarios {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @RequestMapping("gestionarUsuarios")
    public String gestionarUsuarios(Model model) {
        model.addAttribute("usuarios", servicioUsuarios.obtenerUsuarios());
        return "admin/gestionar_usuarios";
    }

    @RequestMapping("registrarUsuario")
    public String registrarUsuario(Model model) {
        model.addAttribute("nuevo", new Usuario());
        return "admin/registro_usuario";
    }

    @RequestMapping("guardarUsuario")
    public String guardarUsuario(
            @ModelAttribute("nuevo") @Valid Usuario nuevo,
            BindingResult resultadoValidacion) {
        if (resultadoValidacion.hasErrors()) {
            return "admin/registro_usuario";
        }

        servicioUsuarios.registrarUsuario(nuevo);
        return "admin/registro_usuario_ok";
    }

    @RequestMapping("borrarUsuario")
    public String borrarUsuario(@RequestParam("id") Long id, Model model) throws Exception {
        servicioUsuarios.borrarUsuario(id);
        return gestionarUsuarios(model);
    }

    @RequestMapping("editarUsuario")
    public String editarUsuario(@RequestParam("id") Long id, Model model) {
        model.addAttribute("usuarioEditar", servicioUsuarios.obtenerUsuarioPorId(id));
        return "admin/editar_usuario";
    }

    @RequestMapping("guardarCambiosUsuario")
    public String guardarCambiosUsuario(
            @ModelAttribute("usuarioEditar") @Valid Usuario usuarioEditar,
            BindingResult resultadoValidacion,
            Model model) throws Exception {
        if (resultadoValidacion.hasErrors()) {
            return "admin/editar_usuario";
        }

        servicioUsuarios.guardarCambiosUsuario(usuarioEditar);
        return gestionarUsuarios(model);
    }
}
