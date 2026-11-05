package com.miguel.tienda.responseData;

import java.util.List;
import java.util.Map;


//tipo de dato para devolver los moviles y su total para el js mostrar_moviles
public class InfoMoviles {

    private List<Map<String, Object>> moviles;
    private int totalMoviles;

    public List<Map<String, Object>> getMoviles() {
        return moviles;
    }

    public void setMoviles(List<Map<String, Object>> moviles) {
        this.moviles = moviles;
    }

    public int getTotalMoviles() {
        return totalMoviles;
    }

    public void setTotalMoviles(int totalMoviles) {
        this.totalMoviles = totalMoviles;
    }
}
