package com.miguel.tienda.serviciosImpl;

import com.miguel.tienda.model.*;
import com.miguel.tienda.servicios.ServicioSetUp;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;


@Service
@Transactional
public class ServicioSetUpImplJPA implements ServicioSetUp {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void prepararRegistrosIniciales() {
        //preparar unos registros si la base de datos esta vacia
        Configuracion configuracion = null;

        // la idea presentada se basa en que va a haber un solo registro en la tabla configuracion
        // Ese registro nos indicara si la base de datos ya tiene sus registros iniciales
        // Como por ejemplo productos, categorias, usuarios, pedidos, etc.

        // En SQL seria como: select * from configuracion as c

        try {
            configuracion = (Configuracion) entityManager.createQuery("select c from Configuracion c").getSingleResult();
        }catch (NoResultException e){
            System.out.println("No se encontro un unico registro en la tabla de configuracion");
        }
        if (configuracion == null || ! configuracion.isRegistrosiIniciales()){
            //Hago todos los registros iniciales
            // Categorias
            Categoria samsung = new Categoria("Samsung", "Samsung");
            Categoria apple = new Categoria("Apple", "Apple");
            Categoria xiaomi = new Categoria("Xiaomi", "Xiaomi");

            entityManager.persist(samsung);
            entityManager.persist(apple);
            entityManager.persist(xiaomi);

            //Productos

            //El primer registro tendra ID 1
            Movil movil1 = new Movil("Samsung Galaxy S26", "SM079", 516, 16, 150, 899, "MOVIL-001", null, null);movil1.setCategoria(samsung);movil1.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/1.jpg"));entityManager.persist(movil1);

            Movil movil2 = new Movil("iPhone 16", "APPLEPHONE016", 256, 8, 112, 799, "MOVIL-002", null, null);
            movil2.setCategoria(apple);
            movil2.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/2.jpg"));
            entityManager.persist(movil2);

            Movil movil3 = new Movil("Samsung Galaxy S24", "SM S921B", 256, 8, 167, 699, "MOVIL-003", null, null);
            movil3.setCategoria(samsung);
            movil3.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/3.jpg"));
            entityManager.persist(movil3);

            Movil movil4 = new Movil("Samsung Galaxy A55", "SM A556B", 128, 8, 213, 399, "MOVIL-004", null, null);
            movil4.setCategoria(samsung);
            movil4.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/4.jpg"));
            entityManager.persist(movil4);

            Movil movil5 = new Movil("iPhone 15", "APPLEPHONE015", 128, 6, 171, 699, "MOVIL-005", null, null);
            movil5.setCategoria(apple);
            movil5.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/5.jpg"));
            entityManager.persist(movil5);

            Movil movil6 = new Movil("iPhone 14", "APPLEPHONE014", 128, 6, 172, 599, "MOVIL-006", null, null);
            movil6.setCategoria(apple);
            movil6.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/6.jpg"));
            entityManager.persist(movil6);

            Movil movil7 = new Movil("Xiaomi Redmi Note 13", "REDMI NOTE 13", 256, 8, 188, 249, "MOVIL-007", null, null);
            movil7.setCategoria(xiaomi);
            movil7.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/7.jpg"));
            entityManager.persist(movil7);

            Movil movil8 = new Movil("Xiaomi 14", "XIAOMI 14", 512, 12, 193, 799, "MOVIL-008", null, null);
            movil8.setCategoria(xiaomi);
            movil8.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/8.jpg"));
            entityManager.persist(movil8);

            Movil movil9 = new Movil("Xiaomi Poco X6", "POCO X6", 256, 12, 181, 349, "MOVIL-009", null, null);
            movil9.setCategoria(xiaomi);
            movil9.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/9.jpg"));
            entityManager.persist(movil9);

            Movil movil10 = new Movil("Xiaomi Redmi 13", "REDMI 13", 128, 6, 205, 199, "MOVIL-010", null, null);
            movil10.setCategoria(xiaomi);
            movil10.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/10.jpg"));
            entityManager.persist(movil10);

            //Vamos a registrar unos usuarios y algun carrito

            Usuario user1 = new Usuario();
            user1.setNombre("Pepe");
            user1.setApellidos("Sanchez");
            user1.setEmail("pepe@gmail.com");
            user1.setPass("12345678");
            entityManager.persist(user1);

            //Vamos a prerpar un carrito
            Carrito carrito1 = new Carrito();
            carrito1.setUsuario(user1);
            entityManager.persist(carrito1);

            ProductoCarrito pc1 = new ProductoCarrito();
            pc1.setCarrito(carrito1);
            pc1.setMovil(movil1);
            pc1.setCantidad(3);
            entityManager.persist(pc1);

            ProductoCarrito pc2 = new ProductoCarrito();
            pc2.setCarrito(carrito1);
            pc2.setMovil(movil3);
            pc2.setCantidad(5);
            entityManager.persist(pc2);




            //Una vez realizados los registros iniciales guardo el registro en la tabla Configuracion indicando que todo esta ok
            configuracion = new Configuracion();
            configuracion.setRegistrosiIniciales(true);
            entityManager.persist(configuracion);
        }



    }//end prepararRegistrosIniciales

    private byte[] leerImagenInicial(String ruta_imagen){
        byte[] info = null;
        try {
            URL url = new URL(ruta_imagen);
            info = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info;
    }


}
