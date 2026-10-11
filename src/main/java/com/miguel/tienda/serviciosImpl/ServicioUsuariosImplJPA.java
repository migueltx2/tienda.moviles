package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioUsuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioUsuariosImplJPA implements ServicioUsuarios {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> obtenerUsuarios() {
        return entityManager.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        entityManager.persist(usuario);
    }

    @Override
    public void borrarUsuario(long id) throws Exception {
        Usuario usuarioABorrar = entityManager.find(Usuario.class, id);
        if (usuarioABorrar != null) {
            entityManager.remove(usuarioABorrar);
        } else {
            throw new Exception("ID no encontrada");
        }
    }

    @Override
    public void guardarCambiosUsuario(Usuario usuarioEditado) throws Exception {
        if (entityManager.find(Usuario.class, usuarioEditado.getId()) == null) {
            throw new Exception("ID no encontrada");
        }
        entityManager.merge(usuarioEditado);
    }

    @Override
    public Usuario obtenerUsuarioPorId(long id) {
        return entityManager.find(Usuario.class, id);
    }
}
