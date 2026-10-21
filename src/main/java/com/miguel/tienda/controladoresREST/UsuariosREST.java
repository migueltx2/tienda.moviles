package com.miguel.tienda.controladoresREST;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioUsuarios;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("restUsuarios/")
public class UsuariosREST {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @RequestMapping("registrarUsuario")
    public String registrarUsuario(@RequestParam Map<String,Object> formData){
        System.out.println("RECIBIDO FORM DATA" + formData);
        //formData tiene la info del usuario a registrar
        //Hay que transformar el formData a Usuario, vamos a hacerlo con la libreria gson
        Gson gson = new Gson();
        JsonElement json = gson.toJsonTree(formData);
        Usuario u = gson.fromJson(json, Usuario.class);

        servicioUsuarios.registrarUsuario(u);
        return "Te has registrado correctamente, ya puedes identificarte";

    }

    //Recordatorio: Todos estos metodos se llaman desde el js de index.html
    @RequestMapping("identificarUsuario")
    public String identificarUsuario(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            HttpServletRequest request){
        //Comprobar si hay un usuario con este email y pass en la base de datos
        //Si es asi, los datos son correctos
        //Marcamos al usuario en cuestion que esta identificado
        //De administracio
        Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
        String respuesta = "";
        if ( u != null ){
            request.getSession().setAttribute("usuario_identificado", u);
            respuesta = "Ok," + u.getNombre();
        } else {
            respuesta = "email o pass incorrectos";
        }
        return  respuesta;
    }


}
