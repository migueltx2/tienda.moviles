package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.constantesSQL.ConstantesSQL;
import com.miguel.tienda.model.Categoria;
import com.miguel.tienda.model.Movil;
import com.miguel.tienda.servicios.ServicioMoviles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service //marcas que la clase actua como servicio de datos
@Transactional // los metodos de la clase se ejecutan como una unica transaccion
public class ServicioMovilesImplJPA implements ServicioMoviles {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarMovil(Movil m) {
        //El movil ahora mismo tiene la fotoSubida, pero en imagenPortarda no tiene nada
        try {
            m.setImagenPortada(m.getFotoSubida().getBytes());
        } catch (IOException e) {
            System.out.println("NO PUEDE PROCESAR LA FOTO SUBIDA");

            throw new RuntimeException(e);
        }

        // de la idCategoria recibida, hay que asignar la categoria de la base de datos
        Categoria c = entityManager.find(Categoria.class, m.getIdCategoria());
        m.setCategoria(c);

        entityManager.persist(m);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movil> obtenerMoviles() {
        //lanzar JPQL
        //La consulta lanzada equivale al sql: select * from tabla_moviles as m
        return entityManager.createQuery("select m from Movil m").getResultList();
    }

    @Override
    public Movil obtenerMovilPorId(long id) {
        return entityManager.find(Movil.class, id);
    }

    @Override
    public void borrarMovil(long id) throws Exception  {
        //En JPA la forma mas comun de borrar un registro, es pedirlo, comprobar que existe, y luego borrarlo
        Movil movilAborrar = entityManager.find(Movil.class, id);
        if ( movilAborrar != null) {
            entityManager.remove(movilAborrar);
        } else {
            throw new Exception("id no encontrada");
        }

    }

    @Override
    public void guardarCambiosMovil(Movil movilEditar) throws Exception {
        //para no perder la foto hay que detectar si el admin le ha asignado al movil una nuvea foto,
        // en caso contrario le vamos a cargar la foto anterior que tuviese
        if (movilEditar. getFotoSubida().getSize() == 0){
            Movil original = entityManager.find(Movil.class, movilEditar.getId());
            movilEditar.setImagenPortada(original.getImagenPortada());
        } else {
            movilEditar.setImagenPortada(
                movilEditar.getFotoSubida().getBytes()
            );
        }

        entityManager.merge(movilEditar);
        //merge actualiza el registro si ya existe
        //si no existe lo crea
    }

    @Override
    public List<Map<String, Object>> obtenerJSONmoviles() {
        //Con JPA hemos asociado categoria a producto
        //Problema: El json recibido en portada  no es correcto
        //Solucion: Dejar claro en una consulta a base de datos que informacion va a formar el json que necesitamos importar

        Map<String,Object> json = new HashMap<>();
        json.put("nombre", "GTAV");
        json.put("precio", 45);
        // json.put(56,50); //da error porque lo primero no es un string
        //para tener el control total, vamos a usar SQL
        Query consulta = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_MOVILES_JSON);
        //usar la consulta directamente es mucho mas lioso
        //que lo siguiente
        NativeQueryImpl nativeQuery = (NativeQueryImpl) consulta;
        nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);



        return nativeQuery.getResultList();
    }

    @Override
    public Map<String, Object> obtenerJSONMovilPorId(Long id) {
        return (Map<String, Object>) ((NativeQueryImpl)entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_MOVIL_JSON)).setParameter("id", id).setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE).getResultList().get(0);
    }


}
