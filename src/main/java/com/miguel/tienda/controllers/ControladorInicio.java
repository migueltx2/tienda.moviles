package com.miguel.tienda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorInicio {

    @RequestMapping()
    public String inicio(){
        return "index";
    }

}
