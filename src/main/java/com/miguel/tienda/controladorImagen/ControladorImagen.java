package com.miguel.tienda.controladorImagen;

import com.miguel.tienda.servicios.ServicioMoviles;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

//Esto es un controlador que sirve para ofrecer la imagen indicada

@Controller
public class ControladorImagen {

    @Autowired
    private ServicioMoviles servicioMoviles;

    @RequestMapping("mostrar_imagen")
    public void mostrarImagen(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        byte[] info = servicioMoviles.obtenerMovilPorId(id).getImagenPortada();
        if (info == null){
            return;
        }
        //emitir info
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/webp ");
        response.getOutputStream().write(info);
        response.getOutputStream().close();
    }



}
