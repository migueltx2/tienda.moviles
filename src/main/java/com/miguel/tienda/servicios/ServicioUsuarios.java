package com.miguel.tienda.servicios;

import com.miguel.tienda.model.Usuario;

import java.util.List;

public interface ServicioUsuarios {

    List<Usuario> obtenerUsuarios();

    void registrarUsuario(Usuario usuario);

    void borrarUsuario(long id) throws Exception;

    void guardarCambiosUsuario(Usuario usuarioEditado) throws Exception;

    Usuario obtenerUsuarioPorId(long id);
}
