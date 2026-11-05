package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Movil;

//import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface ServicioMoviles {

    void registrarMovil (Movil movil);

    List<Movil> obtenerMoviles();

    List<Movil> obtenerMovilesPorTitulo(String titulo);

    List<Movil> obtenerMovilesPorTituloYcomienzoYfin(String titulo, int comienzo, int resultadosPorPagina);

    List<Movil> obtenerMovilesPorTitulo(String titulo, int comienzo, int resultadosPorPagina);

    Movil obtenerMovilPorId(long id);

    void borrarMovil(long id) throws Exception;

    void guardarCambiosMovil(Movil movilEditar) throws Exception;

    List<Map<String, Object>> obtenerJSONmoviles(String titulo, int comienzo);

    Map<String,Object> obtenerJSONMovilPorId(Long id);

    int obtenerTotalMoviles(String nombre);
}
