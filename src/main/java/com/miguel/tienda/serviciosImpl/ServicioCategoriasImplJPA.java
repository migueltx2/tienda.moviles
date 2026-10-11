package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.model.Categoria;
import com.miguel.tienda.model.Movil;
import com.miguel.tienda.servicios.ServicioCategorias;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioCategoriasImplJPA implements ServicioCategorias {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Categoria> obtenerCategorias(){
        return entityManager.createQuery("select v from Categoria v").getResultList();
    }

    @Override
    public void registrarCategoria(Categoria c) {
        entityManager.persist(c);
    }

    @Override
    public void borrarCategorias(long id) throws Exception {
        Categoria categoriaABorrar = entityManager.find(Categoria.class, id);
        if ( categoriaABorrar != null) {
            entityManager.remove(categoriaABorrar);
        } else {
            throw new Exception("ID no encontrada");
        }
    }

    @Override
    public void guardarCambiosCategoria(Categoria categoriaEditada) throws Exception {


        entityManager.merge(categoriaEditada);
    }

    @Override
    public Categoria obtenerCategoriaPorId(long id) {
        return entityManager.find(Categoria.class, id);
    }

}
