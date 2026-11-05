package com.miguel.tienda;

import com.miguel.tienda.interceptores.InterceptorAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

//Aqui vamos a configurar varias cosas entre ellas el interceptor
@Component
public class Config implements WebMvcConfigurer {

    @Autowired
    private InterceptorAdmin interceptorAdmin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorAdmin);
        registry.addInterceptor(localeChangeInterceptor());
    }

    //Con @Bean indicamos que el siguiente metodo devuelve una bean de spring
    @Bean
    public LocaleResolver localeResolver(){
        //Al tener @Bean en este metodo,
        // Spring va a meter lo que se devuelva en el return en el contenedor de Spring

        //Para que queremos un LocaleResolver?
        //Sirve para resolver en que idioma mostrar la app web
        //Vamos a decir que el cambio de idioma se mantenga en sesion
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setIgnoreInvalidLocale(true);
        localeChangeInterceptor.setParamName("idioma");
        return localeChangeInterceptor;
    }


}
