package com.miguel.tienda.constantesSQL;

public class ConstantesSQL {
    public final static String SQL_OBTENER_MOVILES_JSON = "SELECT tv.id, tv.nombre, tv.precio,c.nombre as categoria FROM MOVIL as tv, CATEGORIA as c where tv.categoria_id = c.id and lower(tv.nombre) like lower(:titulo) limit :comienzo , 10;";

    public final static String SQL_OBTENER_MOVIL_JSON =   "SELECT tv.id, tv.nombre, tv.precio, tv.modelo, tv.almacenamiento, tv.ram, tv.peso, c.nombre as categoria FROM MOVIL as tv, CATEGORIA as c where tv.categoria_id = c.id and tv.id = :id;";
    public static final String SQL_OBTENER_PRODUCTOS_CARRITO_JSON = """ 
            SELECT
                    tv.id AS movil_id,
                    tv.precio,
                    tv.nombre,
                    pc.cantidad
                FROM movil AS tv
                JOIN producto_carrito AS pc
                    ON tv.id = pc.movil_id
                WHERE pc.carrito_id = :carrito_id;
            """;
    public static final String SQL_BORRAR_PRODUCTOS_CARRITO = "DELETE FROM producto_carrito WHERE carrito_id = :carrito_id";

    public static final String SQL_OBTENER_TOTAL_MOVILES = """
            select count(id) FROM movil WHERE lower(nombre) like :titulo
            """;
}
