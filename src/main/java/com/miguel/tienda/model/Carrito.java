package com.miguel.tienda.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito")
    private List<ProductoCarrito> productosCarrito =
            new ArrayList<ProductoCarrito>();

    @Id
    @GeneratedValue
    private Long id;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ProductoCarrito> getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(List<ProductoCarrito> productosCarrito) {
        this.productosCarrito = productosCarrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
