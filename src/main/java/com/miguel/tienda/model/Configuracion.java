package com.miguel.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//tabla que va a ayudarnos a ver si ya tenemos unos registro iniciales u otras cosas


@Entity
public class Configuracion {
    //La tabla configuracion marcara en una fila si ya se han insertado los registros iniciales en la tienda
    private boolean registrosiIniciales;

    @Id
    @GeneratedValue
    private int id;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRegistrosiIniciales() {
        return registrosiIniciales;
    }

    public void setRegistrosiIniciales(boolean registrosiIniciales) {
        this.registrosiIniciales = registrosiIniciales;
    }
}
