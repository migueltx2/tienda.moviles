package com.miguel.tienda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {
    private String nombre;
    private String descripcion;

    // Voy a especificar la asociacion de Categoria con Videojuego
    // Asociaciones en hibernate/jpa:
    // OneToOne: 1 categoria solo puede ser agisnada a 1 producto
    // OneToMany: 1 categoria puede estar asociada a muchos productos
    // ManyToOne: muchas categorias pueden estar asociadas a 1 producto
    // ManyToMany: muchas categorias pueden ser asignadas a muchos productos

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Movil> moviles = new ArrayList<Movil>();

    @Id
    @GeneratedValue
    private int id;

    public Categoria() {
    }

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Movil> getMoviles() {
        return moviles;
    }

    public void setMoviles(List<Movil> moviles) {
        this.moviles = moviles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
