package com.miguel.tienda.constantesSQL;

public class ConstantesSQL {
    public final static String SQL_OBTENER_MOVILES_JSON = "SELECT tv.id, tv.nombre, tv.precio,c.nombre as categoria FROM MOVIL as tv, CATEGORIA as c where tv.categoria_id = c.id;";

    public final static String SQL_OBTENER_MOVIL_JSON = "SELECT tv.id, tv.nombre, tv.precio,c.nombre as categoria FROM MOVIL as tv, CATEGORIA as c where tv.categoria_id = c.id and tv.id = 2;";
}
