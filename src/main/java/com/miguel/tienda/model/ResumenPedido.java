package com.miguel.tienda.model;

import java.util.List;
import java.util.Map;

//Esta es la informacion que recibira el usuario para confirmar el pedido
public class ResumenPedido {

    //Datos paso1:
    private String nombreCompleto;
    private String direccion;
    private String provincia;

    //Datos paso2:
    private String titularTarjeta;
    private String numeroTarjeta;
    private String tipoTarjeta;

    List<Map<String, Object>> moviles;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public List<Map<String, Object>> getMoviles() {
        return moviles;
    }

    public void setMoviles(List<Map<String, Object>> moviles) {
        this.moviles = moviles;
    }
}
