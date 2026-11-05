package com.miguel.tienda.interceptores;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//Un metodo de esta clase, se va a ejecutar antes de cualquiero accesso a admin/

// @Component hace que cuando arranque la aplicacion haya un objeto de esta clase en el sistema controlado por spring
@Component
public class InterceptorAdmin implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Aqui pongo el codigo que se va a ejecutar antes


        String passAdmin = ""; //Va a ser un pass que el usuario tiene que introducir
        //Si el usuario inserto una contraseña en el login de admin:
        if ( request.getParameter("pass-login-admin") != null){
            passAdmin = request.getParameter("pass-login-admin");
            if ( passAdmin.equals("123")){
                //guardo en una variable de sesion que el usuario actual es un admin
                request.getSession().setAttribute("admin", "ok");
            }
        }

        //Este interceptor se va a ejecutar antes del acceso a cualquier ruta. Ver si se quiera acceder a /admin

        //System.out.println("Hola desde el interceptor");
        if (request.getRequestURI().contains("/admin/")){
            //miro si la variable de sesion admin existe y vale ok, eso quiere decir que el usuario introdujo correctamente
            //el pass en el form de login_admin

            if ( !
                    (request.getSession().getAttribute("admin") !=null
                    && request.getSession().getAttribute("admin").equals("ok"))
            ) {
                //Si en sesion no esta este admin ok, redirecciono al form de login
                response.sendRedirect("../loginAdmin");
                return false;
            }

        }

        //return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }
}
