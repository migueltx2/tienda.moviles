package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Categoria;

import java.util.List;

public interface ServicioCategorias {

    List<Categoria> obtenerCategorias();

    void registrarCategoria (Categoria categoria);

    void borrarCategorias(long id) throws Exception;

    void guardarCambiosCategoria(Categoria categoriaEditada) throws Exception;

    Categoria obtenerCategoriaPorId(long id);
}
