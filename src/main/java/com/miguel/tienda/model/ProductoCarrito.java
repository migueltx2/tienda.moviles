package com.miguel.tienda.model;

import jakarta.persistence.*;

@Entity
public class ProductoCarrito {

    @OneToOne
    @JoinColumn
    private Movil movil;

    @ManyToOne
    @JoinColumn (name = "carrito_id")
    private Carrito carrito;

    @Id
    @GeneratedValue
    private Long id;

    //Campos extra
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Movil getMovil() {
        return movil;
    }

    public void setMovil(Movil movil) {
        this.movil = movil;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
