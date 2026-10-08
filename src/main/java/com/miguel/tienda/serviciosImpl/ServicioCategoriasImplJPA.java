package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.model.Categoria;
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

}
