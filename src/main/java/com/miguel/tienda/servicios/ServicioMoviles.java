package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Movil;

//import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface ServicioMoviles {

    void registrarMovil (Movil movil);

    List<Movil> obtenerMoviles();

    Movil obtenerMovilPorId(long id);

    void borrarMovil(long id) throws Exception;

    void guardarCambiosMovil(Movil movilEditar) throws Exception;

    List<Map<String, Object>> obtenerJSONmoviles();

    Map<String,Object> obtenerJSONMovilPorId(Long id);
}
