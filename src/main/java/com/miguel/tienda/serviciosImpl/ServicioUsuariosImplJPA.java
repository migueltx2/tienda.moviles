package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.model.Usuario;
import com.miguel.tienda.servicios.ServicioUsuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    @Override
    public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
        Query query = entityManager.createQuery("select u from Usuario u WHERE u.email = :email and u.pass = :pass");
        //La anterior consulta de JPA seria como la SQL: Select * from usuario where email = ? and pass = ?
        query.setParameter("email", email); //Asi asigno el :email
        query.setParameter("pass", pass); //Asi asigno el :pass
        List<Usuario> res = query.getResultList();

        if (res.size() == 0){
            return null;
        }

        return res.get(0);
    }
}
